(ns resume.web
  (:require [clojure.java.io :as io]
            [selmer.parser :as selmer]
            [resume.pages :as pages]
            [stasis.core :as stasis]))

(def app (do
           (selmer/set-resource-path! (io/resource "templates"))
           (stasis/serve-pages pages/all)))
