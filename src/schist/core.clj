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

(defn schist-sync [args]
  (log/info args))
(defn schist-search [args]
  (log/info args))
(defn schist-load [args]
  (log/info args))
(defn schist-merge [args]
  (log/info args))
(defn schist-annotate [args]
  (log/info args))
(defn schist-start [args]
  (log/info args))
(defn schist-stop [args]
  (log/info args))
(defn schist-restart [args]
  (log/info args))


(defonce app-opt
         {:as      "app"
          :option  "app"
          :default :present                                 ; present meaning required
          :type    :keyword
          }
         )

(defonce profile-opt
         {
          :as      "profile"
          :option  "profile"
          :default "personal-device"
          :type    :string
          }
         )

(defonce port-opt
         {
          :as      "port"
          :option  "port"
          :default 52096
          :type    :int
          })

(defonce cli-api
         {
          :command     "schist"
          :description "Maintain shell/repl history"
          :version     "0.0.1"                              ; can I get this from project.clj?
          :subcommands [
                        {
                         :command     "search"
                         :description "Searches history for given app for a given command"
                         :opts        [
                                       app-opt
                                       profile-opt
                                       ]
                         :runs        schist-search
                         }
                        {
                         :command     "merge"
                         :description "Combines a history database file with the current database"
                         :opts        [
                                       {
                                        :as      "other-db" ; what is the difference between 'as' and 'option'? So far, was running
                                        ; into problems having 'as' but no 'option'
                                        :option  "other-db"
                                        :default :present
                                        :type    :string
                                        :short   "-o"       ; or this could be positional, if integer, I think
                                        }
                                       ]
                         :runs        schist-merge
                         }
                        {
                         :command     "load"
                         :description "Loads a file into the history for a given app"
                         :opts        [
                                       app-opt
                                       ]
                         :runs        schist-load
                         }
                        {
                         :command     "annotate"
                         :description "Marks something as important, optionally with a comment for explanation"
                         :opts        [
                                       app-opt
                                       {
                                        :as      "annotation"
                                        :option  "annotation"
                                        :default nil
                                        :type    :string
                                        }
                                       ]
                         :runs        schist-annotate
                         }
                        {
                         :command     "start"
                         :description "Starts schist sync in the background continuously"
                         :opts        [
                                       port-opt
                                       ]
                         :runs        schist-start
                         }
                        {
                         :command     "stop"
                         :description "Stops schist if it is running in the background"
                         :opts        [
                                       port-opt
                                       ]
                         :runs        schist-stop
                         }
                        {
                         :command     "restart"
                         :description "Restarts schist"
                         :opts        [
                                       port-opt
                                       ]
                         :runs        schist-restart
                         }
                        {
                         :command     "sync"
                         :description "Runs a one-time (as opposed to continuously running in the background) sync of the monitored logs"
                         :opts        [
                                       ; if empty, ommit?
                                       ]
                         :runs        schist-sync
                         }

                        ]
          }
         )

(defn -main [& args]
  (let [config (load-config)]
    (run-cmd args cli-api)
    )
  )