(ns advent.day-2-test
  (:require [advent.day-2 :as sut]
            [clojure.test :as t]))

(t/deftest box-area+-test
  (t/is (= 43
           (sut/box-area+ [1 1 10])))
  (t/is (= 58
           (sut/box-area+ [2 3 4]))))

(t/deftest ribbon-length+-test
  (t/is (= 34
           (sut/ribbon-length+ [2 3 4])))
  (t/is (= 14
           (sut/ribbon-length+ [1 1 10]))))
