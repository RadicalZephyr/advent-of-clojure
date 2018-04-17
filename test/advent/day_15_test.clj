(ns advent.day-15-test
  (:require [advent.day-15 :as sut]
            [clojure.test :as t]))

(t/deftest parse-ingredients-test
  (t/is (= #{{:name "Butterscotch"
               :capacity -1
               :durability -2
               :flavor 6
               :texture 3
               :calories 8}}
           (sut/parse-ingredients "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8")))

  (t/is (= #{{:name "Butterscotch"
              :capacity -1
              :durability -2
              :flavor 6
              :texture 3
              :calories 8}
             {:name "Cinnamon"
              :capacity 2
              :durability 3
              :flavor -2
              :texture -1
              :calories 3}}
           (sut/parse-ingredients
            (str "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n"
                 "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3\n")))))

(t/deftest collate-attrs-test
  (t/is (= {:capacity [8 6]
            :flavor [-4 72]}
           (sut/collate-attrs {4 {:capacity 2 :flavor -1}
                               6 {:capacity 1 :flavor 12}}))))

(t/deftest total-score-test
  (t/is (= (* (+ 6 8) (+ -4 72))
           (sut/total-score {4 {:capacity 2 :flavor -1}
                             6 {:capacity 1 :flavor 12}})))

  (t/is (= 0
           (sut/total-score {1 {:capacity -1 :flavor 1}
                             3 {:capacity -1 :flavor 2}})))

  (t/is (= 7
           (sut/total-score {1 {:calories -1 :flavor 1}
                             3 {:calories -1 :flavor 2}}))))
