(ns schist.parse.literal
  (:import (schist.parse.common HistLogParser)))

(defrecord LiteralHistLogParser []
  HistLogParser
  ; at this point we definitiely have a non-empty string as "log" because any error handling is common
  (parse-log-entries [log] (let [lines (split-lines log)
                                 lower-bound nil
                                 upper-bound nil
                                 ]
                             (map #(create-entry %1 lower-bound ) lines)
                             ))
  (lower-bound [entry] (:lower-bound entry))
  (upper-bound [entry] (:upper-bound entry))
  (command [entry] (:command entry))
  )