(ns relog.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [clojure.data.json :as json]
            [hiccup.page :refer [include-js include-css html5]]
            [relog.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]))

(def mount-target
  [:div#app])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(def loading-page
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(defroutes routes
  (GET "/" [] loading-page)
  (GET "/hello/:name" [name] (str "hello there " name ", you well?"))

  (GET "/api/feed" [] (json/write-str {:some "map" :of "values"}))
  (resources "/")
  (not-found loading-page))

(def app (wrap-middleware #'routes))
