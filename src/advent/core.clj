(ns advent.core
  (:require [clojure.java.io :as io]
            [clojure.repl :refer :all]))

(defn slurp-resource [file-name]
  (slurp (io/resource file-name)))

(defn resource-line-seq [file-name]
  (->> file-name
       io/resource
       io/reader
       line-seq))
