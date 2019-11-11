(ns resume.org-test
  (:require [resume.org :refer :all]
            [midje.sweet :refer :all]))

(fact "Headings tokenize to text and level"
 (tokenize "* Foo") => '([:heading {:text "Foo" :level 1}])
 (tokenize "**** Bar") => '([:heading {:text "Bar" :level 4}]))

(fact "Options tokenize to name and value"
      (tokenize ":ICON: foo") => '([:option {:name "ICON" :value "foo"}])
      (tokenize ":URL: http://foo") => '([:option {:name "URL" :value "http://foo"}]))
