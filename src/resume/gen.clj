(ns resume.gen
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [resume.org :as org]
            [resume.resume-json :as resume-json]
            [selmer.filters :as selmer-filters]
            [selmer.parser :as selmer]
            [clojure.string :as str]))

(def experience-source "org/experience.org")

(defn resume-json
  "Prepare resume.json source"
  []
  (->> experience-source
       slurp
       org/parse
       resume-json/export))

(selmer/set-resource-path! (io/resource "templates"))

(defn resume-html
  "Prepare resume.html source"
  []
  (selmer/render-file "resume.html" {:resume (resume-json)
                                     :css (slurp (io/resource "public/style.css"))
                                     :css-paths ["https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css"
                                                 "https://cdnjs.cloudflare.com/ajax/libs/octicons/2.0.2/octicons.min.css"]}))

;; Inspired by https://github.com/yogthos/Selmer/blob/4fdf5b546d6afb2e5cea98adc9424694f6ca5e64/src/selmer/filter_parser.clj#L21
(selmer-filters/add-filter! :escape-tex
          (fn [s]
            (let [slength (count s)
                  sb      (StringBuilder. slength)]
              (loop [idx 0]
                (if (>= idx slength)
                  (.toString sb)
                  (let [c (char (.charAt s idx))]
                    (case c
                      \& (.append sb "\\&")
                      (.append sb c))
                    (recur (inc idx))))))))

(defn resume-tex
  "Prepare resume.tex source"
  []
  (selmer/render-file "resume.tex"
                      {:resume (resume-json)}
                      {:tag-open \<
                       :tag-close \>}))

;; Source: https://stackoverflow.com/a/26372677
(defn slurp-bytes
  "Slurp the bytes from a slurpable thing"
  [x]
  (with-open [out (java.io.ByteArrayOutputStream.)]
    (clojure.java.io/copy (clojure.java.io/input-stream x) out)
    (.toByteArray out)))

(defn resume-pdf
  "Prepare resume.pdf via pdfLaTeX"
  []
  (let [tempDir (-> (shell/sh "mktemp" "-d") :out str/trim)
        clsFilePath (str tempDir "/" "resume.cls")
        resumeFileName "resume.tex"
        resumeFilePath (str tempDir "/" resumeFileName)]
    (spit clsFilePath (slurp (io/resource "public/resume.cls")))
    (spit resumeFilePath (resume-tex))
    (let [pdflatex (shell/sh "pdflatex" resumeFileName :dir tempDir)]
      (if (= 0 (:exit pdflatex))
        (let [pdf (slurp-bytes (str tempDir "/" "resume.pdf"))]
          (shell/sh "rm" "-rf" tempDir)
          pdf)
        (let [errorText (->> pdflatex :out str/split-lines (filter #(re-matches #".*Error.*" %)) first)]
          (throw (Exception. (or errorText (str "Latex invocation failed in " tempDir)))))))))
