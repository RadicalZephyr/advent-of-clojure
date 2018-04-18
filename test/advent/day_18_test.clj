(ns advent.day-18-test
  (:require [advent.day-18 :as sut]
            [clojure.test :as t]))

(t/deftest parse-grid-test
  (t/is (= [[:. :# :.]
            [:. :# :.]
            [:. :# :.]]
           (sut/parse-grid ".#.\n.#.\n.#.\n")))

  (t/is (= [[:. :. :#]
            [:. :# :.]
            [:# :. :.]]
           (sut/parse-grid "..#\n.#.\n#..\n"))))


(t/deftest neighbors-test
  (t/is (= [[0 1] [1 0] [1 1]]
           (sut/neighbors [2 2] [0 0]))))

(t/deftest step-board-test
  (t/is (= #{[0 0] [0 1] [1 0] [1 1]}
           (sut/step-board
            [2 2]
            #{[0 1] [1 0] [1 1]})))

  (t/is (= #{}
           (sut/step-board
            [2 2]
            #{[0 0] [0 1]})))

  (t/is (= #{[1 1] [1 2]
             [2 1] [2 2]}
           (sut/step-board
            [4 4]
            #{[1 1] [1 2]
              [2 1] [2 2]})))

  (t/is (= #{[0 0] [0 1] [0 2]
             [1 0]       [1 2]
             [2 0] [2 1] [2 2]}
           (sut/step-board
            [3 3]
            #{[0 1] [1 0] [1 1]
              [2 1] [1 2]}))))

(t/deftest dense->sparse-test
  (t/is (= #{[0 0] [0 1]}
           (sut/dense->sparse [[:# :#]
                               [:. :.]])))
  (t/is (= #{[1 0] [1 1]}
           (sut/dense->sparse [[:. :.]
                               [:# :#]]))))
