(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest count-parens-test
  (is (= {:sum 0 :found-index 36rzzz}
         (count-parens "(())")))
  (is (= {:sum 3 :found-index 36rzzz}
         (count-parens "(((")
         (count-parens "(()(()(")))
  (is (= {:sum 3 :found-index 1}
         (count-parens "))(((((")))
  (is (= {:sum -1 :found-index 3}
         (count-parens "())")))
  (is (= {:sum -1 :found-index 1}
         (count-parens "))(")))
  (is (= {:sum -3 :found-index 1}
         (count-parens ")))")
         (count-parens ")())())"))))
