(ns advent.day-9-test
  (:require [advent.day-9 :as sut]
            [clojure.test :as t]
            [loom.graph :as graph]))

(t/deftest parse-map-test
  (t/is (= (graph/weighted-graph ['Albuquerque 'Pittsburgh 12])
           (sut/parse-map "Albuquerque to Pittsburgh = 12")))
  (t/is (= (graph/weighted-graph ['Aa 'Ba 1] ['Ba 'Ca 4])
           (sut/parse-map "Aa to Ba = 1\nBa to Ca = 4"))))

(t/deftest path-length-test
  (let [g (graph/weighted-graph ['Aa 'Bb 1]
                                ['Bb 'Cc 4]
                                ['Aa 'Cc 2])]
    (t/is (= 3 (sut/path-length g '[Bb Aa Cc])))
    (t/is (= 3 (sut/path-length g '[Cc Aa Bb])))
    (t/is (= 5 (sut/path-length g '[Aa Bb Cc])))
    (t/is (= 5 (sut/path-length g '[Cc Bb Aa])))
    (t/is (= 6 (sut/path-length g '[Bb Cc Aa])))
    (t/is (= 6 (sut/path-length g '[Aa Cc Bb])))))

(t/deftest all-tours-test
  (t/is (= '#{[Albuquerque Pittsburgh]
              [Pittsburgh Albuquerque]}
           (set
            (sut/all-tours
             (graph/weighted-graph ['Albuquerque 'Pittsburgh 12])))))

  (t/is (= '#{[Bb Aa Cc]
              [Cc Aa Bb]
              [Aa Bb Cc]
              [Cc Bb Aa]
              [Bb Cc Aa]
              [Aa Cc Bb]}
           (set
            (sut/all-tours
             (graph/weighted-graph ['Aa 'Bb 1]
                                   ['Bb 'Cc 4]
                                   ['Aa 'Cc 2]))))))

(t/deftest path-upper-bound-test
  (t/is (= 14
           (sut/path-upper-bound
            (graph/weighted-graph ['Aa 'Bb 1]
                                  ['Bb 'Cc 4]
                                  ['Aa 'Cc 2])))))

(t/deftest shortest-tour-length-test
  (t/is (= 3
           (sut/shortest-tour-length
            (graph/weighted-graph ['Aa 'Bb 1]
                                  ['Bb 'Cc 4]
                                  ['Aa 'Cc 2])))))
