(ns schist.daemon
  (:require [clojure.tools.logging :as log])
  )

(defn schist-start [args]
  (log/info args))
; Questioning stop and restart, actually. Don't want to introduce that much server process complexity, listening
; or frameworks or more threads. User could just find process and kill it if they want, at least in the first go.
(defn schist-stop [args]
  (log/info args))
(defn schist-restart [args]
  (log/info args))