(ns resume.pages
  (:require [cheshire.core :as cheshire]
            [cheshire.generate :refer [add-encoder]]
            [java-time :as time]
            [resume.gen :as gen]))

(add-encoder java.time.LocalDate
             (fn [c jsonGenerator]
               (.writeString jsonGenerator (time/format "yyyy-MM-dd" c))))

(def all {"/resume.html" (fn [_req] (gen/resume-html))
          "/resume.json" (fn [_req] (-> (gen/resume-json) (cheshire/generate-string {:pretty true})))})
