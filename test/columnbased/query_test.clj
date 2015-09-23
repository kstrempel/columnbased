(ns columnbased.query-test
  (:require [clojure.test :refer :all]
            [columnbased.core :refer :all]
            [columnbased.table :refer :all]
            [columnbased.insert :refer :all]
            [columnbased.query :refer :all]))

(deftest insert-and-query
  (testing "test a small query"
    (let [db (open-db "test")
          table (table-create db "first")]
      (try
        (insert-row db "first" {1 1 2 2 3 3})
        (insert-row db "first" {1 4 2 4 3 5})
        (insert-row db "first" {1 7 2 8 3 9})
        (insert-row db "first" {1 10 2 11 3 12})
        (is (= 4 (count (query-all db "first"))))
        (print (query-all db "first"))
        (finally (destroy-db db))))))
