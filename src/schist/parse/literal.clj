(ns schist.parse.literal
  (:import (schist.parse.common HistLogParser)))

(defn ^:private create-entry [line lower-bound]
  {:command line :lower-bound lower-bound}
  )

; Can I spec this? That may provide the feeling of missing types I'm having.
(defrecord LiteralHistLogParser []
  HistLogParser
  ; at this point we definitiely have a non-empty string as "log" because any error handling is common
  (parse-log-entries [log] (let [lines (split-lines log)]
                             (map (fn [line] {:command line}) lines)
                             ))
  (lower-bound [entry] nil)
  (upper-bound [entry] nil)
  (command [entry] (:command entry))
  (app-metadata [entry] nil)
  )