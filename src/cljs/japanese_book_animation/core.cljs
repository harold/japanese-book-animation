(ns japanese-book-animation.core
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.events.EventType :as event-type]))

(enable-console-print!)

(def state*
  (r/atom {:x 0}))

(defn rectangle
  [x]
  [:rect {:x (+ (* x 2) (:x @state*)) :y 50
          :width 1
          :height 500
          :style {:fill :black}}])

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
       (into [:g] (map rectangle (range 250)))])}))

(defn reload
  []
  (r/render [page] (.getElementById js/document "app")))

(defn ^:export main [] (reload))
