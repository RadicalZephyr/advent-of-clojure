(ns advent.day-9
  (:require [advent.core :refer [slurp-resource]]
            [clojure.repl :refer :all]
            [instaparse.core :as insta]
            [loom.graph :as graph]
            [clojure.math.combinatorics :as combo]))

(def raw-parse-map
  (insta/parser
   "map = (route <'\n'>)* route?
    route = origin <' to '> destination <' = '> distance
    <origin> = city-name
    <destination> = city-name
    city-name = #'[a-zA-Z]+'
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

(defn path-length [g path]
  (->> path
       (partition 2 1)
       (map vec)
       (map (partial graph/weight g))
       (apply +)))

(defn all-tours [g]
  (combo/permutations
   (graph/nodes g)))

(defn path-upper-bound [g]
  (->> g
       graph/edges
       (map (partial graph/weight g))
       (apply +)))

(defn shortest-tour [g]
  (apply min-key (partial path-length g) (all-tours g)))

(defn shortest-tour-length [g]
  (path-length g (shortest-tour g)))

(defn longest-tour [g]
  (apply max-key (partial path-length g) (all-tours g)))

(defn longest-tour-length [g]
  (path-length g (longest-tour g)))

(defn day-9 [file-name]
  (-> file-name
      slurp-resource
      parse-map
      shortest-tour-length))

(defn day-9-2 [file-name]
  (-> file-name
      slurp-resource
      parse-map
      longest-tour-length))
