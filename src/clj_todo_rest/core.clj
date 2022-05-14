(ns clj-todo-rest.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [compojure.core :as compojure]
            [compojure.route :as compojure-route]))

(def todos
  (atom ()))

(def todo-id
  (atom 1))

(defn add-todo [todo]
  (swap! todos #(conj % {:id @todo-id :text todo}))
  (swap! todo-id #(inc %))
  )

(defn page-404 []
  {:status  404
   :headers {"content-type" "text/plain"}
   :body    "Page not found"})

(defn get-all-todos []
  {:status  200
   :headers {"content-type" "text/json"}
   :body    (json/write-str @todos)})

(defn post-todo [request]
  (let [body (json/read-str (slurp (:body request)))]
    (log/info body)
    (add-todo body)
    {:status  200
     :headers {"content-type" "text/json"}
     :body    (json/write-str @todos)
     }))

(defn find-todo-by-id [id]
  (filter #(= (:id %) id) @todos))

(defn get-todo-by-id
  [request]
  (log/info request)
  (let [id (Integer/parseInt (:id (:params request)))]
    {:status 200
     :headers {"content-type" "text/json"}
     :body (json/write-str (find-todo-by-id id))}))

(compojure/defroutes app
                     (compojure/GET "/" request (get-all-todos))
                     (compojure/GET "/:id" request (get-todo-by-id request))
                     (compojure/POST "/" request (post-todo request))
                     (compojure-route/not-found page-404))

(defn -main [& args]
  (jetty/run-jetty app {:port 4000}))
