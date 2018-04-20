(ns advent.day-20
  (:require [clojure.math.combinatorics :as combo]
            [clojure.math.numeric-tower :as math]))

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

(defn prime-seq
  ([]
   (prime-seq (drop 2 (range))))
  ([ps]
   (lazy-seq
    (let [p (first ps)]
      (cons p
            (prime-seq (remove #(= 0 (mod % p)) (rest ps))))))))

(def primes-1000
  (vec (take 1000 (prime-seq))))

(defn partitions [x]
  (->> (repeat x 1)
       (combo/partitions)
       (map (fn [xs] (map #(apply + %) xs)))))

(defn make-factorization [primes exps]
  (try
    (apply *
           (map math/expt primes exps))
    (catch ArithmeticException e
      nil)))

(defn highly-factorable-seq [x]
  (let [primes (prime-seq)
        partitions (partitions x)]
    (mapcat #(map math/expt primes %) partitions)))

(defn prime-exps-for [x n]
  (loop [f x
         i 0]
    (if (> f 1)
      (recur (quot f n)
             (inc i))
      i)))

(defn prime-exps-seq
  ([x]
   (prime-exps-seq x (prime-seq)))
  ([x primes]
   (lazy-seq
    (cons (prime-exps-for x (first primes))
          (prime-exps-seq x (rest  primes))))))

(defn prime!-seq []
  (reductions * (prime-seq)))

(defmacro prime-factors [prime-limit]
  (let [primes (prime-seq)
        prime-exp-limits (->> primes
                              (map (juxt #(gensym (str "i" % "-"))
                                         identity
                                         #(prime-exps-for 1500000 %)))
                              (take prime-limit)
                              vec
                              reverse)]
    `(for [~@(mapcat (fn [[sym _ limit]]
                       (vector sym `(range ~limit)))
                     prime-exp-limits)]
       (try
         (* ~@(map (fn [[sym p _]]
                     `(math/expt ~p ~sym))
                   prime-exp-limits))
         (catch ArithmeticException e#
           nil)))))

(defn day-20 [x]
  (let [primes (prime-seq)]
    (->> (range 20)
         (drop 1)
         (mapcat partitions)
         (keep #(make-factorization primes %))
         sort
         (some #(when (>= (factorsum %) x) %)))))

(defn presents-seq2
  ([] (presents-seq2 1))
  ([i]
   (lazy-seq
    (let [num-presents (* 11 i)]
      (concat
       (map #(vector (* i %) num-presents) (range 1 51))
       (presents-seq2 (inc i)))))))

(defn failed-day-20-2 [x]
  (->> (presents-seq2)
       (take 30000000)
       (reduce
        (fn [ret [k presents]]
          (assoc! ret k (+ (get ret k 0) presents)))
        (transient {}))
       (persistent!)
       (filter #(>= (second %) x))
       (map first)
       (apply min)))

(defn factorsum2 [x]
  (->> (range 2 (inc (quot x 2)))
       (keep #(when (and (= 0 (mod x %))
                         (>= 50 (/ x %)))
                (* 11 %)))
       (apply + 11 (* 11 x))))

(defn day-20-2 [x]
  (let [primes (prime-seq)]
    (->> (range 15)
         (drop 1)
         (mapcat partitions)
         (mapcat #(vector % (conj % 0)))
         (mapcat combo/permutations)
         (keep #(make-factorization primes %))
         sort
         (some #(when (>= (factorsum2 %) x) %)))))
