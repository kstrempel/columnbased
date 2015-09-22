(ns columnbased.query
  (:require [clojure.string :as str :only split])
  (:use [columnbased.core]
        [columnbased.table]
        [columnbased.insert]))

(defn query-all [db table]
  (let [info (table-get db table)
        table-id (:id info)
        last (:last info)
        key (create-table-key table-id)]
    (map #(let [row (second %)]
            (hash-map :id (:id (first row))
                      :values (dissoc (apply merge (rest row)) :id)))
         (group-by :id
                   (map #(let [parts (reverse (str/split (first %) #"/"))]
                           (hash-map :id (second parts)
                                     (keyword (first parts)) (second %)))
                        (literate db (str key 0) (str key (inc last))))))))
