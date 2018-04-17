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
  (t/is (= {:capacity [2 1]
            :flavor [-1 12]}
           (sut/collate-attrs [{:capacity 2 :flavor -1}
                               {:capacity 1 :flavor 12}]))))

(t/deftest total-score-test
  (t/is (= (* (+ 8 6) (+ -4 72))
           (sut/total-score [{:capacity 2 :flavor -1}
                             {:capacity 1 :flavor 12}]
                            [4 6])))

  (t/is (= 0
           (sut/total-score [{:capacity -1 :flavor 1}
                             {:capacity -1 :flavor 2}]
                            [1 1])))

  (t/is (= 7
           (sut/total-score [{:calories -1 :flavor 1}
                             {:calories -1 :flavor 2}]
                            [1 3]))))

(t/deftest all-ingredient-proportions-test
  (t/is (= [[1 1]]
           (sut/all-ingredient-proportions 2 2)))
  (t/is (= [[1 2] [2 1]]
           (sut/all-ingredient-proportions 2 3)))
  (t/is (= [[1 1 2] [1 2 1] [2 1 1]]
           (sut/all-ingredient-proportions 3 4)))
  (t/is (= [[1 1 1 1]]
           (sut/all-ingredient-proportions 4 4)))
  (t/is (= [[1 3] [2 2] [3 1]]
           (sut/all-ingredient-proportions 2 4)))
  (t/is (= [[1 1 2] [1 2 1] [2 1 1]]
           (sut/all-ingredient-proportions 3 4)))
  (t/is (= [[1 1 3] [1 2 2] [1 3 1] [2 1 2] [2 2 1] [3 1 1]]
           (sut/all-ingredient-proportions 3 5)))
  (t/is (= [[1 9] [2 8] [3 7] [4 6] [5 5]
            [6 4] [7 3] [8 2] [9 1]]
           (sut/all-ingredient-proportions 2 10))))
