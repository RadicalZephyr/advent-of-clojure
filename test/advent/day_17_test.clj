(ns advent.day-17-test
  (:require [advent.day-17 :as sut]
            [clojure.test :as t]))

(t/deftest count-the-ways-test
  (t/is (= 1
           (sut/count-the-ways [5] 5)))
  (t/is (= 2
           (sut/count-the-ways [4 3 2 1] 5)))
  (t/is (= 4
           (sut/count-the-ways [4 3 2 1 1] 5)))
  (t/is (= 6
           (sut/count-the-ways [4 4 3 2 1 1] 5)))
  (t/is (= 4
           (sut/count-the-ways [20 15  10 5 5] 25))))

(t/deftest count-the-ways2-test
  (t/is (= 1
           (sut/count-the-ways2 [5] 5)))
  (t/is (= 2
           (sut/count-the-ways2 [4 3 2 1] 5)))
  (t/is (= 3
           (sut/count-the-ways2 [4 3 2 1 1] 5)))
  (t/is (= 3
           (sut/count-the-ways2 [20 15  10 5 5] 25))))
