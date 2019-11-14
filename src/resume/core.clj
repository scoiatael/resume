(ns resume.core
  (:require [resume.pages :as pages]
            [stasis.core :as stasis]))

(def export-dir "target/dist")

(defn export []
  (stasis/empty-directory! export-dir)
  (stasis/export-pages pages/all export-dir))
