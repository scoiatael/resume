(ns resume.resume-json-test
  (:require [resume.resume-json :as sut]
            [midje.sweet :as midje]))

(midje/fact "Sections are properly flattened into map"
            (sut/sections-into-map {:children [{:heading "foo"}]})
            => {"foo" {:heading "foo"}})

(midje/fact "Education is exported properly"
            (sut/export-education {:options {"FROM" "2009-12-12"
                                             "TO" "2012-01-12"
                                             "AREA" "Software Engineering (incomplete)",
                                             "DEGREE" "Bachelors"
                                             "INSTITUTION" "The University of Queensland"}})
            => {:endDate "2012-01-12"
                :startDate "2009-12-12"
                :area "Software Engineering (incomplete)"
                :studyType "Bachelors"
                :institution "The University of Queensland"})

(midje/fact "Skills are exported properly"
            (sut/export-skills {:options {"LEVEL" "Senior"}
                                :heading "Backend"
                                :text ["Node"]})
            => {:level "Senior"
                :name "Backend"
                :keywords ["Node"]})

(midje/fact "Basics are exported properly"
            (sut/export-basics {:options {"NAME" "Foo Bar" "LABEL" "Frontend Architect" "EMAIL" "test@frotend.example" "SUMMARY" "Lorem Ipsum"}
                                :children [{:heading "Location" :options {"CITY" "Warszawa" "COUNTRY" "CH"}}
                                           {:heading "Profiles"
                                            :children [{:heading "SomeNetwork" :options {"USERNAME" "Yarpen" "URL" "https://face.in/yzigrin"}}]}]})
            => {:email "test@frotend.example"
                :label "Frontend Architect"
                :location {:city "Warszawa" :countryCode "CH"}
                :name "Foo Bar"
                :profiles [{:network "SomeNetwork"
                            :url "https://face.in/yzigrin"
                            :username "Yarpen"}]
                :summary "Lorem Ipsum"})

(midje/fact "Work experience is exported properly"
            (sut/export-experience {:options {"LEVEL" "Senior Javascript Developer" "FROM" "2018-03-01" "TO" "2019-03-01" "POSITION" "Senior Javascript Developer"}
                                    :heading "Blockbid"
                                    :text ["Blockbid is an Australian crypto currency exchange." "React, Apollo, Styled Components" "Node.js / Rails"]})
            => {:summary "Blockbid is an Australian crypto currency exchange."
                :company "Blockbid"
                :position "Senior Javascript Developer"
                :startDate "2018-03-01"
                :endDate "2019-03-01"
                :highlights ["React, Apollo, Styled Components"
                             "Node.js / Rails"]})
