(ns resume.docker
  (:require [clojure.java.shell :as shell]
            [clojure.string :as str]
            [stasis.core :as stasis]))

(defn build
  "Build base docker image and return its tag"
  []
  (let [{exit :exit
         out :out
         err :err} (shell/sh "docker" "build" ".")]
    (if (= 0 exit)
      (->> out str/split-lines (map #(re-matches #"Successfully built (.*)" %)) (filter #(not (= nil %))) first last)
      (throw (Exception. "docker build failed")))))

(defn- with-reporting [export-dir fun]
  (let [load-export-dir (fn [] (stasis/slurp-directory export-dir #"\.[^.]+$"))
        before (load-export-dir)]
    (fun)
    (println "Export complete:")
    (stasis/report-differences before (load-export-dir))))

(defn export
  "Compile and export artifacts from inside docker container"
  []
  (let [image-tag (build)
        pwd (-> (java.io.File. ".") .getAbsolutePath)
        docker-invocation ["docker" "run" "--rm" (str "-v=" pwd ":/usr/local/resume") image-tag "lein" "export"]]
    (with-reporting
      "target/dist"
      (fn []
        (let [{exit :exit
               out :out
               err :err} (apply shell/sh docker-invocation)]
          (if-not (= 0 exit)
            (throw (Exception. "docker export failed"))))))))
