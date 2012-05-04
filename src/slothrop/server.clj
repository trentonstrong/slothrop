(ns slothrop.server
  (:gen-class :main true)
  (:require [clojure.core.cache :as cache]
            [noir.server :as server]
            [noir.response :as response])
  (:use aleph.http
        noir.core
        lamina.core
        chesire.core
        slothrop.core))

(def cache (atom (cache/lru-cache-factory 10 {})))

(defn- get-book-by-id [id]
  (if (contains? @cache id)
    (-> @cache
        (cache/hit id)
        (get id))
    (let [book  (read-epub "resources/pg76.epub")]
      (println "cache miss for book id: " id)
      (swap! cache assoc id book)
      book)))

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

(defn- read-configuration [path]
  "Reads server configuration file"
  (parse-string (slurp path)))

(defn -main [& args]
  (let [config (read-configuration (first args))]
    (if (index-exists? (:index :path config))
    (server/start 8080))