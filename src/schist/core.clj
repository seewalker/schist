(ns schist.core
  (:require
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [cli-matic.core :refer [run-cmd]])
  (:gen-class)
  (:import (java.io IOException PushbackReader)))

(defonce home (System/getProperty "user.home"))
(defonce schist-dir ".schist")
; this will go to a separate "config" ns where the overall object is the only export.
(def ^:private config-name "config.edn")

(defn load-config []
  (let [config-path (io/file home schist-dir config-name)]
    (try
      (with-open [r (io/reader config-path)]
        (edn/read (PushbackReader. r)))
      (catch IOException ex
        (log/errorf ex "Could not load configuration at %s" config-path)
        )
      ))
  )

; replace all system properties in history-logs.name
(defn process-config [config]
  nil
  )

(defn schist-search [args] nil)
(defn schist-merge [args] nil)
(defn schist-load [args]
  (log/info load-args))

(defonce cli-api
{
 :command "schist"
 :description "Maintain shell/repl history"
 :version "0.0.1"                                           ; can I get this from project.clj?
 :subcommands [
               {
                :command "search"
                :description "Searches history for given app for a given command"
                :opts [
                       {:as "app"
                        :option "app"
                        :default :present                   ; present meaning required
                        :type :keyword
                        }
                       ]
                :runs schist-search
                }
               {
                :command "merge"
                :description "Combines a history database file with the current database"
                :opts [
                       {
                        :as "other-db"
                        :option "other-db"
                        :default :present
                        :type :string
                        :short "-o"                         ; or this could be positional, if integer, I think
                        }
                       ]
                :runs schist-merge
                }
               {
                :command "load"
                :description "Loads a file into the history for a given app"
                :opts [
                       {:as "app"
                        :option "app"
                        :default :present                   ; present meaning required
                        :type :keyword
                        }
                       ]
                :runs schist-load
                }
               ]
 }
         )


(defn -main [& args]
  (let [config (load-config)]
    (run-cmd args cli-api)
    )

  )