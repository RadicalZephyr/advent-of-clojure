(ns advent.day-19-test
  (:require [advent.day-19 :as sut]
            [clojure.test :as t]))

(t/deftest parse-puzzle-test
  (t/is (= [{"H" #{"HO" "OH"}
             "O" #{"HH"}}
            "HOH"]
           (sut/parse-puzzle "H => HO\nH => OH\nO => HH\n\nHOH\n"))))

(t/deftest indexes-of
  (t/is (= [0 2]
           (sut/indexes-of "HOH" "H")))
  (t/is (= [1]
           (sut/indexes-of "HOH" "O"))))

(t/deftest all-replacements-test
  (t/is (= ["HOO"]
           (sut/all-replacements "HO" ["H" #{"HO"}])))
  (t/is (= ["HOOH" "HOHO"]
           (sut/all-replacements "HOH" ["H" #{"HO"}]))))
