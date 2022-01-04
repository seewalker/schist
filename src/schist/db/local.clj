(ns schist.db.local
  (:require
    [next.jdbc :as jdbc]
    [clojure.tools.logging :as log]
    )
  (import (java.util UUID)))

(defonce format-version 1)                                  ; these three constants could be in the abstraction
(defonce uuid-length 36)

(defonce trans-type-codes {:create 1 :set-importance 2})

(def ^:private db {:dbtype "sqlite" :dbname "schist.db"})
(def ^:private datasource (jdbc/get-datasource db))

; might as well "create table if not exists".
; should this namespace own the connection? should it be in the class scope?
; I should make storage abstract to accomodate the "managed-schist" in the future.

; this will be in the abstraction "what is the latest save?"
(defonce latest-command "SELECT max(datetime(trans.utc_datetime)) FROM command_transaction trans INNER JOIN command cmd ON (trans.command_id = cmd.id) WHERE cmd.app = ? AND trans.type = ?")
(defonce latest-command-result (keyword "max(datetime(utc_datetime))")) ; in lockstep with the select above

; sqlite doesn't have datetime as a storage thing, so it will be just text in the creation.
; app can be string, instead of a different table, because that level of abstraction not needed
; so far, and string will be easier to merge.
(defonce ^:private db-creation-statements
         [
          (format
            "CREATE TABLE IF NOT EXISTS command (
              id varchar(%s) PRIMARY KEY,
              app TEXT,
              cmd TEXT,
              lower_bound_utc_datetime TEXT,
              upper_bound_utc_datetime TEXT,
              format_version INTEGER
            )"
            uuid-length
            )
          (format
            "CREATE TABLE IF NOT EXISTS command_transaction (
              id varchar(%s) PRIMARY KEY,
              app TEXT,
              command_id varchar(%s) FOREIGN KEY REFERENCES command(id),
              type INTEGER,
              utc_datetime TEXT,
              format_version INTEGER
            )"
            uuid-length
            )
          (format
            "CREATE TABLE IF NOT EXISTS command_metadata(
              id varchar(%s) PRIMARY KEY,
              comment TEXT,
              importance_indicator INTEGER,
              format_version INTEGER
            )"
            uuid-length
            )
          "CREATE INDEX IF NOT EXISTS search_index ON command(cmd, app)"
          "CREATE INDEX IF NOT EXISTS upper_bound_index ON command_transaction(utc_datetime)"
          ]
         )

; I think this will become a "defmethod" where the dispatch is on :persist-mode
(defn latest-save [app]
  (try
    (get (jdbc/execute-one! datasource [latest-command app]) latest-command-result)
    (catch Exception ex
      (log/error ex "Failed to retrieve ")
      nil
      )))

; I think this will become a "defmethod" where the dispatch is on :persist-mode
(defn save-command [app cmd-txt raw-lower-bound upper-bound]
  (let [cmd-uuid (.toString (UUID/randomUUID))
        trans-uuid (.toString (UUID/randomUUID))
        lower-bound (if (nil? raw-lower-bound) (latest-save app) raw-lower-bound)]
    {:command
     {:id                       cmd-uuid
      :app                      app
      :cmd                      cmd-txt
      :lower_bound_utc_datetime lower-bound
      :upper_bound_utc_datetime upper-bound
      :format_version           format-version
      }
     :command_transaction
     {
      :id             trans-uuid
      :command_id     cmd-uuid
      :app            app
      :type           (:create trans-type-codes)
      :format_version format-version
      }
     })
  )

; One point of having this not be done at classload time is somebody may not want to use local storage at all
; execute the db creation here.
(defn setup! [] nil)

(defn save-records [app cmd-records]
  (jdbc/execute-batch! ds )
  )





; does look like next-jdbc can work with sqlite : https://github.com/seancorfield/next-jdbc/blob/develop/src/next/jdbc/connection.clj
; https://github.com/xerial/sqlite-jdbc