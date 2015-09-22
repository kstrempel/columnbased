(ns columnbased.core-test
  (:require [clojure.test :refer :all]
            [columnbased.core :refer :all]))

(deftest create-and-destroy
  (testing "failing open and destroy"
    (let [db (open-db "testdb")]
      (is (some? db))
      (destroy-db db))))

(deftest put-and-get
  (testing "failing put and get."
    (let [db (open-db "testdb")]
      (is (some? db))
      (lput db "key" "avalue")
      (is (= "avalue" (lget db "key")))
      (destroy-db db))))

(deftest iterate-test-empty
  (testing "failing iterator"
    (let [db (open-db "testdb")]
      (is (empty? (literate db nil nil)))
      (destroy-db db))))
