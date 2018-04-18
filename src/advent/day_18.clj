(ns advent.day-18
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]))

(def raw-parse-grid
  (insta/parser
   "grid = ( line )+
    line = ( space )+ <'\n'>
    space = #'[.#]'"))

(defn parse-grid [grid-lines]
  (->> grid-lines
       raw-parse-grid
       (insta/transform {:grid vector
                         :line vector
                         :space keyword})))

(defn neighbors [[max-x max-y] [x y]]
  (for [dx (range -1 2)
        dy (range -1 2)
        :let [nx (+ x dx)
              ny (+ y dy)]
        :when (and
               (not= 0 dx dy)
               (>= max-x nx 0)
               (>= max-y ny 0))]
    [nx ny]))

(defn step-board [size grid]
  (let [size (mapv dec size)]
    (->> grid
         (mapcat #(neighbors size %))
         frequencies
         (keep (fn [[coord count]]
                 (if (contains? grid coord)
                   (when (> 4 count 1) coord)
                   (when (= 3 count) coord))))
         set)))

(defn dense->sparse [dense-grid]
  (->> dense-grid
       (keep-indexed
        (fn [x row]
          (seq
           (keep-indexed (fn [y space]
                           (when (= :# space)
                             [x y]))
                         row))))
       (apply concat)
       set))

(defn day-18 [file-name]
  (let [grid (->> file-name
                  slurp-resource
                  parse-grid
                  dense->sparse)]
    (count
     (reduce (fn [grid _]
               (step-board [100 100] grid))
             grid
             (range 100)))))

(defn day-18-2 [file-name]
  (let [grid (->> file-name
                  slurp-resource
                  parse-grid
                  dense->sparse)]
    (count
     (reduce (fn [grid _]
               (conj (step-board [100 100] grid)
                     [0 0] [0 99] [99 0] [99 99]))
             (conj grid [0 0] [0 99] [99 0] [99 99])
             (range 100)))))
