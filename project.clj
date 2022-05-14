(defproject clj-todo-rest "0.1.0-SNAPSHOT"
  :description "todo rest api"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.4.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [compojure "1.6.3"]]
  :plugins [[lein-ring "0.12.6"]]
  :repl-options {:init-ns clj-todo-rest.core}
  :main clj-todo-rest.core)
