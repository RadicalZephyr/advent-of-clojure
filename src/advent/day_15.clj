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

(defn attribute-total [cookie-proportions values]
  (->> values
       (map * cookie-proportions)
       (apply +)
       (max 0)))

(defn total-score [ingredients cookie-proportions]
  (->> (-> ingredients
           collate-attrs
           (dissoc :name :calories))
       vals
       (map #(attribute-total cookie-proportions %))
       (apply *)))

(defmacro all-ingredient-proportions [count total]
  (if (= count total)
    [(vec (repeat count 1))]
    (let [syms (repeatedly (dec count) #(gensym "x"))]
      `(for [~@(mapcat (fn [sym] `[~sym (range 1 ~(- total count -2))]) syms)
             :let [y# (- ~total ~@syms)]
             :when (and (= ~total (+ ~@syms y#))
                        (> y# 0))]
         [~@syms y#]))))

(defn day-15 [file-name]
  (let [ingredients (->> file-name
                         slurp-resource
                         parse-ingredients)]
    (->> (all-ingredient-proportions 4 100)
         (map #(total-score ingredients %))
         (reduce max))))

(defn count-calories [ingredients cookie-proportions]
  (let [cookie (collate-attrs ingredients)]
    (attribute-total cookie-proportions (:calories cookie))))

(defn day-15-2 [file-name]
  (let [ingredients (->> file-name
                         slurp-resource
                         parse-ingredients)]
    (reduce max
            (for [cookie-proportions (all-ingredient-proportions 4 100)
                  :when (= 500 (count-calories ingredients cookie-proportions))]
              (total-score ingredients cookie-proportions)))))
