(ns advent.day-7
  (:require [clojure.java.io :as io]
            [clojure.repl :refer :all]
            [clojure.string :as str]
            [instaparse.core :as insta]
            [loom.alg :as alg]
            [loom.graph :as graph]
            [advent.core :refer [slurp-resource]]))

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

(defn symbols [& data]
  (filter symbol? (flatten data)))

(defn wire->dependencies [[wire-name input]]
  (for [sym (symbols input)]
    [sym wire-name]))

(defn circuit->wire-dependencies [circuit]
  (->> circuit
       (mapcat wire->dependencies)
       (apply graph/digraph)))

(defmulti propagate-input
  (fn [resolved-circuit input]
    (cond
      (or (number? input)
          (symbol? input)) :number-or-symbol

      (vector? input)
      (let [[op-key] input] op-key)
      :else :default))
  :default :default)

(defn lookup-wire-or-value [circuit wire-or-value]
  (get circuit wire-or-value wire-or-value))

(defmethod propagate-input :default [circuit input]
  (throw (ex-info (format "Unrecognized operation '%s' requested." input)
                  {:input input :circuit circuit})))

(defmethod propagate-input :number-or-symbol
  [circuit value]
  (lookup-wire-or-value circuit value))

(defmethod propagate-input :not [circuit [_ value]]
  (bit-not (lookup-wire-or-value circuit value)))

(defmacro defbinary-circuit-op [op-name fn-op]
  `(defmethod propagate-input ~(keyword op-name)
     [circuit# [~'_ left# right#]]
     (~fn-op
      (lookup-wire-or-value circuit# left#)
      (lookup-wire-or-value circuit# right#))))

(defbinary-circuit-op and bit-and)
(defbinary-circuit-op or bit-or)
(defbinary-circuit-op lshift bit-shift-left)
(defbinary-circuit-op rshift unsigned-bit-shift-right)

(defn bigint? [value]
  (= clojure.lang.BigInt
     (class value)))

(defn as-unsigned-short [value]
  (bit-and 0xffff (int value)))

(defn resolve-wire [circuit wire-name]
  (assoc circuit
         wire-name
         (as-unsigned-short
          (propagate-input circuit
                           (get circuit wire-name)))))

(defn resolve-circuit [circuit]
  (let [dependencies (circuit->wire-dependencies circuit)]
    (loop [result circuit
           wire-names (alg/topsort dependencies)]
      (if-let [current-wire (first wire-names)]
        (recur (resolve-wire result current-wire)
               (rest wire-names))
        result))))

(defn read-circuit [file-name]
  (-> file-name
      slurp-resource
      parse-wires))

(defn day-7 [file-name]
  (-> file-name
      read-circuit
      resolve-circuit
      (get 'a)))

(defn day-7-2 [file-name]
  (let [a-value (day-7 file-name)]
    (-> file-name
        read-circuit
        (assoc 'b a-value)
        resolve-circuit
        (get 'a))))
