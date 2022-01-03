(ns schist.parse.common)

(defn time-bounds [application]
  nil)

; I should design to be as open-for-extension as possible. Plugins using any jvm language (possible to be even more open?)
; so lowest common denomintor may be java interface.
; However this takes shape, it should involve
(defprotocol HistLogParser
  (parse-log-entries [log])
  (process-log-entry [log-entry]))
