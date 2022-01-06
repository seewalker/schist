(ns schist.history
  ^{:doc "Maintaining schist's own history log file, schist itself being an 'app' it monitors"
    :author "Alex Seewald"}
  (:require
    [schist.config :refer [create-schist-file]]
    [clojure.tools.logging :as log]
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    )
  (:import (java.io IOException PushbackWriter))
  )

;
(defn save-schist-command
  [cmd]
  (let [history-path (create-schist-file "history.edn")]
    (try
      (with-open [w (io/writer config-path :append true)]
        (edn/write (PushbackWriter. w)))
      (catch IOException ex
        (log/errorf ex "Could not load configuration at %s" config-path)
        (throw ex)))))