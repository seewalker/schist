(ns schist.watermark-test
  (:require [clojure.test :refer :all]
            [schist.watermark :refer :all]
            ))

(deftest
  lcs-strict-substring
  (testing "left is substring"
    (let [left "bcd"
          right "abcde"
          common (lcs left right)
          ]
      (is (= left common)))
    )
  (testing "right is substring"
    (let [left "lmnop"
          right "mno"
          common (lcs left right)
          ]
      (is (= right common)))
    )
  )

(deftest
  overlap-range
  (testing "left is substring"
    (let [left "abcd"
          right "bcde"
          common (lcs left right)
          ]
      (is (= "bcd" common)))
    )
  )
