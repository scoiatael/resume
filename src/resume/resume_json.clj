(ns resume.resume-json
  (:require [java-time :as time]))

(defn parse-date
  [date]
  (time/local-date "yyyy-MM-dd" date))

(defn export-education
  "Convert org section about education into resume.json format"
  [education]
  (let [{{from "FROM"
          to "TO"
          area "AREA"
          degree "DEGREE"
          institution "INSTITUTION"} :options} education]
    {:endDate (parse-date to)
     :startDate (parse-date from)
     :area area
     :studyType degree
     :institution institution}))

(defn export-skills
  "Convert org section about education into resume.json format"
  [skill]
  (let [{{level "LEVEL"} :options
         area :heading
         keywords :text} skill]
    {:keywords keywords
     :level level
     :name area}))

(defn sections-into-map
  "Create lookup map from sections in org file"
  [org]
  (let [{sections :children} org]
    (->> sections
         (map #(vector (:heading %) %))
         (into {}))))

(defn- export-profile [profile]
  (let [{network :heading
         {username "USERNAME"
          url "URL"} :options} profile]
    {:url url
     :username username
     :network network}))

(defn export-basics
  "Convert org section about myself into resume.json format"
  [basics]
  (let [subsections (sections-into-map basics)
        {{{city "CITY"
           country "COUNTRY"} :options} "Location"
         {profiles :children} "Profiles"} subsections
        {{name "NAME"
          label "LABEL"
          email "EMAIL"
          summary "SUMMARY"} :options} basics]
    {:location {:city city :countryCode country}
     :name name
     :label label
     :email email
     :summary summary
     :profiles (map export-profile profiles)}))

(defn export-experience
  "Convert org section about work experience into resume.json format"
  [experience]
  (let [{options :options
         text :text
         name :heading} experience]
    {:summary (first text)
     :company name
     :position (get options "POSITION")
     :startDate (parse-date (get options "FROM"))
     :endDate (parse-date (get options "TO"))
     :highlights (rest text)}))

(defn export-interest
  "Convert org section about interest into resume.json format"
  [interest]
  (let [{interest :heading
         keywords :text} interest]
    {:keywords keywords
     :name interest}))

(defn export-language
  "Convert org section about language into resume.json format"
  [language]
  (let [{language :heading
         {fluency "LEVEL"} :options} language]
    {:fluency fluency
     :language language}))

(defn export
  "Exports parsed org experience file into resume.json format"
  [org]
  (let [sections-by-name (sections-into-map org)]
    {:basics (->> sections-by-name (#(get % "Basics")) export-basics)
     :education (->> sections-by-name (#(get % "Education")) :children (map export-education))
     :skills (->> sections-by-name (#(get % "Skills")) :children (map export-skills))
     :work (->> sections-by-name (#(get % "Experience")) :children (map export-experience))
     :languages (->> sections-by-name (#(get % "Languages")) :children (map export-language))
     :interests (->> sections-by-name (#(get % "Interests")) :children (map export-interest))
     :meta {:theme :pumpkin}}))
