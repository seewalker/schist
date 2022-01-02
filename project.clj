(defproject schist "0.1.0-SNAPSHOT"
  :description "A well-preserved shell history"
  :url "http://example.com/FIXME"
  :license {:name "MPL-2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "1.2.3"]
                 [cli-matic "0.4.3"]
                 ]
  :repl-options {:init-ns schist.core}
  :main schist.core
  )
