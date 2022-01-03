(ns schist.config
 (:require
   [clojure.tools.logging :as log]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   )
  (:import (java.io IOException PushbackReader))
  )

(defonce home (System/getProperty "user.home"))
(defonce schist-dir ".schist")
; this will go to a separate "config" ns where the overall object is the only export.
(def ^:private config-name "config.edn")

; this is important to unit test
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

; this is important to unit test
; replace all system properties in history-logs.name
(defn process-config [config]
  nil
  )