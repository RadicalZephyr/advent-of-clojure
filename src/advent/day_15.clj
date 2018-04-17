(ns advent.day-15
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]))

(def raw-parse-ingredients
  (insta/parser
   "ingredients = ( ingredient <'\n'>? )+
    ingredient = name <': '> attrs
    <name> = #'[A-Z][a-z]+'
    attrs = ( attr <', '> )* attr
    attr = attr-name <' '> attr-value
    attr-name = #'[a-z]+'
    attr-value = #'-?[0-9]+'"))

(defn- into-map [& pairs]
  (into {} pairs))

(defn parse-ingredients [ingredients-list]
  (->> ingredients-list
       raw-parse-ingredients
       (insta/transform {:ingredients into-map
                         :ingredient vector
                         :attrs into-map
                         :attr vector
                         :attr-name keyword
                         :attr-value #(Integer/parseInt %)})))
