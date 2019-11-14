(ns resume.core
  (:require [clojure.java.io :as io]
            [selmer.parser :as selmer]
            [stasis.core :as stasis]
            [resume.pages :as pages]))

(def export-dir "target/dist")

(defn export []
  (selmer/set-resource-path! (io/resource "templates"))
  (stasis/empty-directory! export-dir)
  (stasis/export-pages pages/all export-dir))
