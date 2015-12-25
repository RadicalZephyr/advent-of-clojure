(ns advent.day-6
  (:require [clojure.set :as set]
            [instaparse.core :as insta]
            [advent.core :refer [resource-line-seq]]))

(def raw-parse-instructions
  (insta/parser
   "<parse-instructions> = command <' '> coord <' through '> coord
    command = 'toggle' / <'turn '> ('on' | 'off')
    coord = digits <','> digits
    <digits> = #'\\d+'"))

(defn transform-coord [x y]
  [(Integer/parseInt x)
   (Integer/parseInt y)])

(defn parse-instructions [instructions]
  (->> (raw-parse-instructions instructions)
       (insta/transform {:coord transform-coord
                         :command keyword})))

(defn coords->set [[x1 y1] [x2 y2]]
  (set
   (for [x (range x1 (inc x2))
         y (range y1 (inc y2))]
     [x y])))

(defmulti do-instruction
  (fn [lights [method _ _]]
    method)
  :default :default)

(defmethod do-instruction :on [lights [_ c1 c2]]
  (set/union lights (coords->set c1 c2)))

(defmethod do-instruction :off [lights [_ c1 c2]]
  (set/difference lights (coords->set c1 c2)))

(defmethod do-instruction :toggle [lights [_ c1 c2]]
  (let [toggle-area (coords->set c1 c2)
        on-lights  (set/difference toggle-area lights)
        off-lights (set/intersection lights toggle-area)]
    (-> lights
        (set/union on-lights)
        (set/difference off-lights))))

(defmethod do-instruction :default [lights _]
  lights)

(defn day-6 [file-name]
  (->> file-name
       resource-line-seq
       (map parse-instructions)
       (reduce do-instruction #{})
       count))

(defn light-grid []
  (int-array 1000000))

(defn xy->index [x y]
  (+ x
     (* y 1000)))

(defn get-light [array x y]
  (aget array (xy->index x y)))

(defn set-light [array x y value]
  (aset-int array (xy->index x y)
            value))

(defn update-light [array x y f]
  (set-light array x y
             (f (get-light array x y))))

(defn light-on [array x y]
  (update-light array x y inc))

(defn light-off [array x y]
  (update-light array x y
                (fn [val]
                  (let [new-val (dec val)]
                    (if (= new-val 0)
                      0 new-val)))))

(defn light-toggle [array x y]
  (update-light array x y (partial + 2)))

(defn do-instruction-2 [lights [method c1 c2]]
  (let [update-fn (case method
                    :on light-on
                    :off light-off
                    :toggle light-toggle)]
    (doseq [[x y] (coords->set c1 c2)]
      (update-fn lights x y))
    lights))

(defn sum-brightness [^ints xs]
  (areduce xs i ret (int 0)
           (+ ret (aget xs i))))

(defn day-6-2 [file-name]
  (->> file-name
       resource-line-seq
       (map parse-instructions)
       (reduce do-instruction-2 (light-grid))
       sum-brightness))
