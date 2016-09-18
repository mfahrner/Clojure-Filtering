(ns clojure-filtering.core
  (:gen-class)
  (:require [clojure.string :as str][clojure.data.json :as json]))



(defn -main []
  (println "Type a category:")
  (let [purchases (slurp "purchases.csv")
        ; spliting string into vector of strings by line.
        ; uses "imported" clojure.string which is assigned to "alias" str
        purchases (str/split-lines purchases)
        ; splits each line into a vector of strings based on commas
        purchases (map (fn [line]
                         (str/split line #","))
                       purchases)
        ; assigns local variable header to the first vector of people
        ; will now not include top line unless header is included in return statement
        header (first purchases)
        ; rebinds purchases to be everything but the first line
        purchases (rest purchases)
        ; rebinds purchases to a map of hashmaps
        ; each line is turned into a hashmap
        ; zipmap mixes the two collections header and line together
        ; each hashmap key comes from header and value from line
        purchases (map (fn [line]
                         (zipmap header line))
                       purchases)
        ; assigns var category to line read in after prompt
        category (read-line)
        ; filters by line
        ; evaluates statement between "category" value and what user passes in from prompt
        ; rebinds purchases to map of hashmaps that return true
        purchases (filter (fn [line]
                            (= (get line "category") category))
                          purchases)
        ; converts text file to json
        ; uses "import" which is declared in ns :require
        file-text (json/write-str purchases)]
    ; writes file
    (spit "filtered_purchases.json" file-text)))
