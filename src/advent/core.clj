(ns advent.core
  (:require [clojure.java.io :as io]
            [clojure.repl :refer :all]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(defn slurp-resource [file-name]
  (slurp (io/resource file-name)))

(defn resource-line-seq [file-name]
  (->> file-name
       io/resource
       io/reader
       line-seq))

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

(defn count-vowels [s]
  (->> s
       seq
       (filter #{\a \e \i \o \u})
       count))

(defn at-least-three-vowels? [s]
  (>= (count-vowels s) 3))

(defn repeated-letter? [s]
  (re-find #"(.)\1" s))

(defn contains-bad-pair? [s]
  (re-find #"ab|cd|pq|xy" s))

(defn nice? [s]
  (and (at-least-three-vowels? s)
       (repeated-letter? s)
       (not (contains-bad-pair? s))))

(defn day-5 [file-name]
  (->> file-name
       resource-line-seq
       (filter nice?)
       count))

(defn two-repeat-no-overlap? [s]
  (re-find #"(..)(?=.*\1)" s))

(defn sandwich-triple? [s]
  (re-find #"(.)(?=.\1)" s))

(defn nice?-2 [s]
  (and (two-repeat-no-overlap? s)
       (sandwich-triple? s)))

(defn day-5-2 [file-name]
  (->> file-name
       resource-line-seq
       (filter nice?-2)
       count))
