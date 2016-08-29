(ns advent.day-11
  (:require [instaparse.core :as insta]))

(defn stringify [coll]
  (apply str coll))

(defn c->i [c]
  (- (int c)
     (int \a)))

(defn i->c [i]
  (char
   (+ i (int \a))))

(def iol? #{8 14 11})

(defn intstr [s]
  (->> (seq s)
       (map c->i)))

(defn no-iol? [password]
  (not (some iol? (intstr password))))

(defn- is-straight? [triple]
  (= [0 1 2]
     (map #(- % (first triple)) triple)))

(defn has-straight? [password]
  (->> password
       intstr
       (partition 3 1)
       (some is-straight?)
       boolean))

(def parse-pairs
  (insta/parser
   "password = letters* pair letters* pair letters*
    pair = 'aa' | 'bb' | 'cc' | 'dd' | 'ee' | 'ff' | 'gg' | 'hh' | 'ii' | 'jj' | 'kk' | 'll' | 'mm' | 'nn' | 'oo' | 'pp' | 'qq' | 'rr' | 'ss' | 'tt' | 'uu' | 'vv' | 'ww' | 'xx' | 'yy' | 'zz'
    letters = #'[a-z]'"))


(defn pair-seq [passnumber]
  (lazy-seq
   (let [[one two & tail] passnumber]
     (when (and one two)
       (if (= one two)
         (cons one (pair-seq tail))
         (pair-seq (rest passnumber)))))))

(defn has-pairs? [password]
  (->> password
       intstr
       pair-seq
       count
       (<= 2)))

(defn valid-password? [password]
  (and
   (no-iol? password)
   (has-straight? password)
   (has-pairs? password)))

(defn skip-iol [i]
  (if (iol? i) (inc i) i))

(defn inc-char [c]
  (-> c
      c->i
      inc
      skip-iol
      (mod 26)
      i->c))

(defn inc-carry-char [c]
  (let [i-next (inc-char c)]
      [(= i-next \a) i-next]))

(defn roll-letter [{:keys [roll? cs] :as acc} c]
  (if roll?
    {:roll? true :cs (conj cs \z)}
    (let [iol? (iol? (c->i c))]
      {:roll? iol? :cs (conj cs c)})))

(defn pre-roll-iol [letters]
  (:cs (reduce roll-letter {:roll? false :cs []} letters)))

(defn inc-letter [{:keys [inc? cs] :as acc} c]
  (if inc?
    (let [[carry? cnext] (inc-carry-char c)]
      {:inc? carry? :cs (conj cs cnext)})
    {:inc? false :cs (conj cs c)}))

(defn increment-letters [letters]
  (:cs (reduce inc-letter {:inc? true :cs []} letters)))

(defn next-password [password]
  (-> password
      pre-roll-iol
      reverse
      increment-letters
      reverse
      stringify))

#_(defn next-valid-password [current-password]
  )

#_(defn valid-password-seq [password]
  (lazy-seq
   (let [next (next-password password)]
     (if (valid-password? next)
       (cons next (valid-password-seq next))
       (valid-password-seq (next-password next))))))



#_(defn day-11 [current-password]
  (next-valid-password current-password))
