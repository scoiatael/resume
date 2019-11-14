(ns resume.core
  (:require [resume.org :as org]
            [cheshire.core :as json]
            [resume.resume-json :as resume-json]))

(def experience-source "org/experience.org")
(def resume-json-build "target/resume.json")

(defn export-resume-json
  "Convert org/experience.org into build/resume.json"
  []
  (->> experience-source
      slurp
      org/parse
      resume-json/export
      (#(json/generate-string % {:pretty true}))
      (spit resume-json-build)))
