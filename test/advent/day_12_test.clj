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
         (sut/sum-numbers "{}"))))

(t/deftest int-seq-test
  (t/is (= [1]
           (sut/int-seq [1])))
  (t/is (= [2]
           (sut/int-seq [2 "blue"])))
  (t/is (= [1]
           (sut/int-seq {"a" 1})))
  (t/is (= [1]
           (sut/int-seq {"a" [1]})))
  (t/is (= [2 1]
           (sut/int-seq {"a" [{"b" 2} 1]})))
  (t/is (= []
           (sut/int-seq {"a" [{"b" 2} 1]
                         "b" "red"})))
  (t/is (= [1 2]
           (sut/int-seq [1 {"a" 3 "how" "red"} 2])))
  (t/is (= [1 2 3]
           (sut/int-seq [1 2 3])))
  (t/is (= [1 3]
           (sut/int-seq [1 {"c" "red" "b" 2} 3])))
  (t/is (= []
           (sut/int-seq {"d" "red" "e" [1 2 3 4] "f" 5})))
  (t/is (= [1 5]
           (sut/int-seq [1 "red" 5]))))
