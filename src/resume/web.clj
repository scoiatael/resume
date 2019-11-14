(ns resume.web
  (:require [resume.pages :as pages]
            [stasis.core :as stasis]))

(def app (stasis/serve-pages pages/all))
