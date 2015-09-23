(ns columnbased.mass
  (:gen-class)
  (:require [clojure.test :refer :all]
            [columnbased.core :refer :all]
            [columnbased.table :refer :all]
            [columnbased.insert :refer :all]
            [columnbased.query :refer :all]))

(defn masses [amount]
  (let [db (open-db "test")
        table (table-create db "first")]
    (try
      (print
       (with-out-str
         (time
          (do
            (doseq [i (range 0 amount)]
              (insert-row db "first" {1 i 2 i 3 i}))))))
      (print
       (with-out-str
         (time (println "Count of rows "
                        (count (query-all db "first"))))))
      (finally (destroy-db db)))))

(defn main []
  (masses 10000))
