(ns advent.day-1
  (:require [advent.core :refer [slurp-resource]]))

(defn entered-basement? [found-index i sum]
  (and (> found-index i)
       (< sum 0)))

(defn accumulate-sum-index [{:keys [sum found-index]} [i n]]
  (let [sum  (+ sum n)]
    {:sum sum
     :found-index (if (entered-basement? found-index i sum)
                    i found-index)}))

(defn count-parens [parens]
  (->> parens
       seq
       (map #(case % \( 1 \) -1 0))
       (map vector (map inc (range)))
       (reduce accumulate-sum-index
               {:sum 0 :found-index 36rzzz})))

(defn day-1 [file-name]
  (-> file-name
      slurp-resource
      count-parens))
