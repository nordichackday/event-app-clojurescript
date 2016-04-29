(ns future-app.android.core
  (:require [reagent.core :as r]))

(def react-native (js/require "react-native"))

(def app-registry (.-AppRegistry react-native))
(def text (r/adapt-react-class (.-Text react-native)))
(def view (r/adapt-react-class (.-View react-native)))
(def image (r/adapt-react-class (.-Image react-native)))
(def list-view (r/adapt-react-class (.-ListView react-native)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight react-native)))

(def logo-img (js/require "./images/cljs.png"))

(def languages ["English" "Suomi" "Svenska"])

(def selected-language (r/atom "English"))

(defn- language-selection-component []
  [view
   (when (= @selected-language "English")
     (for [lang languages]
       [text {:key lang :on-press #(reset! selected-language lang) :style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} lang]))])

(defn app-root []
  (fn []
    [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
     [language-selection-component]
     ;example to add image
     [image {:source logo-img
             :style  {:width 80 :height 80 :margin-bottom 30}}]
     [text "For debugging. Selected language:" @selected-language]
     [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                           :on-press #(reset! selected-language "Svenska")}
      [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]]))

(defn init []
  (.registerComponent app-registry "futureApp" #(r/reactify-component app-root)))
