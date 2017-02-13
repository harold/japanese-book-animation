(ns japanese-book-animation.core
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.events.EventType :as event-type]))

(enable-console-print!)

(def state*
  (r/atom {:x 0}))

(defn page
  []
  (r/create-class
   {:component-did-mount
    (fn [this]
      (println "did-mount")
      (events/listen (r/dom-node this)
                     event-type/MOUSEMOVE
                     (fn [e] (swap! state* assoc :x (.-offsetX e)))))

    :comopnent-will-unmount
    (fn [this]
      (println "will-unmount")
      (events/removeAll (r/dom-node this)))

    :reagent-render
    (fn []
      [:svg {:width "100%" :height "100%"}
       [:rect {:x (:x @state*) :y 50
               :width 300
               :height 100
               :style {:fill :red}}]])}))

(defn reload
  []
  (r/render [page] (.getElementById js/document "app")))

(defn ^:export main [] (reload))
