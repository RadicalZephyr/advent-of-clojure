(ns advent.day-9
  (:require [advent.core :refer [resource-line-seq]]
            [clojure.repl :refer :all]
            [instaparse.core :as insta]
            [loom.graph :as graph]))

(def raw-parse-map
  (insta/parser
   "map = (route <'\n'>)* route?
    route = origin <' to '> destination <' = '> distance
    <origin> = city-name
    <destination> = city-name
    city-name = #'[A-z][a-z]+'
    distance = #'[0-9]+'"))

(defn transform-distance [magnitude]
  (Integer/parseInt magnitude))

(defn transform-map [& routes]
  (apply graph/weighted-graph routes))

(defn parse-map [map-description]
  (->> (raw-parse-map map-description)
       (insta/transform {:map transform-map
                         :route vector
                         :city-name symbol
                         :distance transform-distance})))
