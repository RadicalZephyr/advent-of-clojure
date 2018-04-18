(ns advent.day-18
  (:require [advent.core :refer [slurp-resource]]
            [instaparse.core :as insta]))

(def raw-parse-grid
  (insta/parser
   "grid = ( line )+
    line = ( space )+ <'\n'>
    space = #'[.#]'"))

(defn parse-grid [grid-lines]
  (->> grid-lines
       raw-parse-grid
       (insta/transform {:grid vector
                         :line vector
                         :space keyword})))
