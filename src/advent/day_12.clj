(ns advent.day-12
  (:require [instaparse.core :as insta]
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
