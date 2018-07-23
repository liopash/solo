(ns solo.talk
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [reagent.core :as r]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]))

(defonce state (r/atom {:slide 1}))

(defn show-state []
  [:div (str @state)])

(defn enter-something [& [r]]
  (let [s (if r r (r/cursor state [:name]))]
    [:input {:type "text"
             :default-value @s
             :on-change #(reset! s (-> % .-target .-value))}]))

(defn seiten-wahl []
  [:select {:value (:slide @state)
            :on-change #(swap! state assoc :slide (-> % .-target .-value js/parseInt))}
   (for [i [1 2 3]]
     [:option i])])

(defn *title [s]
  [:div.title.boxed s])

(def initial-focus
  (with-meta identity
    {:component-did-mount #(.focus (r/dom-node %))}))

(defmulti slide identity)

(defmethod slide 1 []
  (let [state-a (r/cursor state [:slide1 :a])
        state-b (r/cursor state [:slide1 :b])
        slide-2 (r/cursor state [:slide2 :a])]
    [:span [*title "Seite 1"]
     ^{:key :foo} [:div.content

      "Dies ist eine interaktve Seite mit einem Textfeld :"
      [enter-something state-a]
      
      [:div>ol

       [:li
        "Man kann Texte auch auf mehrere Zeilen aufteilen, um den
        Lesefluss nicht zu stören. So kann man einfach immer weiter
        und weiter schreiben." [:br]
        "Dieses Feld erhält auch noch den initialen Fokus."

        [initial-focus [enter-something state-b]]]
           
       [:li
        "Hier kann man noch mehr schreiben. Man kann natürlich auch
        Eingaben für eine zweite Seite machen."
          
        [enter-something slide-2]]]]]))

(defmethod slide 2 []
  (let [s (r/cursor state [:slide2 :a])]
    [:span [*title "Seite 2"]
     ^{:key :bar}
     [:div.content
      "Dies ist eine interaktve Seite mit einem Textfeld :"
      [initial-focus [enter-something s]]]]))

(defmethod slide 3 []
  [:span [*title "Seite 3"]
   [:div.content
      "Nur Test"]])

(defmethod slide :default []
  [:span "oops"])

(defn current-slide []
  (slide (:slide @state)))

(r/render [:div
           [show-state]
           [seiten-wahl]
           [current-slide]]
          (js/document.getElementById "main"))

