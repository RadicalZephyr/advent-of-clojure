(ns advent.day-7-test
  (:require [advent.day-7 :as sut]
            [clojure.test :as t]
            [loom.alg :as alg]
            [loom.graph :as graph]))

(t/deftest parse-wires-test
  (t/is (= {'x 123}
           (sut/parse-wires "123 -> x")))
  (t/is (= {'y 456}
           (sut/parse-wires "456 -> y")))

  (t/is (= {'d  [:and 'x 'y]}
           (sut/parse-wires "x AND y -> d")))
  (t/is (= {'f [:lshift 'x 2]}
           (sut/parse-wires "x LSHIFT 2 -> f")))
  (t/is (= {'h [:not 'x]}
           (sut/parse-wires "NOT x -> h")))
  (t/is (= {'a 'lx}
           (sut/parse-wires "lx -> a")))

  (t/is (= {'x 123
            'y [:not 'x]}
           (sut/parse-wires "123 -> x\nNOT x -> y"))))

(t/deftest symbols-test
  (t/is (= '[a]
           (sut/symbols 'a)))
  (t/is (= '[a]
           (sut/symbols '[a])))
  (t/is (= '[a b]
           (sut/symbols '[a b])))
  (t/is (= '[a]
           (sut/symbols '[a :abc])))
  (t/is (= '[a b]
           (sut/symbols '[a [:abc b]])))
  (t/is (= '[a b]
           (sut/symbols '[a [:abc [b]]]))))

(t/deftest wire->dependencies-test
  (t/is (= []
           (sut/wire->dependencies '[x 123])))
  (t/is (= '[[x y]]
           (sut/wire->dependencies '[y [:not x]])))
  (t/is (= '#{[x z]
              [y z]}
           (set
            (sut/wire->dependencies '[z [:and x y]])))))

(t/deftest circuit->wire-dependencies-test
  (let [circuit-deps (sut/circuit->wire-dependencies {'x 123 'y [:not 'x]})]
    (t/is (= (graph/digraph '[x y])
             circuit-deps))
    (t/is (= '(x y)
             (alg/topsort circuit-deps))))

  (let [circuit-deps (sut/circuit->wire-dependencies {'x 123
                                                      'y [:not 'x]
                                                      'z [:or 'x 'y]})]
    (t/is (= (graph/digraph '[x y] '[y z] '[x z])
             circuit-deps))
    (t/is (= '(x y z)
             (alg/topsort circuit-deps)))))

(t/deftest as-unsigned-short-test
  (t/is (= 124
           (sut/as-unsigned-short (bigint 124))))
  (t/is (= 65412
           (sut/as-unsigned-short (bigint -124)))))

(t/deftest resolve-wire-test
  (t/is (= '{x 123 y [:not x]}
           (sut/resolve-wire '{x 123 y [:not x]} 'x)))
  (t/is (= '{x 123 y 65412}
           (sut/resolve-wire '{x 123 y [:not x]} 'y)))
  (t/is (= '{x 123 y 3}
           (sut/resolve-wire '{x 123 y [:and 7 x]} 'y)))
  (t/is (= '{x 123 y 127}
           (sut/resolve-wire '{x 123 y [:or 4 x]} 'y)))
  (t/is (= '{x 123 y 492}
           (sut/resolve-wire '{x 123 y [:lshift x 2]} 'y)))
  (t/is (= '{x 123 y 30}
           (sut/resolve-wire '{x 123 y [:rshift x 2]} 'y)))

  (t/is (= '{x 123 y [:not x] f 492}
           (sut/resolve-wire '{x 123
                               y [:not x]
                               f [:lshift x 2]}
                             'f))))

(t/deftest resolve-circuit-test
  (t/is (= '{x 123
             y 65412}
           (sut/resolve-circuit '{x 123 y [:not x]})))
  (t/is (= '{y 123
             x 65412}
           (sut/resolve-circuit '{y 123 x [:not y]})))
  (t/is (= '{z 123
             y 123
             x 65412}
           (sut/resolve-circuit '{z y y 123 x [:not y]})))

  (t/is (= '{d 72     ;; d: 72
             e 507    ;; e: 507
             f 492    ;; f: 492
             g 114    ;; g: 114
             h 65412  ;; h: 65412
             i 65079  ;; i: 65079
             x 123    ;; x: 123
             y 456}   ;; y: 456
           (sut/resolve-circuit '{x 123            ;; 123 -> x
                                  y 456            ;; 456 -> y
                                  d [:and x y]     ;; x AND y -> d
                                  e [:or x y]      ;; x OR y -> e
                                  f [:lshift x 2]  ;; x LSHIFT 2 -> f
                                  g [:rshift y 2]  ;; y RSHIFT 2 -> g
                                  h [:not x]       ;; NOT x -> h
                                  i [:not y]}))))  ;; NOT y -> i
