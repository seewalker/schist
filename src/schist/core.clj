(ns schist.core
  (:require
    [clojure.tools.logging :as log]
    [schist.config :refer [load-config]]
    [schist.daemon :refer [schist-start]]
    [cli-matic.core :refer [run-cmd]])
  (:gen-class))

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

; Will keep this pretty minimalistic. Features like ""
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
                                       ; maybe an option for filter by only successful commands
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
                         :description "Loads a file into the history for a given app (using the log format of that app)"
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
                        ; maybe an "execute" command that runs something in the system shell.
                        {
                         :command     "start"
                         :description "Starts schist sync in the background continuously"
                         :opts        [
                                       port-opt
                                       ]
                         :runs        schist-start
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
    (log/info config)
    (run-cmd args cli-api)
    )
  )