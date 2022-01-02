(ns schist.parse.common)

(defn time-bounds [application]
  nil)

(defprotocol HistLogParser
  (parse-log-entries [log])
  (process-log-entry [log-entry]))
