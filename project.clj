(defproject schist "0.1.0-SNAPSHOT"
  :description "A well-preserved shell history"
  :url "http://example.com/FIXME"
  :license {:name "MPL-2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.3"]             ; or 1.10.3 since the jdbc made this download?
                 [org.clojure/tools.logging "1.2.3"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [cli-matic "0.4.3"]
                 [com.github.seancorfield/next.jdbc "1.2.761"]
                 [org.xerial/sqlite-jdbc "3.36.0.3"]
                 ]
  :profiles {
             :test
             {
              :dependencies [
                             [com.github.seancorfield/expectations "2.0.143"]
                             [pjstadig/humane-test-output "0.11.0"]
                             ]
              :injections   [
                             (require 'pjstadig.humane-test-output)
                             (pjstadig.humane-test-output/activate!)
                             ]
              }
             }
  :repl-options {:init-ns schist.core}
  :main schist.core
  :jvm-opts ["-Dclojure.tools.logging.factory=clojure.tools.logging.impl/slf4j-factory"]
  )
