(ns book-server.core
  (:require [clojure.java.io :as io])
  (:import [nl.siegmann.epublib.epub EpubReader]
           [nl.siegmann.epublib.domain Book TableOfContents TOCReference SpineReference Resource]))

(defn- read-epub-from-stream  [^java.io.InputStream stream]
  "Read an application/epub or application/epub+zip format file from a Java InputStream"
  (.readEpub (EpubReader.) stream))

(defn read-epub [path]
  "Read an application/epub or application/epub+zip format file from a URL or filesystem path"
  (read-epub-from-stream (io/input-stream path)))

(declare resolve-toc-reference)

(defn resolve-toc-references [references]
  "Recursively resolve TOCReference items into hash-maps"
  (if (> (.size references) 0)
    (map resolve-toc-reference references)
    nil))

(defn resolve-toc-reference [^TOCReference reference]
  "Resolve a single TOCReference into a hash-map"
  (hash-map
   :title (.getTitle reference)
   :href (.getCompleteHref reference)
   :fragment (.getFragmentId reference)
   :resource-id (.getResourceId reference)
   :children (resolve-toc-references (.getChildren reference))))
           
(defn get-toc [^Book book]
  "Get a map representation of a book's table of contents"
  (let [toc (.getTableOfContents book)]
    (hash-map
     :depth (.calculateDepth toc)
     :root (resolve-toc-references (.getTocReferences toc)))))

(defn resolve-spine-reference [^SpineReference reference]
  "Resolve a spine resource reference into a hash-map"
  (let [resource (.getResource reference)]
    (hash-map
     :href (.getHref resource)
     :id (.getId resource)
     :mime-type (str (.getMediaType resource))
     :encoding (.getInputEncoding resource))))

(defn get-spine [^Book book]
  "Get a map representation of the spine, an list of resources ordered by intended display order"
  (let [spine (.getSpine book)]
    (hash-map
     :size (.size spine)
     :resources (map resolve-spine-reference (.getSpineReferences spine)))))

(defn get-metadata [^Book book]
  "Retrieve map representation of book metadata, such as title, author..."
  (let [metadata (.getMetadata book)]
    {:identifier (.getValue (or
                  (first (filter #(.isBookId %) (.getIdentifiers metadata)))
                  (first (.getIdentifiers metadata)))) 
     :title (.getFirstTitle metadata)
     :all_titles (.getTitles metadata)
     :types (.getTypes metadata)
     :subjects (.getSubjects metadata)
     :descriptions (.getDescriptions metadata)
     :language (.getLanguage metadata)
     :format (.getFormat metadata)
     :dates (map str (.getDates metadata))
     :authors (map str (.getAuthors metadata))
     :contributors (map str (.getContributors metadata))
     :publishers (.getPublishers metadata)
     :rights (.getRights metadata)}))

(defn get-resource-by-href [^Book book href]
  "Retrieves a resource via its href"
  (let [resources (.getResources book)]
    (.getByHref resources href)))

(defn get-resource-content-type [^Resource resource]
  "Determines and returns a resource's content type (mime-type)"
  (.getName (.getMediaType resource)))