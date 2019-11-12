(ns resume.org-test
  (:require [resume.org :refer :all]
            [midje.sweet :refer :all]
            [matcher-combinators.midje :refer [match]]))

(fact "Headings tokenize to text and level"
 (tokenize "* Foo") => '([:heading {:text "Foo" :level 1}])
 (tokenize "**** Bar") => '([:heading {:text "Bar" :level 4}]))

(fact "Options tokenize to name and value"
      (tokenize ":ICON: foo") => '([:option {:name "ICON" :value "foo"}])
      (tokenize ":URL: http://foo") => '([:option {:name "URL" :value "http://foo"}]))

(fact "Simple headings are parsed correctly"
      (into-ast [[:heading {:text "Foo" :level 1}]]) => (match {:children [{:heading "Foo"}]}))

(fact "Multiple headings are parsed correctly"
      (into-ast [[:heading {:text "Foo" :level 1}]
                 [:heading {:text "Bar" :level 1}]]) => (match {:children [{:heading "Foo"} {:heading "Bar"}]}))

(fact "Nested headings are parsed correctly"
      (into-ast [[:heading {:text "Foo" :level 1}]
                 [:heading {:text "Baz" :level 2}]
                 [:heading {:text "Bar" :level 1}]]) => (match {:children [{:heading "Foo" :children [{:heading "Baz"}]} {:heading "Bar"}]}))

(fact "Text is parsed correctly"
      (into-ast [[:heading {:text "Foo" :level 1}]
                 [:text "bar"]
                 [:text "baz"]]) => (match {:children [{:heading "Foo" :text ["bar" "baz"]}]}))

(fact "Options are parsed correctly"
      (into-ast [[:heading {:text "Foo" :level 1}]
                 [:option {:name "URL" :value "https://foo.example"}]
                 [:text "baz"]]) => (match {:children [{:heading "Foo" :text ["baz"] :options {"URL" "https://foo.example"}}]}))
