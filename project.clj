(defproject book-server "1.0.0-SNAPSHOT"
  :description "A server which renders epub books as JSON in a RESTful way.  Essentially provides an API for a dumb client epub reader."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.codehaus.jackson/jackson-mapper-asl "1.9.5"]
                 [nl.siegmann.epublib/epublib-core "3.0-SNAPSHOT"]
                 [org.apache.lucene/lucene-core "3.5.0"]
                 [org.apache.lucene/lucene-queries "3.5.0"]
                 [noir-async "1.0.0-SNAPSHOT"]
                 [org.clojure/core.cache "0.5.0"]]
  :plugins [[lein-swank "1.4.4"]]
  :repositories {"psiegman-snapshots" "https://github.com/psiegman/mvn-repo/raw/master/snapshots"}
  :warn-on-reflection true
  :main book-server.server)