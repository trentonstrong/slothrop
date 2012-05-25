(ns slothrop.server
  (:gen-class :main true)
  (:require [clojure.core.cache :as cache]
            [noir.server :as server]
            [noir.response :as response])
  (:use aleph.http
        noir.core
        lamina.core
        cheshire.core
        slothrop.books
        slothrop.epub))

(def cache (atom (cache/lru-cache-factory 10 {})))

(defpage "/book/id/:id" {book-id :id}
  "Retrieves root metadata about a given book"
  (let [book (get-book-by-id book-id)]
    (response/json
     (merge (get-metadata book)
            {:spine (get-spine book)
             :toc (get-toc book)}))))

(defpage "/book/id/:id/:resource" {book-id :id resource-href :resource}
  "Retrieves a book resource via its href"
  (let [book (get-book-by-id book-id)
        resource (get-resource-by-href book resource-href)
        content-type (get-resource-content-type resource)]
    {:status 200
     :headers {"Content-Type" content-type}
     :body (.getInputStream resource)}))

(def handler (server/gen-handler {:mode :dev
                                  :ns 'slothrop.server}))