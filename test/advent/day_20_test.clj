(ns advent.day-20-test
  (:require [advent.day-20 :as sut]
            [clojure.test :as t]))

(t/deftest factors-test
  (t/is (= #{1}
           (sut/factors 1)))

  (t/is (= #{1 2}
           (sut/factors 2)))

  (t/is (= #{1 2 4}
           (sut/factors 4)))

  (t/is (= #{1 2 3 6}
           (sut/factors 6))))
