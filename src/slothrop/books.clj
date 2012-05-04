(ns slothrop.books
  (:use slothrop.epub)
  (:import (org.apache.lucene.document
       Document Field Field$Store Field$Index NumericField)
     (org.apache.lucene.analysis.standard StandardAnalyzer)
     (org.apache.lucene.store NIOFSDirectory)
     (org.apache.lucene.search
       IndexSearcher QueryWrapperFilter TermQuery Sort)
     (org.apache.lucene.queryParser QueryParser)
     (org.apache.lucene.index IndexWriter IndexWriter$MaxFieldLength
                              IndexReader Term CorruptIndexException)
     (org.apache.lucene.util Version)
     (java.io File IOException))

(def book-cache (atom (cache/lru-cache-factory 10 {})))

(defn get-book-by-id [id]
  (if (contains? @cache id)
    (-> @book-cache
        (book-cache/hit id)
        (get id))
    (let [book  (read-epub "resources/pg76.epub")]
      (println "cache miss for book id: " id)
      (swap! book-cache assoc id book)
      book)))

(defn inedx-exists? [path]
  "Verify that an index exists at the specified path and that it can be opened successfully."
  (if (.exists (File. path))
    (try
      (IndexReader/open (NIOFSDirectory. path))
      (catch IOException e
        false)
      (catch CorruptIndexException e
        false))
    false))

(defn create-index [dir]
  false)
  
  
(defn index-books [dir]
  false)