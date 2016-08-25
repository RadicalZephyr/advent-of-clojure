(ns advent.day-9-test
  (:require [advent.day-9 :as sut]
            [clojure.test :as t]
            [loom.graph :as graph]))

(t/deftest parse-map-test
  (t/is (= (graph/weighted-graph ['Albuquerque 'Pittsburgh 12])
           (sut/parse-map "Albuquerque to Pittsburgh = 12")))
  (t/is (= (graph/weighted-graph ['Aa 'Ba 1] ['Ba 'Ca 4])
           (sut/parse-map "Aa to Ba = 1\nBa to Ca = 4"))))
