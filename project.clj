(defproject book-server "1.0.0-SNAPSHOT"
  :description "A server which renders epub books as JSON in a RESTful way.  Essentially provides an API for a dumb client epub reader."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.codehaus.jackson/jackson-mapper-asl "1.9.5"]
                 [nl.siegmann.epublib/epublib-core "3.0-SNAPSHOT"]
                 [noir-async "1.0.0-SNAPSHOT"]]
  :plugins [[lein-swank "1.4.4"]]
  :repositories {"psiegman-snapshots" "https://github.com/psiegman/mvn-repo/raw/master/snapshots"}
  :warn-on-reflection true
  :main book-server.core)