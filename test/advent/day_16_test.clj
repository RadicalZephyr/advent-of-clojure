(ns advent.day-16-test
  (:require [advent.day-16 :as sut]
            [clojure.test :as t]))

(t/deftest parse-aunts-test
  (t/is (= {1 {:trees 1 :goldfish 2 :cats 0}}
           (sut/parse-aunts "Sue 1: trees: 1, goldfish: 2, cats: 0")))
  (t/is (= {100 {:fowl 1 :blue 2 :cats 0}}
           (sut/parse-aunts "Sue 100: fowl: 1, blue: 2, cats: 0"))))
