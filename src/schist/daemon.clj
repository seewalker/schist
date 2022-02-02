(ns schist.daemon
  (:require [clojure.tools.logging :as log]
            [clojure.core.async :as async])
  )

(defn schist-loop [args]
  (log/info "schist looping")
  (Thread/sleep 1000))

; to be like the others, it should return pretty fast, which means the loop should start in the background
; getting kicked off in a different process.
(defn schist-start [args]
  (log/info args)
  (async/thread
    (while true (schist-loop args))))

; Questioning stop and restart, actually. Don't want to introduce that much server process complexity, listening
; or frameworks or more threads. User could just find process and kill it if they want, at least in the first go.
(defn schist-stop [args]
  (log/info args))
(defn schist-restart [args]
  (log/info args))