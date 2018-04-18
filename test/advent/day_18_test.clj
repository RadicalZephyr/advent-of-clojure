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
