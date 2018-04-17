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



(defn parse-ingredients [ingredients-list]
  (->> ingredients-list
       raw-parse-ingredients
       (insta/transform {:ingredients (fn into-set [& maps] (into #{} maps))
                         :ingredient #(assoc %2 :name %1)
                         :attrs (fn into-map [& pairs] (into {} pairs))
                         :attr vector
                         :attr-name keyword
                         :attr-value #(Integer/parseInt %)})))

(defn- multiply-cookie [cookie attrs]
  (reduce (fn [cookie [k v]]
            (update cookie k (fnil conj []) v))
          cookie
          attrs))

(defn collate-attrs [cookie]
  (reduce multiply-cookie
          {}
          cookie))

(defn total-score [ingredients cookie-proportions]
  (->> (-> ingredients
           collate-attrs
           (dissoc :calories))
       vals
       (map #(map * cookie-proportions %))
       (map #(max 0 (apply + %)))
       (apply *)))
