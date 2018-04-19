(ns advent.day-20-test
  (:require [advent.day-20 :as sut]
            [clojure.test :as t]))

(t/deftest factorsum-test
  (t/is (= 3
           (sut/factorsum 2)))

  (t/is (= 7
           (sut/factorsum 4)))

  (t/is (= 12
           (sut/factorsum 6)))

  (t/is (= 8
           (sut/factorsum 7)))

  (t/is (= 15
           (sut/factorsum 8)))

  (t/is (= 13
           (sut/factorsum 9))))

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

(t/deftest prime-seq-test
  (t/is (= [2]
           (take 1 (sut/prime-seq))))
  (t/is (= [2 3]
           (take 2 (sut/prime-seq))))
  (t/is (= [2 3 5]
           (take 3 (sut/prime-seq))))
  (t/is (= [2 3 5 7]
           (take 4 (sut/prime-seq)))))

(t/deftest partitions-test
  (t/is (= [[1]]
           (sut/partitions 1)))
  (t/is (= [[2] [1 1]]
           (sut/partitions 2)))
  (t/is (= [[3] [2 1] [1 1 1]]
           (sut/partitions 3)))
  (t/is (= [[4] [3 1] [2 2] [2 1 1] [1 1 1 1]]
           (sut/partitions 4))))
