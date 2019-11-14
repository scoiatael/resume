(ns resume.pages
  (:require [cheshire.core :as cheshire]
            [resume.gen :as gen]))

(def all {"/resume.html" (fn [_req] (gen/resume-html))
          "/resume.json" (fn [_req] (-> (gen/resume-json) (cheshire/generate-string {:pretty true})))})
