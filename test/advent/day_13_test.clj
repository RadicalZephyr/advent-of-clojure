(ns advent.day-13-test
  (:require [advent.day-13 :as sut]
            [clojure.test :as t]
            [loom.graph :as loom]))

(t/deftest parse-guests-test
  (t/is (= (loom/weighted-digraph ['Bob 'Sue 12])
           (sut/parse-guests
            "Bob would gain 12 happiness units by sitting next to Sue.")))

  (t/is (= (loom/weighted-digraph ['Bob 'Sue 12]
                                  ['Sue 'Bob -100])
           (sut/parse-guests
            (str
             "Bob would gain 12 happiness units by sitting next to Sue.\n"
             "Sue would lose 100 happiness units by sitting next to Bob.\n")))))


(t/deftest collapse-digraph-test
  (t/is (= (loom/weighted-graph ['Bob 'Sue -88])
           (sut/collapse-digraph
            (loom/weighted-digraph ['Bob 'Sue 12]
                                   ['Sue 'Bob -100]))))
  (t/is (= (loom/weighted-graph ['Bob 'Sue -80])
           (sut/collapse-digraph
            (loom/weighted-digraph ['Bob 'Sue 20]
                                   ['Sue 'Bob -100])))))

(t/deftest maximize-happiness-test
  (let [guest-list (str
                    "Alice would gain 54 happiness units by sitting next to Bob.\n"
                    "Alice would lose 79 happiness units by sitting next to Carol.\n"
                    "Alice would lose 2 happiness units by sitting next to David.\n"
                    "Bob would gain 83 happiness units by sitting next to Alice.\n"
                    "Bob would lose 7 happiness units by sitting next to Carol.\n"
                    "Bob would lose 63 happiness units by sitting next to David.\n"
                    "Carol would lose 62 happiness units by sitting next to Alice.\n"
                    "Carol would gain 60 happiness units by sitting next to Bob.\n"
                    "Carol would gain 55 happiness units by sitting next to David.\n"
                    "David would gain 46 happiness units by sitting next to Alice.\n"
                    "David would lose 7 happiness units by sitting next to Bob.\n"
                    "David would gain 41 happiness units by sitting next to Carol.\n")]
    (t/is (= 330
             (+ 46 -2 54 83 -7 60 55 41)
             (+ 44 137 53 96)
             (sut/maximize-happiness guest-list)))))
