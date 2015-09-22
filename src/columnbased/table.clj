(ns columnbased.table
  (:use [columnbased.core])
  (:import [java.util UUID]))

(defn create-table-name [name]
  (str "table/" name))

(defn table-create [db name]
  (lput db (create-table-name name) {:id (UUID/randomUUID) :last 0}))

(defn table-delete [db name]
  (ldelete db (create-table-name name)))

(defn table-get [db name]
  (lget db (create-table-name name)))

(defn table-increase-rows [db name]
  (let [info (table-get db name)]
    (lput db (create-table-name name) (assoc info :last (inc (:last info))))))
