(ns advent.day-14-test
  (:require [advent.day-14 :as sut]
            [clojure.test :as t]))

(t/deftest parse-reindeer-test
  (t/is (= [{:name "Rudolph"
             :speed 22
             :fly-count 8
             :rest-count 165}]
           (sut/parse-reindeer "Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.")))

  (t/is (= [{:name "Rudolph"
              :speed 22
              :fly-count 8
             :rest-count 165}
            {:name "Steiner"
             :speed 26
             :fly-count 81
             :rest-count 16}]
           (sut/parse-reindeer "Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.\nSteiner can fly 26 km/s for 81 seconds, but then must rest for 16 seconds."))))

(t/deftest distance-travelled-test
  (let [reindeer {:name "Rudolph"
                  :speed 22
                  :fly-count 4
                  :rest-count 10}]
    (t/is (= 22
             (sut/distance-travelled reindeer 1)))
    (t/is (= 44
             (sut/distance-travelled reindeer 2)))
    (t/is (= 88
             (sut/distance-travelled reindeer 4)))
    (t/is (= 88
             (sut/distance-travelled reindeer 5)))
    (t/is (= 110
             (sut/distance-travelled reindeer 15)))
    (t/is (= 132
             (sut/distance-travelled reindeer 16)))
    (t/is (= 176
             (sut/distance-travelled reindeer 19)))
    (t/is (= 176
             (sut/distance-travelled reindeer 20))))

  (t/is (= 10
           (sut/distance-travelled {:name "Rudolph"
                                    :speed 1
                                    :fly-count 1
                                    :rest-count 1}
                                   20))))

(t/deftest accumulating-seq-test
  (t/is (= [1 2 3]
           (sut/accumulating-seq (repeat 3 1))))
  (t/is (= [2 4 6]
           (sut/accumulating-seq (repeat 3 2)))))

(t/deftest score-seq-test
  (t/is (= [[1 0]
            [2 0]
            [3 0]]
           (sut/score-seq [[2 3 4]
                           [1 2 3]])))
  (t/is (= [[1 0]
            [2 0]
            [2 1]]
           (sut/score-seq [[10 11 12]
                           [1 2 14]]))))
