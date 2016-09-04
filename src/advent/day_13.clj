(ns advent.day-13
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]
            [loom.graph :as loom]
            [clojure.math.combinatorics :as combo]
            [advent.day-9 :as tours]))

(def raw-parse-guests
  (insta/parser
   "guest-list = (guest-relation <'\n'> )* guest-relation <'\n'>?
    guest-relation = name <' would '> change <' happiness units by sitting next to '> name <'.'>
    name = #'[a-zA-Z]+'
    change = sign <' '> amount
    sign = 'gain' | 'lose'
    amount = #'[0-9]+'"))

(def transform-sign
  {"gain" +
   "lose" -})

(defn transform-relation [from amount to]
  [from to amount])

(defn parse-guests [guest-list]
  (->> guest-list
       raw-parse-guests
       (insta/transform {:guest-list loom/weighted-digraph
                         :guest-relation transform-relation
                         :name symbol
                         :sign transform-sign
                         :amount #(Integer/parseInt %)
                         :change #(%1 %2)})))

(defn- total-weight [g a b]
  (+ (loom/weight g a b)
     (loom/weight g b a)))

(defn- new-entry [g [a b]]
  [a b (total-weight g a b)])

(defn collapse-digraph [dg]
  (->> (combo/combinations (loom/nodes dg) 2)
       (map (partial new-entry dg))
       (apply loom/weighted-graph)))

(defn maximize-happiness [guest-list]
  (->> guest-list
       parse-guests
       collapse-digraph
       tours/longest-tour-length))

(defn day-13 [file-name]
  (-> file-name
      slurp-resource
      maximize-happiness))
