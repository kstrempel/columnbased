(ns columnbased.insert-test
  (:require [clojure.test :refer :all]
            [columnbased.core :refer :all]
            [columnbased.table :refer :all]
            [columnbased.insert :refer :all]))

(deftest insert-row-test
  (testing "insert a row"
    (let [db (open-db "test")]
      (try
        (let [id (:id (lrow (table-create db "first")))]
          (insert-row db "first" {"value1" 1 "value2" 2}))
        (finally
          (destroy-db db))))))
