(ns slothrop.books
  (:require [clojure.core.cache :as cache]
            [korma.core :as relational])
  (:use slothrop.epub
        slothrop.entity))


(def book-cache (atom (cache/lru-cache-factory 10 {})))

(defn get-book-by-id [id]
  (if (contains? @book-cache id)
    (-> @book-cache
        (cache/hit id)
        (get id))
    (let [book  (read-epub "resources/pg76.epub")]
      (println "cache miss for book id: " id)
      (swap! book-cache assoc id book)
      book)))

  
(defn index-books [dir]
  false)