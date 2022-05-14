(ns clj-todo-rest.core
  (:require [ring.adapter.jetty :as jetty]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, ring!"})

(defn -main []
  (jetty/run-jetty handler {:port 4000}))
