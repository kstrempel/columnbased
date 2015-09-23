(ns columnbased.query
  (:require [clojure.string :as str :only split]
            [clj-leveldb :as l])
  (:use [columnbased.core]
        [columnbased.table]
        [columnbased.insert]))

(defn query-all [db table]
  (let [info (table-get db table)
        table-id (:id info)
        last (:last info)
        key (create-table-key table-id)]
    (let [current (atom -1)]
      (map #(apply merge %)
           (partition-by #(:id %)
                         (map #(let [parts (reverse (str/split (first %) #"/"))]
                                 (hash-map :id (second parts)
                                           (keyword (first parts)) (second %)))
                              (literate db
                                        (str key (format-id 0))
                                        (str key (format-id (inc last))))))))))
