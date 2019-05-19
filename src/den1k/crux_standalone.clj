(ns den1k.crux-standalone
  (:require  [crux.api :as crux])
  (:import (crux.api ICruxAPI)))

(def crux-options
  {:kv-backend "crux.kv.memdb.MemKv"
   :db-dir     "data/db-dir-1"})

(defn run-system [{:keys [server-port] :as options} with-system-fn]
  (with-open [crux-system (crux/start-cluster-node options)]
    (with-system-fn crux-system)))

(declare s crux)
(comment
 (def s (future
         (run-system
          crux-options
          (fn [crux-system]
            (def crux crux-system)
            (Thread/sleep Long/MAX_VALUE)))))
 (future-cancel s)

 (.close system)
 )
