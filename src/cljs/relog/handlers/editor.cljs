(ns relog.handlers.editor
  (:require-macros [cljs.core.async.macros :refer  [go]])
  (:require [reagent.core :as r]
            [re-frame.core :refer [def-event dispatch]]
            [cljs-http.client :as http]
            [cljs.core.async :refer  [<!]]))

(def-event
  :fetch-all-posts
  (fn [db _]
  (go (let [response (<! (http/get "http://localhost:3000/api/posts"))]
        (let [json (JSON/parse (:body response))]
          (dispatch [:posts-received (js->clj json :keywordize-keys true)]))))
  db))

(def-event
  :posts-received
  (fn [db [_ feedResponse]]
  (-> db
      (assoc :posts feedResponse))))

