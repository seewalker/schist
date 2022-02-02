(ns schist.history
  ^{:doc "Maintaining schist's own history log file, schist itself being an 'app' it monitors"
    :author "Alex Seewald"}
  (:require
    [schist.config :refer [create-schist-file]]
    [clojure.tools.logging :as log]
    [clojure.java.io :as io]
    )
  (:import (java.io IOException))
  )

;
(defn save-schist-command
  [cmd]
  (let [history-path (create-schist-file "history")]
    (try
      (with-open [w (io/writer history-path :append true)]
        (.write w cmd))
      (catch IOException ex
        (log/errorf ex "Could not write schist command history to %s" history-path)
        (throw ex)))))