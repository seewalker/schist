(ns schist.core-test
  (:require [clojure.test :refer :all]
            [schist.core :refer :all]
            [clojure.string :as str]))

(deftest auto-test
  (testing "trying out automcomplete"
    (is (not (nil? (str/trim-newline ""))))))