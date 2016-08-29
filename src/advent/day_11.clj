(ns advent.day-11
  (:require [instaparse.core :as insta]))

(defn c->i [c]
  (- (int c)
     (int \a)))

(defn i->c [i]
  (char
   (+ i (int \a))))

(def iol? #{8 14 11})

(defn str->ints [s]
  (->> (seq s)
       (map c->i)))

(defn ints->str [is]
  (->> is
       (map i->c)
       (apply str)))

(defn no-iol? [password]
  (not (some iol? password)))

(defn- is-straight? [triple]
  (= [0 1 2]
     (map #(- % (first triple)) triple)))

(defn has-straight? [password]
  (->> password
       (partition 3 1)
       (some is-straight?)
       boolean))

(defn pair-seq [passnumber]
  (lazy-seq
   (let [[one two & tail] passnumber]
     (when (and one two)
       (if (= one two)
         (cons one (pair-seq tail))
         (pair-seq (rest passnumber)))))))

(defn has-pairs? [password]
  (->> password
       pair-seq
       count
       (<= 2)))

(defn valid-password? [password]
  (let [passnumber password]
    (and
     (no-iol? passnumber)
     (has-straight? passnumber)
     (has-pairs? passnumber))))

(defn skip-iol [i]
  (if (iol? i) (inc i) i))

(defn inc-char [c]
  (-> c
      inc
      skip-iol
      (mod 26)))

(defn inc-carry-char [c]
  (let [i-next (inc-char c)]
    [(= i-next
        (c->i \a))
     i-next]))

(defn roll-letter [{:keys [roll? cs] :as acc} c]
  (if roll?
    {:roll? true :cs (conj cs (c->i \z))}
    {:roll? (iol? c) :cs (conj cs c)}))

(defn pre-roll-iol [letters]
  (:cs (reduce roll-letter {:roll? false :cs []} letters)))

(defn inc-letter [{:keys [inc? cs] :as acc} c]
  (if inc?
    (let [[carry? cnext] (inc-carry-char c)]
      {:inc? carry? :cs (conj cs cnext)})
    {:inc? false :cs (conj cs c)}))

(defn increment-letters [letters]
  (:cs (reduce inc-letter {:inc? true :cs []} letters)))

(defn next-passnumber [passnumber]
  (->> passnumber
       pre-roll-iol
       reverse
       increment-letters
       reverse))

(defn valid-passnumber-seq [passnumber]
  (lazy-seq
   (let [next (next-passnumber passnumber)]
     (if (valid-password? next)
       (cons next (valid-passnumber-seq next))
       (valid-passnumber-seq (next-passnumber next))))))

(defn next-valid-password [password]
  (-> password
      str->ints
      valid-passnumber-seq
      first
      ints->str))

(defn day-11 [current-password]
  (next-valid-password current-password))
