(ns clj-todo-rest.handlers
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]))

;; Helper-functions

(def todos
  (atom ()))

(def todo-id
  (atom 1))

(defn add-todo [todo]
  (swap! todos #(conj % {:id @todo-id :text todo}))
  (swap! todo-id #(inc %))
  )

(defn find-todo-by-id [id]
  (filter #(= (:id %) id) @todos))

;; Routes

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

(defn get-todo-by-id
  [request]
  (log/info request)
  (let [id (Integer/parseInt (:id (:params request)))]
    {:status 200
     :headers {"content-type" "text/json"}
     :body (json/write-str (find-todo-by-id id))}))
