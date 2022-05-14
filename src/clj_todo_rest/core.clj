(ns clj-todo-rest.core
  (:require [ring.adapter.jetty :as jetty]
            [clj-todo-rest.routes :as routes]))

(defn -main []
  (jetty/run-jetty routes/app {:port 4000}))
