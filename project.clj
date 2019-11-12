(defproject resume "0.1.0-SNAPSHOT"
  :description "Resume-from-org generator"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/core.match "0.3.0"]]
  :aliases {"build-resume-json" ["run" "-m" "resume.core/export-resume-json"]}
  :repl-options {:init-ns resume.core}
  :profiles { ;:dev {:dependencies  [org.clojure/tools.trace "0.7.10"]}
             :test {:dependencies [[midje "1.9.9"]
                                   [nubank/matcher-combinators "1.2.4"]]
                    :plugins [[lein-midje "3.2.1"]]}})
