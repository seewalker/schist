(ns schist.parse.common
  (import java.time
          Instant)
  )

(defn time-bounds [application]
  nil)

; I should design to be as open-for-extension as possible. Plugins using any jvm language (possible to be even more open?)
; so lowest common denomintor may be java interface.
; However this takes shape, it should involve

; this is something that could be implemented in DefRecord. BashHistLogParser extends HistLogParser
; without static types, we aren't saying what parse-log-entries and process-log-entry returm
; those keys will be in each individual defrecord
(defprotocol HistLogParser
  (parse-log-entries [log])
  (lower-bound [entry])
  (upper-bound [entry])
  (command [entry])
  )

(defn unspecified-upper-bound []
  (.toString (Instant/now)))
