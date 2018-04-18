(ns advent.day-20)

(defn factors [x]
  (->> (range 2 (inc (quot x 2)))
       (keep #(when (= 0 (mod x %)) %))
       (apply conj #{1} x)))

(defn presents-seq []
  (->> (range)
       (map (comp #(apply + %) factors inc))))

(defn day-20 [puzzle-num]
  (let [puzzle-name (/ puzzle-num 10)]
    (->> (presents-seq)
         (drop-while #(> puzzle-num %))
         first)))
