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

(deftest box-area+-test
  (is (= 43
         (box-area+ [1 1 10])))
  (is (= 58
         (box-area+ [2 3 4]))))

(deftest ribbon-length+-test
  (is (= 34
         (ribbon-length+ [2 3 4])))
  (is (= 14
         (ribbon-length+ [1 1 10]))))
