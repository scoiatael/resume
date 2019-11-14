(defproject resume "1.1.0"
  :description "Resume-from-org generator"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cheshire "5.9.0"]
                 [org.clojure/core.match "0.3.0"]
                 [selmer "1.12.17"]
                 [stasis "2.5.0"]]
  :aliases {"build-resume-json" ["run" "-m" "resume.core/export-resume-json"]
            "build-resume-html" ["run" "-m" "resume.web/export"]}
  :repl-options {:init-ns resume.core}
  :ring {:handler resume.web/app}
  :profiles {:dev {:plugins [[lein-ring "0.12.5"]]
                   :dependencies [[ring "1.8.0"]]}
             :test {:dependencies [[midje "1.9.9"]
                                   [nubank/matcher-combinators "1.2.4"]]
                    :plugins [[lein-midje "3.2.1"]]}})
