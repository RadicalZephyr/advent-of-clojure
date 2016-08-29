(ns advent.day-10)

(defn stringify [chars]
  (apply str chars))

(defn run-length-encode [s]
  (let [initial (first s)
        run (take-while #(= initial %) s)]
    (str (count run) initial)))

(defn next-sequence [s]
  (->> s
       (partition-by identity)
       (map stringify)
       (mapcat run-length-encode)
       stringify))

(defn day-10 [initial]
  (count (nth (iterate next-sequence initial) 40)))

(defn day-10-2 [initial]
  (count (nth (iterate next-sequence initial) 50)))
