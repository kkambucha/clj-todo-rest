(ns clj-todo-rest.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [compojure.core :as compojure]
            [compojure.route :as compojure-route]))

(def todos
  (atom []))

(defn page-index [request]
  {:status  200
   :headers {"content-type" "text/json"}
   :body    (json/write-str (deref todos))})

(defn page-second [request]
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    "Second page"})

(defn page-404 [request]
  {:status  404
   :headers {"content-type" "text/plain"}
   :body    "Page not found"})

(defn post-todo [request]
  (let [body (json/read-str (slurp (:body request)))]
    (log/info body)
    (swap! todos #(conj % body))
    {:status  200
     :headers {"content-type" "text/plain"}
     :body    (json/write-str (deref todos))
     }))

(compojure/defroutes app
                     (compojure/GET "/" request (page-index request))
                     (compojure/POST "/" request (post-todo request))
                     (compojure/GET "/second" request (page-second request))
                     (compojure-route/not-found page-404))

(defn -main [& args]
  (jetty/run-jetty app {:port 4000}))
