(ns advent.day-6-test
  (:require [advent.day-6 :as sut]
            [clojure.test :as t]))

(t/deftest parse-instructions-test
  (t/is (= [:on [489 959] [759 964]]
           (sut/parse-instructions "turn on 489,959 through 759,964")))
  (t/is (= [:off [820 516] [871 914]]
           (sut/parse-instructions "turn off 820,516 through 871,914")))
  (t/is (= [:toggle [756 965] [812 992]]
           (sut/parse-instructions "toggle 756,965 through 812,992"))))

(t/deftest coords->set-test
  (t/is (= #{[0 0] [0 1] [1 0] [1 1]}
         (sut/coords->set [0 0] [1 1])))
  (t/is (= #{[300 300] [301 300]}
           (sut/coords->set [300 300] [301 300]))))

(t/deftest do-instruction-test
  (t/is (= #{[0 0] [0 1] [1 0] [1 1]}
           (sut/do-instruction #{} [:on [0 0] [1 1]])))
  (t/is (= #{[0 0] [0 1]}
           (sut/do-instruction #{[0 0] [0 1] [1 0] [1 1]}
                               [:off [1 0] [1 1]])))
  (t/is (= #{[0 0] [1 0]}
           (sut/do-instruction #{[0 0] [1 1]}
                               [:toggle [1 0] [1 1]]))))
