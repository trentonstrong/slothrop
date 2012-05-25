(ns slothrop.core
  (:gen-class :main true)
  (:use cheshire.core))

(defn- read-configuration [path]
  "Reads server configuration file"
  (parse-string (slurp path)))

(defn -main [& args]
  