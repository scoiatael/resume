(ns resume.resume-json)

(defn export-education
  "Convert org section about education into resume.json format"
  [education]
  (let [{{from "FROM"
          to "TO"
          area "AREA"
          degree "DEGREE"
          institution "INSTITUTION"} :options} education]
    {:endDate to
     :startDate from
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
     :startDate (get options "FROM")
     :endDate (get options "TO")
     :highlights (rest text)}))

(defn export
  "Exports parsed org experience file into resume.json format"
  [org]
  (let [sections-by-name (sections-into-map org)]
    {:basics (->> sections-by-name (#(get % "Basics")) export-basics)
     :education (->> sections-by-name (#(get % "Education")) :children (map export-education))
     :references []
     :skills (->> sections-by-name (#(get % "Skills")) :children (map export-skills))
     :awards []
     :work (->> sections-by-name (#(get % "Experience")) :children (map export-experience))
     :meta {:theme :elegant}}))
