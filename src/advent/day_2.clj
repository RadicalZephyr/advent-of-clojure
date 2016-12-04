(ns advent.day-2
  (:require [advent.core :refer [resource-line-seq]]
            [clojure.string :as str]))

(defn split-specs [box-spec]
  (->> (str/split box-spec #"x" 3)
       (map #(Integer/parseInt %))
       sort))

(defn area [l w]
  (* l w))

(defn box-area+ [[l w h]]
  (+ (* 2 (+ (area l w)
             (area w h)
             (area l h)))
     (area l w)))

(defn ribbon-length+ [[l w h]]
  (+ (* 2 (+ l w))
     (* l w h)))

(defn day-2 [file-name]
  (->> file-name
       resource-line-seq
       (map (comp (juxt box-area+ ribbon-length+) split-specs))
       (reduce #(map + %1 %2) [0 0])))
