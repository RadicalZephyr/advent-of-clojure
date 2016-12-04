(ns advent.day-3-test
  (:require [advent.day-3 :as sut]
            [clojure.test :as t]))

(t/deftest next-step-test
  (t/is (= [0 1]
           (sut/next-step [0 0] \^)))
  (t/is (= [1 0]
           (sut/next-step [0 0] \>)))
  (t/is (= [-1 0]
           (sut/next-step [0 0] \<)))
  (t/is (= [0 -1]
           (sut/next-step [0 0] \v))))

(t/deftest accumulate-path-test
  (t/is (= [[0 1] [0 0]]
           (sut/accumulate-path '([0 0]) \^)))
  (t/is (= [[1 -1] [1 0]]
           (sut/accumulate-path '([1 0]) \v))))

(t/deftest path-test
  (t/is (= [[0 0] [1 0] [1 1] [0 1] [0 0]]
           (sut/path (seq "^>v<")))))
