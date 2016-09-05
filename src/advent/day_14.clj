(ns advent.day-14
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]))

(def raw-parse-reindeer
  (insta/parser
   "<reindeer-list> = ( reindeer <'\n'>?)+
    reindeer = name <' can fly '> speed <' km/s for '> fly-count <' seconds, but then must rest for '> rest-count <' seconds.'>
    name = #'[a-zA-Z]+'
    speed = number
    fly-count = number
    rest-count = number
    number = #'[0-9]+'"))

(defn transform-reindeer [& all]
  (into {} all))

(defn parse-reindeer [reindeer-list]
  (->> reindeer-list
       raw-parse-reindeer
       (insta/transform {:number #(Integer/parseInt %)
                         :reindeer transform-reindeer})))

(defn movement-seq [reindeer]
  (apply concat
         (repeat
          (concat
           (repeat (:fly-count reindeer)
                   (:speed reindeer))
           (repeat (:rest-count reindeer)
                   0)))))

(defn distance-travelled [reindeer seconds]
  (->> (movement-seq reindeer)
       (take seconds)
       (reduce +)))

(defn day-14 [file-name]
  (->> file-name
       slurp-resource
       parse-reindeer
       (map #(distance-travelled % 2503))
       (apply max)))

(defn- accumulating-seq* [total s]
  (lazy-seq
   (if (seq s)
     (let [total (+ total (first s))]
       (cons total
             (accumulating-seq* total
                                (rest s)))))))

(defn accumulating-seq [s]
  (accumulating-seq* 0 s))

(defn position-seq [reindeer]
  (accumulating-seq
   (movement-seq reindeer)))

(defn inc-leader [coll index]
  (if (not= -1 index)
    (update coll index inc)
    coll))

(defn score-seq* [current-scores colls]
  (lazy-seq
   (let [current-positions (map first colls)]
     (when (not-any? nil? current-positions)
       (let [leader-index (.indexOf current-positions (apply max current-positions))
             next-scores (inc-leader current-scores leader-index)]
         (cons next-scores (score-seq* next-scores (map rest colls))))))))

(defn vec-of [n x]
  (vec (repeat n x)))

(defn score-seq [position-seqs]
  (let [initial-scores (vec-of (count position-seqs) 0)]
    (score-seq* initial-scores position-seqs)))

(defn day-14-2 [file-name]
  (->> file-name
       slurp-resource
       parse-reindeer
       (map position-seq)
       score-seq
       (drop 2502)
       first
       (apply max)))
