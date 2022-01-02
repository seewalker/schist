(ns schist.watermark
  (:require
    [clojure.tools.logging :as log]
    )
  )

; hash. If same as last time (as determined by program memory or file, defaulting to false if I can't tell)
; then we know there's updates. But, in general, there is no guarantee of continuity. Detect big gaps
; and ask for clarification in those scenarios before saving anything. Find window of overlap
; (convolution)
; Need pen-and-paper for cases of maximum substring and whether I think they mean discontinuities
; ultimately, should be tunable though.
(defn app-checksum [app config]
  (let [filename (:name (app config))]
    ((try
       (hash (slurp filename))
       (catch Exception e
         (log/error e "Failed to ")
         (throw e)
         )))
    )
  )

; source : https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Longest_common_substring
; todo - unit tests for this
; was originally returning maxlen, but when I do return "str1" it looks right.
(defn lcs
  [str1 str2]
  (loop [s1 (seq str1), s2 (seq str2), len 0, maxlen 0]
    (cond
      (>= maxlen (count s1)) {:s1 s1 :s2 s2 :len len :maxlen maxlen}
      (>= maxlen (+ (count s2) len)) (recur (rest s1) (seq str2) 0 maxlen)
      :else (let [a (nth s1 len ""), [b & s2] s2, len (inc len)]
              (if (= a b)
                (recur s1 s2 len (if (> len maxlen) len maxlen))
                (recur s1 s2 0 maxlen))))))
