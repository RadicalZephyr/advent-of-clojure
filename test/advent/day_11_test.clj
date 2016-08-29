(ns advent.day-11-test
  (:require [advent.day-11 :as sut]
            [clojure.test :as t]))

(t/deftest has-pairs?-test
  (t/is (= true
           (sut/has-pairs? "aabb")))
  (t/is (= true
           (sut/has-pairs? "aabbb")))
  (t/is (= true
           (sut/has-pairs? "aacdebbb")))

  (t/is (= false
           (sut/has-pairs? "acbb")))
  (t/is (= false
           (sut/has-pairs? "bbb")))
  (t/is (= false
           (sut/has-pairs? "abcbcb"))))

(t/deftest valid-password?-test
  (t/is (= true
           (sut/valid-password? "abcddee")))

  (t/is (= false
           (sut/valid-password? "abcddei")))
  (t/is (= false
           (sut/valid-password? "abcddeo")))
  (t/is (= false
           (sut/valid-password? "abcddel")))

  (t/is (= false
           (sut/valid-password? "abbceffg")))
  (t/is (= false
           (sut/valid-password? "abcdeffg"))))

(t/deftest inc-letter-test
  (t/testing "basic letter incrementing and wrapping"
    (t/is (= {:inc? false :cs [\b]}
             (sut/inc-letter {:inc? true :cs []} \a)))
    (t/is (= {:inc? true :cs [\a]}
             (sut/inc-letter {:inc? true :cs []} \z))))

  (t/testing "skips iol completely"
    (t/is (= {:inc? false :cs [\j]}
             (sut/inc-letter {:inc? true :cs []} \h)))
    (t/is (= {:inc? false :cs [\m]}
             (sut/inc-letter {:inc? true :cs []} \k)))
    (t/is (= {:inc? false :cs [\p]}
             (sut/inc-letter {:inc? true :cs []} \n))))

  (t/testing "inc? flag behavior and carrying"
    (t/is (= {:inc? false :cs [\a \a]}
             (sut/inc-letter {:inc? false :cs [\a]} \a)))
    (t/is (= {:inc? false :cs [\a \b]}
             (sut/inc-letter {:inc? true :cs [\a]} \a)))

    (t/is (= {:inc? true :cs [\a \a]}
             (sut/inc-letter {:inc? true :cs [\a]} \z)))))

(t/deftest pre-roll-iol-test
  (t/is (= (seq "iz")
           (sut/pre-roll-iol (seq "ig"))))
  (t/is (= (seq "iz")
           (sut/pre-roll-iol (seq "iz"))))
  (t/is (= (seq "oz")
           (sut/pre-roll-iol (seq "oa"))))
  (t/is (= (seq "lz")
           (sut/pre-roll-iol (seq "ll"))))
  (t/is (= (seq "zxizzzzzz")
           (sut/pre-roll-iol (seq "zxiabcdef")))))

(t/deftest next-password-test
  (t/testing "basic password incrementing"
    (t/is (= "b"
             (sut/next-password "a")))
    (t/is (= "c"
             (sut/next-password "b")))
    (t/is (= "ab"
             (sut/next-password "aa")))
    (t/is (= "ba"
             (sut/next-password "az")))
    (t/is (= "baa"
             (sut/next-password "azz"))))

  (t/testing "skips iol"
    (t/is (= "j"
             (sut/next-password "h")))
    (t/is (= "m"
             (sut/next-password "k")))
    (t/is (= "p"
             (sut/next-password "n"))))

  (t/testing "skips iol intelligently"
    (t/is (= "ja"
             (sut/next-password "iz")))
    (t/is (= "ja"
             (sut/next-password "ig")))
    (t/is (= "m"
             (sut/next-password "k")))
    (t/is (= "p"
             (sut/next-password "n")))

    (t/is (= "abpaa"
             (sut/next-password "aboaa")))
    (t/is (= "abpaa"
             (sut/next-password "abnzz")))
    (t/is (= "abnas"
             (sut/next-password "abnar")))
    (t/is (= "abnba"
             (sut/next-password "abnaz")))))

(t/deftest next-valid-password-test
  (t/is (= "abcdffaa"
           (sut/next-valid-password "abcdefgh")))
  (t/is (= "ghjaabcc"
           (sut/next-valid-password "ghijklmn"))))
