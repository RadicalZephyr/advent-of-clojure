(ns advent.day-5-test
  (:require [advent.day-5 :as sut]
            [clojure.test :as t]))

(t/deftest count-vowels-test
  (t/is (= 0 (sut/count-vowels "bkt")))
  (t/is (= 1 (sut/count-vowels "a")))
  (t/is (= 3 (sut/count-vowels "aei")))
  (t/is (= 3 (sut/count-vowels "aaa"))))

(t/deftest three-vowels?-test
  (t/is (not (sut/at-least-three-vowels? "bkt")))
  (t/is (not (sut/at-least-three-vowels? "abikt")))

  (t/is (sut/at-least-three-vowels? "uie"))
  (t/is (sut/at-least-three-vowels? "aaa"))
  (t/is (sut/at-least-three-vowels? "aaaa")))

(t/deftest repeated-letter?-test
  (t/is (not (sut/repeated-letter? "ab")))
  (t/is (not (sut/repeated-letter? "aba")))

  (t/is (sut/repeated-letter? "aa"))
  (t/is (sut/repeated-letter? "bb")))

(t/deftest contains-bad-pair?-test
  (t/is (not (sut/contains-bad-pair? "acbdpxqy")))

  (t/is (sut/contains-bad-pair? "ab"))
  (t/is (sut/contains-bad-pair? "cd"))
  (t/is (sut/contains-bad-pair? "pq"))
  (t/is (sut/contains-bad-pair? "xy")))

(t/deftest nice?-test
  (t/is (not (sut/nice? "jchzalrnumimnmhp")))
  (t/is (not (sut/nice? "haegwjzuvuyypxyu")))
  (t/is (not (sut/nice? "dvszwmarrgswjxmb")))

  (t/is (sut/nice? "ugknbfddgicrmopn"))
  (t/is (sut/nice? "aaa")))

(t/deftest two-repeat-no-overlap?-test
  (t/is (not (sut/two-repeat-no-overlap? "aaa")))

  (t/is (sut/two-repeat-no-overlap? "xyxy"))
  (t/is (sut/two-repeat-no-overlap? "aabcdefgaa")))

(t/deftest sandwich-triple?-test
  (t/is (not (sut/sandwich-triple? "abcd")))
  (t/is (not (sut/sandwich-triple? "2l',ucd")))

  (t/is (sut/sandwich-triple? "xyx"))
  (t/is (sut/sandwich-triple? "aaa"))
  (t/is (sut/sandwich-triple? "abcdefeghi")))
