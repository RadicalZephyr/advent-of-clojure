(ns advent.day-1-test
  (:require [advent.day-1 :as sut]
            [clojure.test :as t]))

(t/deftest count-parens-test
  (t/is (= {:sum 0 :found-index 36rzzz}
           (sut/count-parens "(())")))
  (t/is (= {:sum 3 :found-index 36rzzz}
           (sut/count-parens "(((")
           (sut/count-parens "(()(()(")))
  (t/is (= {:sum 3 :found-index 1}
           (sut/count-parens "))(((((")))
  (t/is (= {:sum -1 :found-index 3}
           (sut/count-parens "())")))
  (t/is (= {:sum -1 :found-index 1}
           (sut/count-parens "))(")))
  (t/is (= {:sum -3 :found-index 1}
           (sut/count-parens ")))")
           (sut/count-parens ")())())"))))
