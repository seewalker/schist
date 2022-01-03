(ns schist.db
  (import (java.util UUID)))

; might as well "create table if not exists".
; should this namespace own the connection? should it be in the class scope?
; I should make storage abstract to accomodate the "managed-schist" in the future.

(defonce format-version 1)                                  ; these three constants could be in the abstraction
(defonce uuid-length 36)
(defonce max-app-length 128)

; sqlite doesn't have datetime as a storage thing, so it will be just text in the creation.
; app can be string, instead of a different table, because that level of abstraction not needed
; so far, and string will be easier to merge.
(defonce command-table-creation
         (format
           "CREATE TABLE IF NOT EXISTS command (
          id varchar(%s),
          app varchar(%s),
          cmd TEXT,
          time-lower-bound TEXT,
          time-upper-bound TEXT,
          format-version INTEGER
          )"
           uuid-length
           max-app-length,
           format-version
           )
         )

; this will be an implementation of something abstract
; what's currently here will be in a "let" binding where the body is jdbc stuff.
(defn create-entry [app cmd-txt db-config]
  {:id  (.toString (UUID/randomUUID))
   :app app
   :cmt cmt-txt
   :time-lower-bound nil
   :time-upper-bound nil
   :format-version format-version
   }
  )

; does look like next-jdbc can work with sqlite : https://github.com/seancorfield/next-jdbc/blob/develop/src/next/jdbc/connection.clj
; https://github.com/xerial/sqlite-jdbc