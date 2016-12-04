(ns advent.day-3
  (:require [advent.core :refer [slurp-resource]]))

(defn next-step [[x y] direction]
  (case direction
    \^ [x (inc y)]
    \v [x (dec y)]
    \> [(inc x) y]
    \< [(dec x) y]
    [x y]))

(defn accumulate-path [acc direction]
  (->> direction
       (next-step (peek acc))
       (conj acc)))

(defn path [directions]
  (->> directions
       (reduce accumulate-path '([0 0]))))

(defn day-3 [file-name]
  (-> file-name
      slurp-resource
      seq
      path
      set
      count))

(defn day-3-2 [file-name]
  (let [directions (-> file-name
                       slurp-resource
                       seq)
        santa-directions (take-nth 2 directions)
        robo-santa-directions (take-nth 2 (drop 1 directions))]
    (-> (concat (path santa-directions)
                (path robo-santa-directions))
        set
        count)))
