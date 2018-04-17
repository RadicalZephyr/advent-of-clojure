(ns advent.day-17
  (:require [advent.core :refer [slurp-resource]]))

(def sizes
  [47 46 36 36 32 32 31 30 28 26 19 15 11 11 5 3 3 3 1 1])

(defn- index-combinations
  [n cnt]
  (lazy-seq
   (let [c (vec (cons nil (for [j (range 1 (inc n))] (+ j cnt (- (inc n)))))),
         iter-comb
         (fn iter-comb [c j]
           (if (> j n) nil
               (let [c (assoc c j (dec (c j)))]
                 (if (< (c j) j) [c (inc j)]
                     (loop [c c, j j]
                       (if (= j 1) [c j]
                           (recur (assoc c (dec j) (dec (c j))) (dec j)))))))),
         step
         (fn step [c j]
           (cons (rseq (subvec c 1 (inc n)))
                 (lazy-seq (let [next-step (iter-comb c j)]
                             (when next-step (step (next-step 0) (next-step 1)))))))]
     (step c 1))))

(defn combinations [items t]
  (let [v-items (vec (reverse items))]
    (if (zero? t) (list ())
        (let [cnt (count items)]
          (cond (> t cnt) nil
                (= t 1) (for [item (distinct items)] (list item))
                :else (if (= t cnt)
                        (list (seq items))
                        (map #(map v-items %) (index-combinations t cnt))))))))

(defn count-the-ways [sizes total]
  (->> (range 1 (inc (count (keys sizes))))
       (mapcat #(combinations sizes %))
       (map #(apply + %))
       (filter #(= total %))
       (count)))

(defn day-17 []
  (count-the-ways sizes 150))

(defn count-the-ways2 [sizes total]
  (let [groups (->> (range 1 (inc (count (keys sizes))))
                    (mapcat #(combinations sizes %))
                    (map (juxt count #(apply + %)))
                    (filter #(= total (second %)))
                    (group-by first))
        min-count (apply min (keys groups))]
    (count (get groups min-count))))

(defn day-17-2 []
  (count-the-ways2 sizes 150))
