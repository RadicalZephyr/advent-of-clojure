(ns advent.day-16-test
  (:require [advent.day-16 :as sut]
            [clojure.test :as t]))

(t/deftest parse-aunts-test
  (t/is (= {1 {:trees 1 :goldfish 2 :cats 0}}
           (sut/parse-aunts "Sue 1: trees: 1, goldfish: 2, cats: 0")))
  (t/is (= {100 {:fowl 1 :blue 2 :cats 0}}
           (sut/parse-aunts "Sue 100: fowl: 1, blue: 2, cats: 0"))))

(t/deftest aunt-matches?-test
  (t/is (= true
           (sut/aunt-matches? {:a 1}
                              {:a 1})))
  (t/is (= true
           (sut/aunt-matches? {:a 1 :b 2}
                              {:a 1}))))

(t/deftest map<-test
  (t/is (= true
           (sut/map< {:a 1}
                     {:a 2})))
  (t/is (= false
           (sut/map< {:a 3}
                     {:a 2}))))

(t/deftest map>-test
  (t/is (= false
           (sut/map> {:a 1}
                     {:a 2})))
  (t/is (= true
           (sut/map> {:a 3}
                     {:a 2}))))

(t/deftest aunt-matches2?-test
  (t/is (= true
           (sut/aunt-matches2? {:a 1}
                               {:a 1})))
  (t/is (= true
           (sut/aunt-matches2? {:cats 1 :a 1}
                               {:cats 2})))
  (t/is (= true
           (sut/aunt-matches2? {:cats 1 :goldfish 3}
                               {:cats 2 :goldfish 1}))))
