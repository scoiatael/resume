(ns resume.org
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

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

(declare run-parser)

(defn- descend [state token tokens]
  (let [[_heading {level :level heading :text}] token
        [new-state new-tokens] (run-parser {:level level :heading heading :text [] :options {} :children []} tokens)]
    (run-parser (update-in state [:children] #(conj % new-state)) new-tokens)))

(defn- run-parser [state tokens]
  (if-let [token (first tokens)]
    (match token
           [:heading {:level level :text text}] (if (< (:level state) level) (descend state token (rest tokens)) [state tokens])
           [:text text] (run-parser (update-in state [:text] #(conj % text)) (rest tokens))
           [:option {:name name :value value}] (run-parser (update-in state [:options] #(assoc % name value)) (rest tokens)))
    [state tokens]))

(defn into-ast
  "Combine array of tokens into AST"
  [tokens]
  (let [[final-state tokens] (run-parser {:level 0 :text [] :options {} :children []} tokens)]
    (if (empty? tokens)
      final-state
      (throw (AssertionError. "Leftover tokens.")))))

(defn parse
  "Parse org-mode file into AST"
  [org-contents]
  (-> org-contents
      tokenize
      into-ast))
