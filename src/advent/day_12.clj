(ns advent.day-12
  (:require [instaparse.core :as insta]
            [cheshire.core :as json]
            [advent.core :refer [slurp-resource]]))

(def raw-parse-ints
  (insta/parser
   "<json-numbers> = junk? ( number junk )* number? junk?
    number = #'-?[0-9]+'
    <junk> = <#'[^-0-9]+'>"))

(defn parse-ints [s]
  (->> (raw-parse-ints s)
       (insta/transform {:number #(Integer/parseInt %)})))

(defn sum-numbers [s]
  (apply + (parse-ints s)))

(defn day-12 [file]
  (->> file
       slurp-resource
       sum-numbers))

(defn has-red? [m]
  (some #{"red"} (vals m)))

(defn int-seq [data]
  (lazy-seq
   (cond
     (map? data) (if (has-red? data) [] (int-seq (vals data)))
     (sequential? data) (mapcat int-seq data)
     (integer? data) [data])))

(defn day-12-2 [file]
  (->> file
       slurp-resource
       json/parse-string
       int-seq
       (apply +)))
