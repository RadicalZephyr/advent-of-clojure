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

(t/deftest replacements-for-test
  (t/is (= ["HOO"]
           (sut/replacements-for "HO" ["H" #{"HO"}])))
  (t/is (= ["HOOH" "HOHO"]
           (sut/replacements-for "HOH" ["H" #{"HO"}]))))

(t/deftest molecules-of-length-test
  (t/is (= [[2 "OHH"] [2 "HOH"]]
           (sut/molecules-of-length 3 "e" {"e" #{"O"}
                                           "O" #{"HH"}
                                           "H" #{"OH"}})))

  (t/is (= [[3 "HHHO"]]
           (sut/molecules-of-length 4 "e" {"e" #{"O"}
                                           "O" #{"HO"}}))))

(t/deftest find-shortest-transform-test
  (t/is (= 2
           (sut/find-shortest-transform "HOH" {"e" #{"O"}
                                               "O" #{"HH"}
                                               "H" #{"OH"}})))

  (t/is (= 3
           (sut/find-shortest-transform "OHOH" {"e" #{"O"}
                                                "O" #{"HH"}
                                                "H" #{"OH"}})))

  (t/is (= Integer/MAX_VALUE
           (sut/find-shortest-transform "HOHO" {"e" #{"O"}
                                                "O" #{"HH"}
                                                "H" #{"OH"}}))))
