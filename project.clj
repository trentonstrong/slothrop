(defproject slothrop "1.0.0-SNAPSHOT"
  :description "A server which renders epub books as JSON in a RESTful way.  Essentially provides an API for a dumb client epub reader."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [nl.siegmann.epublib/epublib-core "3.0-SNAPSHOT"]
                 [noir-async "1.0.0-SNAPSHOT"]
                 [org.clojure/core.cache "0.5.0"]
                 [cheshire "4.0.0"]
                 [korma "0.3.0-beta7"]
                 [postgresql "9.0-801.jdbc4"]]
  :plugins [[lein-swank "1.4.4"]]
  :repositories {"psiegman-snapshots" "https://github.com/psiegman/mvn-repo/raw/master/snapshots"}
  :warn-on-reflection true
  :main slothrop.server)