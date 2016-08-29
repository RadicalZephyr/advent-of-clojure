(ns advent.day-10-test
  (:require [advent.day-10 :as sut]
            [clojure.test :as t]))

(t/deftest run-length-encode-test
  (t/is (= "11"
           (sut/run-length-encode "1")))
  (t/is (= "21"
           (sut/run-length-encode "11")))
  (t/is (= "12"
           (sut/run-length-encode "21"))))

(t/deftest next-sequence-test
  (t/is (= "11"
           (sut/next-sequence "1")))
  (t/is (= "21"
           (sut/next-sequence "11")))
  (t/is (= "1211"
           (sut/next-sequence "21")))
  (t/is (= "111221"
           (sut/next-sequence "1211")))
  (t/is (= "312211"
           (sut/next-sequence "111221")))
  (t/is (= "13112221"
           (sut/next-sequence "312211")))
  (t/is (= "1113213211"
           (sut/next-sequence "13112221")))

  (t/is (= "1113213211"
           (nth (iterate sut/next-sequence "1") 7))))
