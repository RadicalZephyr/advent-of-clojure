(ns advent.day-20-test
  (:require [advent.day-20 :as sut]
            [clojure.test :as t]))

(t/deftest factorsum-test
  (t/is (= 3
           (sut/factorsum 2)))

  (t/is (= 7
           (sut/factorsum 4)))

  (t/is (= 12
           (sut/factorsum 6))))

(t/deftest factorial-seq-test
  (t/is (= 1
           (nth (sut/factorial-seq) 0)))
  (t/is (= 2
           (nth (sut/factorial-seq) 1)))
  (t/is (= 6
           (nth (sut/factorial-seq) 2)))
  (t/is (= 24
           (nth (sut/factorial-seq) 3)))
  (t/is (= 120
           (nth (sut/factorial-seq) 4))))

(t/deftest partitions-test
  (t/is (= #{[1]}
           (sut/partitions 1)))
  (t/is (= #{[1 1] [2]}
           (sut/partitions 2)))
  (t/is (= #{[1 1 1] [1 2] [3]}
           (sut/partitions 3))))
