(ns resume.web
  (:require [clojure.java.io :as io]
            [resume.core :as core]
            [selmer.parser :as selmer]
            [stasis.core :as stasis]))

(defn resume-page [_request] (selmer/render-file "resume.html" {:resume (core/generate-resume)
                                                                :css-paths ["https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css"
	                                                                          "https://cdnjs.cloudflare.com/ajax/libs/octicons/2.0.2/octicons.min.css"
                                                                            "style.css"]}))

(defn get-pages []
  (stasis/merge-page-sources
   {:public (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
    :resume {"/resume.html" resume-page}}))

(def app (do
           (selmer/set-resource-path! (io/resource "templates"))
           (stasis/serve-pages get-pages)))
