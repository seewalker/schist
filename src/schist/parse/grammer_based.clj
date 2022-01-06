(ns schist.parse.grammer-based
  (:require [instaparse.core :as insta])
  )

; use instaparse

(defn line-based-grammar [entry-grammar]
  (str "S = (entry\n)*;" entry-grammar))
; maybe grammers have to define "command". "timestamp" can also be a special thing they may or may not define
;
(def zsh-entry-parser
  (insta/parse "
  S = (entry\n)*
  entry = : timestamp:duration;command
  duration = number
  timestamp = number
  number = #'[0-9]+'
  command = word
  word = #'[0-9a-zA-Z]
  "))

;
(defn create-grammars [config]

  )

; should support reading