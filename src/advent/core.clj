(ns advent.core
  (:require [clojure.java.io :as io]
            [clojure.repl :refer :all]))

(defn slurp-resource [file-name]
  (slurp (io/resource file-name)))

(defn resource-line-seq [file-name]
  (->> file-name
       io/resource
       io/reader
       line-seq))

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
