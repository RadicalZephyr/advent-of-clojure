(ns advent.day-15-test
  (:require [advent.day-15 :as sut]
            [clojure.test :as t]))

(t/deftest parse-ingredients-test
  (t/is (= {"Butterscotch" {:capacity -1
                            :durability -2
                            :flavor 6
                            :texture 3
                            :calories 8}}
           (sut/parse-ingredients "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8")))

  (t/is (= {"Butterscotch" {:capacity -1
                            :durability -2
                            :flavor 6
                            :texture 3
                            :calories 8}
            "Cinnamon" {:capacity 2
                        :durability 3
                        :flavor -2
                        :texture -1
                        :calories 3}}
           (sut/parse-ingredients
            (str "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n"
                 "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3\n")))))
