(ns lein.learn)

; displays greetings
(defn hello-word
  "Says hello world to the world"
  []
  (str "Hello " "world"))

(hello-word)

(:foo {:foo 1 :bee 2})


; A var('variable') holds a mutable reference to another objects
; A namespaces maps symbols to vars
; clojure.core namespaces contains nearly 500 functions as + - etc
; Symbols resolve into the values held in vars

; dog/cat resolves to the value of the var of the symbol cat in the dog namespace
; cat -> resolves to the value of the var of the symbol cat int he current namespace
; Symbols resolve into the values held in vars. Lists call functions, macros, or special forms
; a call to the function resolved from foo passes the argument 51, "hello" and the value resolved from bar
; if foo is macro bar is not evaluated but passed as symbol then when foo is resolved its replaced with its return value
;(foo 51 "hello" bar)


; the DEF special form
; - assigns a value to a var in the current namespace
; - creates the var if it doesn't already exists

(def alice 3)                                               ;assigns value 3 to var of alice
(def james (+ 8 4))                                         ;assigns value returned by (+ 8 4) to var of james

; the FN special form
; - returns a new function object
; - parameters are denoted as symbols in vector
; - when called, the function returns the value of the last expression in its body
; general form
; (fn [parameters] body)

; the IF special form
; - when condition return any value other than false or nil.
; - expression2 is evaluated and its value returned from the if
; - otherwise, expression3 is evaluated and its value returned from the if
; general form
; (if condition expression2 expression3)

; the DO special form
; - runs a body of expression in order
; - returns the value returned by the last expression in the body
; general form
; (do body)

; these 3 expression run in order
; (do
;  (foo)
;  (bar)
;  (baz)) this do returns the value returned by (baz)


; when (alice) is neither false nor nil, execute (bob) then (carol)
; (if (alice)
; (do
;  (bob)
;  (carol)))

; the LET special form
; locally binds values of expressions to symbols for the duration of its body
; returns the value returned by the last expression in the body
; general form
; (let [bindings] body)

; (let [x (foo) y 3]
;  (bar y x)
;  (baz x))

; equivalent, expect (foo) is evaluated twice
; (do
;  (bar 3 (foo))
;  (baz (foo)))

; (let [x 7 y3]
;   (let [x -9]
;     (foo x y))    invokes foo with the values -9 3
;   (foo x y))      invokes foo with the values 7 3

; the RECUR special form
; - jumps execution back to last recursion point with new binding values
; - may only be used in `tail position`
; general form
; (recur arguments)

(defn foo [x y]
  (print x y)
  (foo (inc x) (dec y)))                                    ; recursive call to foo creates another stack frame

(defn foo1 [x y]
  (print x y)
  (recur (inc x) (dec y)))                                  ; just back to start of function with new args

(defn foo2 [x y]
  (print x y)
  (if (< x 9)
    (recur (inc x) (dec y))))                               ; OK: recur is in tail position of if which
;is itself in tail position of the function

; the LOOP special form
; like let, but also establishes a recursion point for recur
; general form
; (loop [bindings] body)
(loop [x 5 y 2]
  (print x y)
  (if (< x 9)
    (recur (inc x) (dec y))))

; the QUOTE special form
; - returns an element unevaluated
; general form
; (quote element)

;(foo (bar 7 3)) invokes foo with the value returned by the function call (bar 7 3)
;(foo (quote (bar 7 3))) invokes foo with a list of the symbol bar and the values 7 and 4

; the THROW and TRY special forms
; throw an exception object
; try catches exceptions that propagate from its body

; general form
; (throw exception)
; try body catch-clauses)
; (catch class exception-binding body)

; ((try
;   (foo)
;   (baz)
; (catch BadThing alice
;  (bar alice))
; (catch OtherBadThing fred
;  (carol fred))
; (finally
;  (bob)) )

; the var special form
; - returns the var object to which a symbol resolves
; general form
; (var symbol)
; foo/bar returns the value of the var mapped to bar in the ns foo
; (var foo/bar) returns the var mapped to bar in the ns foo

; the new special form
; general form
; (new class arguments)
; new java.io.File "myfile.dat") returns new instance of java.io.File by
; invoking its constructor with the string as argument

; the .(dot) special form
; - returns the value of a java field or invokes a java method
; general form
; (. class field) ; returns value of a static field of the class
; (. java.lang.Math PI) returns value of static field PI of the java class Math


; the set! special form
; - assigns value of an expression to the field of an instance or class specified by a dot form
; general form
; (set! dot-form expression)
; (set! (. java.lang.Math PI) 3)


; will throw overflow exception for n greater
; than 20 because * only accepts and returns Long values
(defn factorial [n]
  (if (= n 0) 1
              (* n (factorial (dec n)))))

; uses arbitrary precision match function, so works for n greater than 20.
; but calling itself recursively will cause stack overflow
(defn factorial-bigint [n]
  (if (= n 0) 1
              (*' n (factorial-bigint (dec n)))))

; fix for stack overflow from above
(defn factorial-bigint-fixed [n]
  (if (= n 0) 1
              (loop [val n i n]
                (if (<= i 1) val
                             (recur (*' val (dec i)) (dec i))))))

(factorial-bigint 22)

; fizz-buzz solution

(defn fizz-buzz []
  (loop [i 1]
    (if (<= i 100)
      (do
        (if (and (= (rem i 3) 0) (= (rem i 5) 0))
          (println "FizzBuzz")
          (if (= (rem i 3) 0)
            (println "Fizz")
            (if (= (rem i 5) 0)
              (println "Buzz")
              (println i))))
        (recur (inc i))))))

(fizz-buzz)

; fizz-buzz pretty solution
(defn fizz-buzz-pretty []
  (loop [i 1]
    (if (<= i 100)
      (do
        (cond
          (and (= (rem i 3) 0) (= (rem i 5) 0)) (println "FizzBuzz")
          (= (rem i 3) 0) (println "Fizz")
          (= (rem i 5) 0) (println "Buzz")
          :else (println i))
        (recur (inc i))))))
(fizz-buzz-pretty)

; core collection functions
; count -> returns number of elements / key-value pairs in a collection
; list? -> returns true if the argument is a list, otherwise false
; vector? - ...
; map? - ...
; contains? - returns true if the collection contains the key / index, otherwise false
; conj -> conjoin returns new collection with an added element or key-value pair
; assoc -> associate return new collection with added/modified value for given key or index
; dissoc -> dissociate returns new map in which a key has been removed
; merge -> returns a new map that combines the key-value pairs of one or more maps

(conj {3 6, "hi" 8} ["bye" 7])
(conj {3 6, "hi" 8} ["hi" 7])
(conj [7 8 9 10 11] true)
(conj '(7 8 9 10 11) true)
(assoc [7 8 9 10 11] 3 true)

; get -> returns value for a given key or index in a collection
; pop -> return a new collection without the first element / key-value pair
; peek -> returns the element / key-value pair that would be removed by pop
; pop removes the LAST element of a vector and FIRST element of a list and don't work on maps

; A sequence is any collection type that supports two operations
; first -> returns the first element of the collection (or nil if the sequence has zero el)
; rest -> returns a sequence of all the elements of the original except the first
; returns an empty sequence if the original has zero or one elements
; Sequence types implement the Java interface clojure.lang.Iseq
; `Sequence-able` aka `seq-able` types have operations that produce sequences.

; The seq function returns a sequence of a seq-able type's elements
(seq [7 8 9 10 11])
(seq '(7 8 9 10 11))
(seq {3 6, "hi" 8})

; Sequences enable performant and stateless iteration over collections:
; (loop [x [1 2 3 4 5]]
;  (print (first x))
;  (recur (rest x)))

; A cons is a sequence type which holds a value and a reference to another sequence.
; The cons function creates a new cons given a value and sequence
; when its second argument is nil, cons returns a list

(def x (cons 3 nil))
(first x)
(rest x)

(def y (cons 4 '(7 8 9)))
(first y)
(rest y)

(repeatedly 3 rand)

; A lazy sequence generates its elements only as needed.
; Many standard functions generate lazy sequences, including:

; range -> returns a lazy sequence of integers that span a specified range
; repeat -> returns an infinite lazy sequence that repeats a value
; repeatedly -> returns an infinite lazy sequence of values generated by calls to a given function
; iterate -> like repeatedly, but each call to the function takes the previous element as argument
; cycle -> returns an infinite lazy sequence that repeats the elements of a collection

; the lazy-seq macro creates a lazy sequence from a body
; the last expression of the body should return a sequence
; the body is only run the first time we invoke first or rest on the lazy sequence
; general form
; (lazy-seq body)

(first (seq {:a 1 :b 2 :c 3}))
(map inc [1 2 3])
(map str ["a" "b" "c" "d"] ["A" "B" "C"])

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) food-journal))

(filter #(< (:human %) 5) food-journal)

(filter #(<= (:month %) 3) food-journal)

(some #(= (:critter %) 2.0) food-journal)


(some #(and (> (:critter %) 3) %) food-journal)

















