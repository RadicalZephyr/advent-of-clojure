(ns advent.day-16
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]))

(def raw-parse-aunts
  (insta/parser
   "aunts = ( aunt <'\n'>? )+
    aunt = <'Sue '> number <': '> attrs
    attrs = ( attr <', '>? )+
    attr = attr-name <': '> number
    attr-name = #'[a-z]+'
    number = #'[0-9]+'"))

(defn into-map [& pairs]
  (into {} pairs))

(defn parse-aunts [aunts-list]
  (->> aunts-list
       raw-parse-aunts
       (insta/transform {:aunts into-map
                         :aunt vector
                         :attrs into-map
                         :attr vector
                         :attr-name keyword
                         :number #(Integer/parseInt %)})))

(defn aunt-matches? [clues aunt]
  (= (select-keys clues (keys aunt)) aunt))

(def clues
  {:children 3
   :cats 7
   :samoyeds 2
   :pomeranians 3
   :akitas 0
   :vizslas 0
   :goldfish 5
   :trees 3
   :cars 2
   :perfumes 1})

(defn day-16 [file-name]
  (->> file-name
       slurp-resource
       parse-aunts
       (some #(when (aunt-matches? clues (second %))
                (first %)))))

(defn map-cmp [f m1 m2]
  (every? #(f (% m1) (% m2)) (keys m1)))

(defn map< [m1 m2]
  (map-cmp < m1 m2))

(defn map> [m1 m2]
  (map-cmp > m1 m2))

(defn split-auntributes [attrs]
  [(select-keys attrs [:cats :trees])
   (dissoc attrs :cats :trees :pomeranions :goldfish)
   (select-keys attrs [:pomeranions :goldfish])])

(defn aunt-matches2? [clues aunt]
  (let [relevant-clues (select-keys clues (keys aunt))]
    (every? identity
     (map apply
          [map< = map>]
          (split-auntributes relevant-clues)
          (split-auntributes aunt)
          [[] [] []]))))

(defn day-16-2 [file-name]
  (->> file-name
       slurp-resource
       parse-aunts
       (some #(when (aunt-matches2? clues (second %))
                (first %)))))
