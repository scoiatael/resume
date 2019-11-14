(ns resume.gen
  (:require [clojure.java.io :as io]
            [resume.org :as org]
            [resume.resume-json :as resume-json]
            [selmer.parser :as selmer]))

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
