(ns advent.day-20)

(defn factorsum [x]
  (->> (range 2 (inc (quot x 2)))
       (keep #(when (= 0 (mod x %)) %))
       (apply + 1 x)))

(defn presents-seq
  ([]
   (presents-seq 1))
  ([start]
   (->> (range)
        (map (comp factorsum #(+ start %))))))

(defn search-factors [puzzle-num]
  (let [puzzle-name (/ puzzle-num 10)]
    (->> (presents-seq)
         (drop-while #(> puzzle-num %))
         first)))

(defn factorial-seq
  ([]
   (factorial-seq 1 2))
  ([x i]
   (lazy-seq
    (cons x
          (factorial-seq (* x i) (inc i))))))

(defn partitions [x]
  (set
   (lazy-cat
    [(repeat x 1) [x]])))
