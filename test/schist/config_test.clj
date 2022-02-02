(ns schist.config-test
  (:require [clojure.test :refer :all]
            [expectations.clojure.test :refer [defexpect expect expecting]]
            [schist.config :refer :all]
            )
  (:import (schist.exception ConfigException)))

; Example to use
; (is (thrown-with-msg? ArithmeticException #"Divide by zero" (/ 1 0)))
;  (expect (more-> ArithmeticException type
;                  #"Divide by zero"   ex-message)
;          (/ 1 0))

(defonce valid-config-file "test/resources/valid-config.edn")

(defexpect load-config-test
  (let [config (load-config :file-path valid-config-file)]
    (expecting "Loads with expected pass-through values"
      (expect "test-device" (:device-nickname config))
      (expect :local (:persist-mode config)))
    (expecting "Expands system properties"
      (expect 1 1)
      )
    )
  )

(def z )

(defexpect example-test
  (expect "y" (example :file-path "y"))
  (comment (expect "x" (example)))
  )
;
