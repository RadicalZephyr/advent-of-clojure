(ns advent.day-19
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]
            [clojure.string :as str]
            [clojure.core.memoize :as memoize]
            [clojure.set :as set]))

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

(defn replacements-for [molecule [match replacements]]
  (for [idx (indexes-of molecule match)
        replacement replacements]
    (str (subs molecule 0 idx)
         replacement
         (subs molecule (+ idx (count match))))))

(defn all-replacements [molecule replacements]
  (mapcat #(replacements-for molecule %) replacements))

(def memo-all-replacements
  (memoize/lu all-replacements {} :lu/threshold 100000))

(defn day-19 [file-name]
  (let [[replacements molecule]
        (->> file-name
             slurp-resource
             parse-puzzle)]
    (count
     (set
      (all-replacements molecule replacements)))))

(defn molecules-of-length
  ([length base replacements]
   (molecules-of-length length base replacements 0))
  ([length base replacements num-transforms]
   (lazy-seq
    (when (> length (count base))
      (let [ms (memo-all-replacements base replacements)]
        (concat
         (map (fn [m] [num-transforms m]) (filter #(= length (count %)) ms))
         (mapcat #(molecules-of-length length % replacements (inc num-transforms))
                 ms)))))))

(defn find-shortest-transform [final-molecule initial-molecule replacements]
  (let [final-molecule-length (count final-molecule)]
   (->> replacements
        (molecules-of-length final-molecule-length initial-molecule)
        (some #(when (= final-molecule (second %))
                 (first %))))))

(defn invert-replacements [replacements]
  (->> (for [[k vs] replacements
             v vs]
         [v k])
       (apply transform-transforms)))

(defn reductions-from
  ([molecule replacements]
   (reductions-from molecule replacements 1))
  ([molecule replacements num-transforms]
   (let [ms (filter #(or (= % "e")
                         (not (str/includes? % "e")))
                    (all-replacements molecule replacements))]
     (lazy-cat
      (map (fn [m] [num-transforms m]) ms)
      (mapcat #(reductions-from % replacements (inc num-transforms)) ms)))))

(defn find-shortest-transform-fast [initial-molecule final-molecule replacements]
  (->> (invert-replacements replacements)
       (reductions-from initial-molecule)
       (some #(when (= final-molecule (second %))
                (first %)))))

(defn day-19-2 [file-name]
  (let [[replacements final-molecule]
        (->> file-name
             slurp-resource
             parse-puzzle)]
    (find-shortest-transform final-molecule "e" replacements)))
