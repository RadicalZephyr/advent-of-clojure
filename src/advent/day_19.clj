(ns advent.day-19
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]))

(def raw-parse-puzzle
  (insta/parser
   "puzzle = transforms <'\n'> molecule <'\n'>
    transforms = ( transform ) +
    transform = molecule <' => '> molecule <'\n'>
    <molecule> = #'[A-Z]+'"))

(defn transform-transforms [& ts]
  (->> ts
       (group-by first)
       (map (fn [[k vs]]
              [k (set (map second vs))]))
       (into {})))

(defn parse-puzzle [puzzle-input]
  (->> puzzle-input
       raw-parse-puzzle
       (insta/transform {:puzzle vector
                         :transforms transform-transforms
                         :transform vector})))
