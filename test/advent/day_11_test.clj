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

(t/deftest do-letter-test
  (t/testing "basic letter incrementing and wrapping"
    (t/is (= {:inc? false :cs [\b]}
             (sut/do-letter {:inc? true :cs []} \a)))
    (t/is (= {:inc? true :cs [\a]}
             (sut/do-letter {:inc? true :cs []} \z))))

  (t/testing "skips iol completely"
    (t/is (= {:inc? false :cs [\j]}
             (sut/do-letter {:inc? true :cs []} \h)))
    (t/is (= {:inc? false :cs [\m]}
             (sut/do-letter {:inc? true :cs []} \k)))
    (t/is (= {:inc? false :cs [\p]}
             (sut/do-letter {:inc? true :cs []} \n))))

  (t/testing "inc? flag behavior and carrying"
    (t/is (= {:inc? false :cs [\a \a]}
             (sut/do-letter {:inc? false :cs [\a]} \a)))
    (t/is (= {:inc? false :cs [\a \b]}
             (sut/do-letter {:inc? true :cs [\a]} \a)))

    (t/is (= {:inc? true :cs [\a \a]}
             (sut/do-letter {:inc? true :cs [\a]} \z)))))

(t/deftest next-password-test
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

#_(t/deftest next-valid-password-test
  (t/is (= "abcdffaa"
           (sut/next-valid-password "abcdefgh")))
  (t/is (= "ghjaabcc"
           (sut/next-valid-password "ghijklmn"))))
