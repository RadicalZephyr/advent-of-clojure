(ns advent.day-7
  (:require [clojure.repl :refer :all]
            [clojure.string :as str]
            [instaparse.core :as insta]
            [loom.graph :as graph]))

(def raw-parse-wires
  (insta/parser
   "circuit = (wire <'\n'>)* wire?
    wire = input <' -> '> output
    input = value | operator
    output = id
    operator = not / value op-name value
    not = <'NOT '> value
    op-name =  <' '> ( 'AND' | 'OR' | 'LSHIFT' | 'RSHIFT' ) <' '>
    <value> = num-lit | id
    id = #'[a-z]+'
    num-lit = #'\\d+'"))

(defn transform-operator
  ([not-op] not-op)
  ([l op r]
   [(keyword op)
    l r]))

(defn transform-circuit [& wires]
  (reduce (fn [circuit {:keys [input output]}]
            (assoc circuit output input))
          {} wires))

(defn parse-wires [wires-description]
  (->> (raw-parse-wires wires-description)
       (insta/transform {:circuit transform-circuit
                         :wire (fn [& io] (into {} io))
                         :operator transform-operator
                         :op-name str/lower-case
                         :id symbol
                         :num-lit #(Integer/parseInt %)})))

(defn symbols [data]
  (filter symbol? (flatten data)))

(defn wire->dependencies [[wire-name input]]
  (for [sym (symbols input)]
    [wire-name sym]))

(defn circuit->wire-dependencies [circuit]
  (->> circuit
       (mapcat wire->dependencies)
       (apply graph/digraph)))

(defn resolve-circuit [circuit]
  )
