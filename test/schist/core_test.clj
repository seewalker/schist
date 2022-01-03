(ns schist.core-test
  (:require [clojure.test :refer :all]
            [schist.core :refer :all]
            [clojure.string :as str]
            [expectations.clojure.test :refer [defexpect expect expecting]]
            ))

; this passes in command line but not clicking here, needs to understand it's part of "test" profile.
(deftest auto-test
  (testing "trying out automcomplete"
    (is (not (nil? (str/trim-newline ""))))
    (expect 1 1)
    ))