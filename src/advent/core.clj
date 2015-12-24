(ns advent.core
  (:require [clojure.java.io :as io]
            [clojure.repl :refer :all]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(defn slurp-resource [file-name]
  (slurp (io/resource file-name)))

(defn entered-basement? [found-index i sum]
  (and (> found-index i)
       (< sum 0)))

(defn accumulate-sum-index [{:keys [sum found-index]} [i n]]
  (let [sum  (+ sum n)]
    {:sum sum
     :found-index (if (entered-basement? found-index i sum)
                    i found-index)}))

(defn count-parens [parens]
  (->> parens
      seq
      (map #(case % \( 1 \) -1 0))
      (map vector (map inc (range)))
      (reduce accumulate-sum-index
              {:sum 0 :found-index 36rzzz})))

(defn day-1 [file-name]
  (-> file-name
      slurp-resource
      count-parens))

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
       io/resource
       io/reader
       line-seq
       (map (comp (juxt box-area+ ribbon-length+) split-specs))
       (reduce #(map + %1 %2) [0 0])))

(defn next-step [[x y] direction]
  (case direction
    \^ [x (inc y)]
    \v [x (dec y)]
    \> [(inc x) y]
    \< [(dec x) y]
    [x y]))

(defn accumulate-path [acc direction]
  (->> direction
       (next-step (peek acc))
       (conj acc)))

(defn path [directions]
  (->> directions
       (reduce accumulate-path '([0 0]))))

(defn day-3 [file-name]
  (-> file-name
      slurp-resource
      seq
      path
      set
      count))

(defn day-3-2 [file-name]
  (let [directions (-> file-name
                       slurp-resource
                       seq)
        santa-directions (take-nth 2 directions)
        robo-santa-directions (take-nth 2 (drop 1 directions))]
    (-> (concat (path santa-directions)
                (path robo-santa-directions))
        set
        count)))

(defn md5
  "Generate a md5 checksum for the given string"
  [token]
  (let [hash-bytes
        (doto (java.security.MessageDigest/getInstance "MD5")
          (.reset)
          (.update (.getBytes token)))]
    (.toString
     (new java.math.BigInteger 1 (.digest hash-bytes)) ; Positive and the size of the number
     16)))

(defn strip-key [key hash]
  (str/replace hash key ""))

(defn mine-seq [key number-of-zeroes]
  (r/fold conj
          (->> (map vector
                    (repeat key)
                    (map inc (range)))
               (take 999999)
               (r/map #(apply str %))
               (r/map (juxt identity (comp count md5)))
               (r/filter #(= (- 32 number-of-zeroes) (second %)))
               (r/map first)
               (r/map (partial strip-key key))
               (r/map #(Long/parseLong %))
               (r/take 1))))

;; My key: bgvyzdsv
(defn day-4 [key]
  [(first (mine-seq key 5))
   (first (mine-seq key 6))])
