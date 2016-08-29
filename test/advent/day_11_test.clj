(ns advent.day-11-test
  (:require [advent.day-11 :as sut]
            [clojure.test :as t]))

(def s sut/str->ints)

(t/deftest has-pairs?-test
  (t/is (= true
           (sut/has-pairs? (s "aabb"))))
  (t/is (= true
           (sut/has-pairs? (s "aabbb"))))
  (t/is (= true
           (sut/has-pairs? (s "aacdebbb"))))

  (t/is (= false
           (sut/has-pairs? (s "acbb"))))
  (t/is (= false
           (sut/has-pairs? (s "bbb"))))
  (t/is (= false
           (sut/has-pairs? (s "abcbcb")))))

(t/deftest has-straight?-test
  (t/is (= true
           (sut/has-straight? (s "abc"))))
  (t/is (= true
           (sut/has-straight? (s "def"))))
  (t/is (= true
           (sut/has-straight? (s "xyz"))))

  (t/is (= false
           (sut/has-straight? (s "abd"))))
  (t/is (= false
           (sut/has-straight? (s "abe"))))
  (t/is (= false
           (sut/has-straight? (s "bbc")))))

(t/deftest valid-password?-test
  (t/is (= true
           (sut/valid-password? (s "abcddee"))))
  (t/is (= true
           (sut/valid-password? (s "abcdffaa"))))
  (t/is (= true
           (sut/valid-password? (s "ghjaabcc"))))

  (t/is (= false
           (sut/valid-password? (s "abcddei"))))
  (t/is (= false
           (sut/valid-password? (s "abcddeo"))))
  (t/is (= false
           (sut/valid-password? (s "abcddel"))))

  (t/is (= false
           (sut/valid-password? (s "abbceffg"))))
  (t/is (= false
           (sut/valid-password? (s "abcdeffg")))))

(t/deftest pre-roll-iol-test
  (t/is (= (s "iz")
           (sut/pre-roll-iol (s "ig"))))
  (t/is (= (s "iz")
           (sut/pre-roll-iol (s "iz"))))
  (t/is (= (s "oz")
           (sut/pre-roll-iol (s "oa"))))
  (t/is (= (s "lz")
           (sut/pre-roll-iol (s "ll"))))
  (t/is (= (s "zxizzzzzz")
           (sut/pre-roll-iol (s "zxiabcdef")))))

(t/deftest next-passnumber-test
  (t/testing "basic password incrementing"
    (t/is (= (s "b")
             (sut/next-passnumber (s "a"))))
    (t/is (= (s "c")
             (sut/next-passnumber (s "b"))))
    (t/is (= (s "ab")
             (sut/next-passnumber (s "aa"))))
    (t/is (= (s "ba")
             (sut/next-passnumber (s "az"))))
    (t/is (= (s "baa")
             (sut/next-passnumber (s "azz")))))

  (t/testing "skips iol"
    (t/is (= (s "j")
             (sut/next-passnumber (s "h"))))
    (t/is (= (s "m")
             (sut/next-passnumber (s "k"))))
    (t/is (= (s "p")
             (sut/next-passnumber (s "n")))))

  (t/testing "skips iol intelligently"
    (t/is (= (s "ja")
             (sut/next-passnumber (s "iz"))))
    (t/is (= (s "ja")
             (sut/next-passnumber (s "ig"))))
    (t/is (= (s "m")
             (sut/next-passnumber (s "k"))))
    (t/is (= (s "p")
             (sut/next-passnumber (s "n"))))

    (t/is (= (s "abpaa")
             (sut/next-passnumber (s "aboaa"))))
    (t/is (= (s "abpaa")
             (sut/next-passnumber (s "abnzz"))))
    (t/is (= (s "abnas")
             (sut/next-passnumber (s "abnar"))))
    (t/is (= (s "abnba")
             (sut/next-passnumber (s "abnaz"))))))

#_(t/deftest next-valid-password-test
  (t/is (= "abcdffaa"
           (sut/next-valid-password "abcdefgh")))
  (t/is (= "ghjaabcc"
           (sut/next-valid-password "ghijklmn"))))
