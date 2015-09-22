(ns columnbased.core
  (:require [clj-leveldb :as l]
            [byte-streams :as bs]
            [clojure.edn :as edn]))

(defn open-db [name]
  (let [db (l/create-db name {:key-decoder byte-streams/to-string
                              :val-decoder (comp edn/read-string bs/to-char-sequence)
                              :val-encoder pr-str})]
    {:name name :db db}))

(defn destroy-db [db]
  (.close (:db db))
  (l/destroy-db (:name db)))

(defn lput [db key value]
  (l/put (:db db) key value)
  (hash-map key value))

(defn lget [db key]
  (l/get (:db db) key))

(defn ldelete [db key]
  (l/delete (:db db) key))

(defn literate [db start end]
  (let [result (l/iterator (:db db) start end)]
    (if (nil? result)
      []
      result)))

(defn lrow [row]
  (val (first row)))
