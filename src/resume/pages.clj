(ns resume.pages
  (:require [cheshire.core :as cheshire]
            [cheshire.generate :refer [add-encoder]]
            [java-time :as time]
            [resume.gen :as gen]
            [stasis.core :as stasis]))

(add-encoder java.time.LocalDate
             (fn [c jsonGenerator]
               (.writeString jsonGenerator (time/format "yyyy-MM-dd" c))))

(def resumes {"/resume.html" (fn [_req] (gen/resume-html))
              "/resume.json" (fn [_req] (-> (gen/resume-json) (cheshire/generate-string {:pretty true})))
              "/resume.tex" (fn [_req] (gen/resume-tex))
              "/resume.pdf" (fn [_req] (gen/resume-pdf))})

(def all (stasis/merge-page-sources
          {:public (stasis/slurp-directory "resources/public" #".*\.(cls)$")
           :resumes resumes}))
