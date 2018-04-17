(ns advent.day-16
  (:require [instaparse.core :as insta]))

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
