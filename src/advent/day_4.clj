(ns advent.day-4
  (:require [clojure.string :as str]
            [clojure.core.reducers :as r]))

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
(defn day-4-1 [key]
  (first (mine-seq key 5)))

(defn day-4-2 [key]
  (first (mine-seq key 6)))
