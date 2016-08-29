(ns advent.day-11
  (:require [instaparse.core :as insta]))

(def parse-straight
  (insta/parser
   "password = letters* straight letters*
    straight = 'abc' | 'bcd' | 'cde' | 'def' | 'efg' | 'fgh' | 'ghi' | 'hij' | 'ijk' | 'jkl' | 'klm' | 'lmn' | 'mno' | 'nop' | 'opq' | 'pqr' | 'qrs' | 'rst' | 'stu' | 'tuv' | 'uvw' | 'vwx' | 'wxy' | 'xyz'
    letters = #'[a-z]'"))


(defn no-iol? [password]
  (not (re-find #"[iol]" password)))

(defn has-straight? [password]
  (not
   (insta/failure?
    (parse-straight password))))

(def parse-pairs
  (insta/parser
   "password = letters* pair letters* pair letters*
    pair = 'aa' | 'bb' | 'cc' | 'dd' | 'ee' | 'ff' | 'gg' | 'hh' | 'ii' | 'jj' | 'kk' | 'll' | 'mm' | 'nn' | 'oo' | 'pp' | 'qq' | 'rr' | 'ss' | 'tt' | 'uu' | 'vv' | 'ww' | 'xx' | 'yy' | 'zz'
    letters = #'[a-z]'"))

(defn has-pairs? [password]
  (not
   (insta/failure?
    (parse-pairs password))))

(defn valid-password? [password]
  (and
   (no-iol? password)
   (has-straight? password)
   (has-pairs? password)))

(defn stringify [coll]
  (apply str coll))

(defn c->i [c]
  (- (int c)
     (int \a)))

(defn i->c [i]
  (char
   (+ i (int \a))))

(defn iol? [i]
  (contains? #{8 14 11} i))

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

(defn valid-password-seq [password]
  (lazy-seq
   (let [next (next-password password)]
     (if (valid-password? next)
       (cons next (valid-password-seq next))
       (valid-password-seq (next-password next))))))

(defn next-valid-password [current-password]
  (first (valid-password-seq current-password)))

(defn day-11 [current-password]
  (next-valid-password current-password))
