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

(deftest next-step-test
  (is (= [0 1]
         (next-step [0 0] \^)))
  (is (= [1 0]
         (next-step [0 0] \>)))
  (is (= [-1 0]
         (next-step [0 0] \<)))
  (is (= [0 -1]
         (next-step [0 0] \v))))

(deftest accumulate-path-test
  (is (= [[0 1] [0 0]]
         (accumulate-path '([0 0]) \^)))
  (is (= [[1 -1] [1 0]]
         (accumulate-path '([1 0]) \v))))

(deftest path-test
  (is (= [[0 0] [1 0] [1 1] [0 1] [0 0]]
         (path (seq "^>v<")))))

(deftest count-vowels-test
  (is (= 0 (count-vowels "bkt")))
  (is (= 1 (count-vowels "a")))
  (is (= 3 (count-vowels "aei")))
  (is (= 3 (count-vowels "aaa"))))

(deftest three-vowels?-test
  (is (not (at-least-three-vowels? "bkt")))
  (is (not (at-least-three-vowels? "abikt")))

  (is (at-least-three-vowels? "uie"))
  (is (at-least-three-vowels? "aaa"))
  (is (at-least-three-vowels? "aaaa")))

(deftest repeated-letter?-test
  (is (not (repeated-letter? "ab")))
  (is (not (repeated-letter? "aba")))

  (is (repeated-letter? "aa"))
  (is (repeated-letter? "bb")))

(deftest contains-bad-pair?-test
  (is (not (contains-bad-pair? "acbdpxqy")))

  (is (contains-bad-pair? "ab"))
  (is (contains-bad-pair? "cd"))
  (is (contains-bad-pair? "pq"))
  (is (contains-bad-pair? "xy")))

(deftest nice?-test
  (is (not (nice? "jchzalrnumimnmhp")))
  (is (not (nice? "haegwjzuvuyypxyu")))
  (is (not (nice? "dvszwmarrgswjxmb")))

  (is (nice? "ugknbfddgicrmopn"))
  (is (nice? "aaa")))

(deftest two-repeat-no-overlap?-test
  (is (not (two-repeat-no-overlap? "aaa")))

  (is (two-repeat-no-overlap? "xyxy"))
  (is (two-repeat-no-overlap? "aabcdefgaa")))

(deftest sandwich-triple?-test
  (is (not (sandwich-triple? "abcd")))
  (is (not (sandwich-triple? "2l',ucd")))

  (is (sandwich-triple? "xyx"))
  (is (sandwich-triple? "aaa"))
  (is (sandwich-triple? "abcdefeghi")))
