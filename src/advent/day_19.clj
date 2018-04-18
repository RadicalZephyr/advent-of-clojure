(ns advent.day-19
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]
            [clojure.string :as str]))

(def raw-parse-puzzle
  (insta/parser
   "puzzle = transforms <'\n'> molecule <'\n'>
    transforms = ( transform ) +
    transform = molecule <' => '> molecule <'\n'>
    <molecule> = #'[a-zA-Z]+'"))

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

(defn indexes-of
  ([s value]
   (indexes-of s value 0))
  ([s value from-index]
   (lazy-seq
    (when-let [next-index (str/index-of s value from-index)]
      (cons next-index
            (indexes-of s value (inc next-index)))))))

(defn all-replacements [molecule [match replacements]]
  (for [idx (indexes-of molecule match)
        replacement replacements]
    (str (subs molecule 0 idx)
         replacement
         (subs molecule (+ idx (count match))))))

(defn day-19 [file-name]
  (let [[replacements molecule]
        (->> file-name
             slurp-resource
             parse-puzzle)]
    (->> replacements
         (mapcat #(all-replacements molecule %))
         set
         count)))
