(ns columnbased.insert
  (:use [columnbased.core]
        [columnbased.table]))

(defn format-id [id]
  (format "%020d" id))

(defn create-table-key [table-id]
  (str "value/" table-id "/"))

(defn create-table-value-key [table-id id key]
  (str (create-table-key table-id) (format-id id) "/" key))

(defn insert-row [db table row]
  (let [info (table-get db table)
        id (:last (lrow (table-increase-rows db table)))
        table-id (:id info)]
    (doseq [[k v] row]
      (lput db (create-table-value-key table-id id k) v))
    (lsync db)
    id))
