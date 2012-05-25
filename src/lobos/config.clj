(ns lobos.config
  (:use lobos.connectivity))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user "postgres"
   :password "dev"
   :subname "//33.33.33.10:5432/bookcloud"})

(open-global db)
