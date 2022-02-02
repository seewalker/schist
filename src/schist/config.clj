(ns schist.config
  (:require
    [clojure.tools.logging :as log]
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [clojure.spec.alpha :as s]
    )
  (:import (java.io IOException PushbackReader)
           (schist.exception ConfigException))
  )

(defonce ^:private home (System/getProperty "user.home"))
(defonce ^:private schist-dir ".schist")
; this will go to a separate "config" ns where the overall object is the only export.
(def ^:private config-name "config.edn")

;
(defn create-schist-file [file-name]
  (io/file home schist-dir file-name))

; this doesn't make it apply to the key :retention (or even :config/retention)
; here it's just a name.
; to make a spec associated to a key, you use s/keys (most important spec, i think).
; may be more concise here (and more precise elsewhere) if using ::retention.
(s/def :config/transaction-retention-days number?)
(s/def :config/device-nickname (s/and string? not-empty))
(s/def :config/persist-mode #{:local :schist-managed})

;
(s/def :config-var/is-env (complement nil?))                ; todo - this will be that the parsing succeeds and if anything found
; then all parse results are environment variables that have values
(s/def :config-var/is-prop (complement nil?))               ; todo - this will be that the parsing succeeds and if anything found
; then all parse results are system properties that have values
(s/def :config/execute-cmd (s/and string? not-empty))       ; should I s/merge in is-env here?
; won't just be "nil?" : this will be the top-level spec.
(s/def :config/valid (s/keys :req [:config/transaction-retention-days :config/device-nickname :config/persist-mode]
                             :opt [:config/execute-cmd]
                             ))

; for history-logs spec, do I use coll-of?

(defn ^:private expand-app-log [app-log]
  app-log
  )
;  (update-in config [:history-logs]
;             (fn [per-app] (map ))
;             )
(defn ^:private expand-config-vars [config]
  config
  )

; this is important to unit test
; Should have a default argument, and unit test will use something other than default.
(defn load-config [& {:keys [file-path] :or {file-path (create-schist-file config-name)}}]
  (try
    (with-open [r (io/reader file-path)]
      (let [config (edn/read (PushbackReader. r))
            validated-config (s/conform :config/valid config)]
        (if (s/invalid? validated-config)
          (throw (ConfigException. (s/explain-str :config/valid validation-errors)))
          (expand-config-vars config))))
    (catch IOException ex
      (log/errorf "Could not load configuration at expected location %s" file-path)
      (throw (ConfigException. ex)))
    (catch ConfigException ex
      (throw ex))
    (catch RuntimeException ex
      (log/errorf "Configuration found at %s has edn format problem" file-path)
      (throw (ConfigException. ex)))
    ))

(defn example [& {:keys [file-path] :or {file-path "x"}}]
  file-path
  )