(ns advent.day-12-test
  (:require [advent.day-12 :as sut]
            [clojure.test :as t]))


(t/deftest parse-ints-test
  (t/is (= [1]
           (sut/parse-ints "1")))
  (t/is (= [-1]
           (sut/parse-ints "-1")))
  (t/is (= [1 2]
           (sut/parse-ints "1a2")))
  (t/is (= [1 2]
           (sut/parse-ints "1a2b")))
  (t/is (= [1 2]
           (sut/parse-ints "aoeu1a2b")))
  (t/is (= [1 2]
           (sut/parse-ints "1a,^&^%^(&*)2b")))
  (t/is (= [1 2]
           (sut/parse-ints "1a2baoseunthoa%^&GDIDHT")))

  (t/is (= [-1 1]
           (sut/parse-ints "{\"a\":[-1,1]}"))))

(t/deftest sum-numbers-test
  (t/is (= 6
           (sut/sum-numbers "[1,2,3]")))
  (t/is (= 6
           (sut/sum-numbers "{\"a\":2,\"b\":4}")))
  (t/is (= 3
           (sut/sum-numbers "[[[3]]]")))
  (t/is (= 3
           (sut/sum-numbers "{\"a\":{\"b\":4},\"c\":-1}")))
  (t/is (= 0
         (sut/sum-numbers "{\"a\":[-1,1]}")))
  (t/is (= 0
           (sut/sum-numbers "[-1,{\"a\":1}]")))
  (t/is (= 0
         (sut/sum-numbers "[]")))
  (t/is (= 0
         (sut/sum-numbers "{}")))



  )
