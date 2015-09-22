(ns columnbased.table-test
  (:require [clojure.test :refer :all]
            [columnbased.core :refer :all]
            [columnbased.table :refer :all]))

(deftest create-and-destroy-table
  (testing "create table and destroy"
    (let [db (open-db "test")
          table (table-create db "first")]
      (try
        (is (some? table))
        (is (= 0 (:last (lrow table))))
        (finally
          (table-delete db "first")
          (destroy-db db))))))

(deftest increase-row-count
  (testing "test increase row count"
    (let [db (open-db "test")
          table (table-create db "first")]
      (try
        (let [increased (table-increase-rows db "first")
              get-fresh (table-get db "first")]
          (is (some? table))
          (is (some? increased))
          (is (= 1 (:last (lrow increased))))
          (is (= 1 (:last get-fresh))))
        (finally
          (table-delete db "first")
          (destroy-db db))))))
