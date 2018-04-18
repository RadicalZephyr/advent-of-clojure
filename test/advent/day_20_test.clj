(ns advent.day-20-test
  (:require [advent.day-20 :as sut]
            [clojure.test :as t]))

(t/deftest factors-test
  (t/is (= 3
           (sut/factorsum 2)))

  (t/is (= 7
           (sut/factorsum 4)))

  (t/is (= 12
           (sut/factorsum 6))))
