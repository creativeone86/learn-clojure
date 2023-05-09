(ns lein.playground)


(filter odd? (map inc (range 5)))

(def book (slurp "https://www.gutenberg.org/files/2701/2701-0.txt"))
(def words (re-seq #"[\w|']+" book))

(count words)

(def common-words #{"a" "able" "about" "across" "after" "all" "every" "this" "which" "why" "will"})

(->> words
     (map clojure.string/lower-case)
     (remove common-words)
     (frequencies)
     (sort-by val)
     (take-last 20))


(->> words
     (distinct)
     (sort-by count)
     (take-last 20))

(defn palindrome? [coll]
  (= (seq coll) (reverse coll)))

(palindrome? [1 2 3 2 1])
(palindrome? "racecar")
(reverse "racecar")

(map inc #{1 2 3})

(defn longest-palindrome [words]
  (->> words
       (distinct)
       (filter palindrome?)
       (sort-by count)
       (take-last 1)))

(longest-palindrome words)


