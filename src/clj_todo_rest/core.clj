(ns clj-todo-rest.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :refer [GET POST defroutes]]
            [clojure.data.json :as json]))

(def todos
  (ref []))

(defn post-todo [request]
  (dosync (ref-set todos (conj todos request)))
  {:status 404
   :headers {"content-type" "text/plain"}
   :body (json/write-str (deref todos))})

(defn page-404 [request]
  {:status 404
   :headers {"content-type" "text/plain"}
   :body "Page not found"})

(defn page-index [request]
  {:status 200
   :headers {"content-type" "text/json"}
   :body (json/write-str (deref todos))})

(defn page-second [request]
  {:status 200
   :headers {"content-type" "text/plain"}
   :body "Second page"})

(defroutes app
           (GET "/" request (page-index request))
           (POST "/" request (post-todo request))
           (GET "/second" request (page-second request))
           page-404)

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, ring!"})

(defn -main []
  (jetty/run-jetty app {:port 4000}))
