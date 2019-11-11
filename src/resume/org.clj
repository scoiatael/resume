(ns resume.org
  (:require [clojure.string :as str]))


(defn- tokenize-heading [line]
  (let [[indent rest] (split-with #{\*} line)]
    {:text (str/trim (apply str rest))
     :level (count indent)}))


(defn- tokenize-option [line]
  (let [[_empty name value] (str/split line #":" 3)]
    {:name name
     :value (str/trim value)}))

(defn- tokenize-line [line]
  (condp #(str/starts-with? %2 %1)  line
         "*" [:heading (tokenize-heading line)]
         ":" [:option (tokenize-option line)]
         [:text line]))


(defn tokenize
  "Split text into list of tokens"
  [text]
  (->> text
       str/split-lines
       (map str/trim)
       (map tokenize-line)))

(defn parse
  "Parse org-mode file into AST"
  [org-contents]
  (-> org-contents
      tokenize))
