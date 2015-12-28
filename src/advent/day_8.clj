(ns advent.day-8
  (:require [clojure.repl :refer :all]
            [clojure.string :as str]
            [advent.core :refer [resource-line-seq]]))

(defn count-code-chars [s]
  (count s))

(defn strip-surrounding-quotes [s]
  (when-let [[_ inner-text] (re-find #"\"(.*)\"" s)]
      inner-text))

(defn unescape-quotes [s]
  (str/replace s #"\\\"" "\""))

(defn unescape-backslashes [s]
  (str/replace s #"\\\\" "\\\\"))

(defn interpret-unicode [s]
  (str/replace s #"\\x([0-9a-fA-F]{2})"
               (fn [[_ code-point]]
                 (-> code-point
                     (Long/parseLong 16)
                     Character/toChars
                     String.))))

(defn count-memory-chars [s]
  (some-> s
          strip-surrounding-quotes
          unescape-quotes
          unescape-backslashes
          interpret-unicode
          count))

(defn sum [num-seq]
  (reduce + 0 num-seq))

(defn day-8 [file-name]
  (let [lines (resource-line-seq file-name)
        code-chars (map count-code-chars lines)
        memory-chars (map count-memory-chars lines)]
    (- (sum code-chars)
       (sum memory-chars))))

(defn count-encoded-chars [s]
  (count (pr-str s)))

(defn day-8-2 [file-name]
  (let [lines (resource-line-seq file-name)
        encoded-chars (map count-encoded-chars lines)
        code-chars (map count-code-chars lines)]
    (- (sum encoded-chars)
       (sum code-chars))))
