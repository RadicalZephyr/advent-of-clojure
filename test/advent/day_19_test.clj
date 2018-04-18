(ns advent.day-19-test
  (:require [advent.day-19 :as sut]
            [clojure.test :as t]))

(t/deftest parse-puzzle-test
  (t/is (= [{"H" #{"HO" "OH"}
             "O" #{"HH"}}
            "HOH"]
           (sut/parse-puzzle "H => HO\nH => OH\nO => HH\n\nHOH\n"))))
