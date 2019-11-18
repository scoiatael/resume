(ns resume.gh-pages
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]))


(defn- sh! [& args]
  (let [cmd-result (apply shell/sh args)
        {exit :exit
         err :err} cmd-result]
    (if (= 0 exit)
      cmd-result
      (throw (Exception. (str "invocation of '" args "' failed with " err))))))

(defn deploy
  "Deploy current artifacts to gh-pages branch"
  []
  (let [git-tag (-> (sh! "git" "describe" "--tags") :out str/trim)]
    (sh! "git" "checkout" "gh-pages")
    (sh! "mv" "target/dist/resume.html" "index.html")
    (sh! "git" "add" "index.html")
    (sh! "git" "commit" "-m" (str "release: " git-tag))
    (sh! "git" "push" "origin" "gh-pages")))
