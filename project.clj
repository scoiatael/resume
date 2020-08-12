(defproject resume "1.5.0"
  :description "Resume generator"
  :url "http://github.com/scoiatael/resume"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cheshire "5.9.0"]
                 [org.clojure/core.match "0.3.0"]
                 [selmer "1.12.17"]
                 [clojure.java-time "0.3.2"]
                 [stasis "2.5.0"]]
  :aliases {"export" ["run" "-m" "resume.core/export"]
            "test" ["with-profile" "test" "midje"]
            "docker-export" ["run" "-m" "resume.docker/export"]
            "gh-pages-deploy" ["run" "-m" "resume.gh-pages/deploy"]}
  :repl-options {:init-ns resume.core}
  :ring {:handler resume.web/app}
  :profiles {:dev {:plugins [[lein-ring "0.12.5"]]
                   :dependencies [[ring "1.8.0"]]}
             :test {:dependencies [[midje "1.9.9"]
                                   [nubank/matcher-combinators "1.2.4"]]
                    :plugins [[lein-midje "3.2.1"]]}})
