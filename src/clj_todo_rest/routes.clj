(ns clj-todo-rest.routes
  (:require [compojure.core :as compojure]
            [compojure.route :as compojure-route]
            [clj-todo-rest.handlers :as handlers]))

(compojure/defroutes app
                     (compojure/GET "/" _ (handlers/get-all-todos))
                     (compojure/GET "/:id" request (handlers/get-todo-by-id request))
                     (compojure/POST "/" request (handlers/post-todo request))
                     (compojure-route/not-found handlers/page-404))
