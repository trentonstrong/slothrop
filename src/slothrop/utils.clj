(ns slothrop.utils
  (:import [java.security MessageDigest]))


(defn get-md5-hash [content] (apply str
  (map (partial format "%02x")
    (.digest (doto (java.security.MessageDigest/getInstance "MD5")
                   .reset
                   (.update (.getBytes content)))))))