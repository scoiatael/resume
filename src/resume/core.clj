(ns resume.core
  (:require [resume.org :as org]
            [clojure.data.json :as json]))

(def experience-source "org/experience.org")
(def resume-json-build "resume.json")

(defn export-resume-json
  "Convert org/experience.org into build/resume.json"
  []
  (->> experience-source
      slurp
      org/parse
      json/write-str
      (spit resume-json-build)))
