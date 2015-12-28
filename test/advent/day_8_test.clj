(ns advent.day-8-test
  (:require [advent.day-8 :as sut]
            [advent.core :refer [resource-line-seq]]
            [clojure.test :as t]))

(def test-lines
  (->> "day_8_test.txt"
       resource-line-seq
       vec))

(defn test-case [n]
  (nth test-lines n))

(t/deftest count-code-chars-test
  (t/is (= 2
           (sut/count-code-chars (test-case 0))))
  (t/is (= 5
           (sut/count-code-chars (test-case 1))))
  (t/is (= 10
           (sut/count-code-chars (test-case 2))))
  (t/is (= 7
           (sut/count-code-chars (test-case 3))))
  (t/is (= 6
           (sut/count-code-chars (test-case 4)))))

(t/deftest interpet-unicode-test
  (t/is (= "'"
           (sut/interpret-unicode "\\x27"))))

(t/deftest count-memory-chars-test
  (t/is (= 0
           (sut/count-memory-chars (test-case 0))))
  (t/is (= 3
           (sut/count-memory-chars (test-case 1))))
  (t/is (= 7
           (sut/count-memory-chars (test-case 2))))
  (t/is (= 4
           (sut/count-memory-chars (test-case 3))))
  (t/is (= 1
           (sut/count-memory-chars (test-case 4)))))

(t/deftest count-encoded-chars-test
  (t/is (= 6
           (sut/count-encoded-chars (test-case 0))))
  (t/is (= 9
           (sut/count-encoded-chars (test-case 1))))
  (t/is (= 16
           (sut/count-encoded-chars (test-case 2))))
  (t/is (= 13
           (sut/count-encoded-chars (test-case 3))))
  (t/is (= 11
           (sut/count-encoded-chars (test-case 4)))))
