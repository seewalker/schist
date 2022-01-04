(ns schist.db.common)

(defn dispatch-by-config [config]
  (:persist-mode config))

; if this goes on and on, macro?
(defmulti db-setup!
          dispatch-by-config)
(defmulti save-records
          dispatch-by-config)
(defmulti latest-save
          dispatch-by-config)
