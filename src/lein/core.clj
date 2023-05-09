(ns lein.core
  (:require [org.httpkit.server :refer [run-server]]
            [clj-time.core :as t]
            [clojure.data.json :as json]))

(def port 8080)

(defn app [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body    (json/json-str {"fullName" "Yordan Georgiev" "timeNow" (str (t/time-now))})})

(defn -main [& args]
  (run-server app {:port port})
  (println (str "Server started on port " port)))