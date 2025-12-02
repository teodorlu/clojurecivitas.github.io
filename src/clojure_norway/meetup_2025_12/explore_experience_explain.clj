(ns clojure-norway.meetup-2025-12.explore-experience-explain
  (:require [scicloj.kindly.v4.kind :as kind]))

(do
  (def signal-green "#97ff97")
  (def bright-teal "#a3ffff")
  (def big-blackgreen {:width "100%",
                       :height "50vw",
                       :background-color "black",
                       :color signal-green,
                       :display "flex",
                       :align-items "center",
                       :justify-content "center",
                       :flex-direction "column"})
  (defn splat [styles & children]
    (kind/hiccup (into [:div {:style (merge big-blackgreen {:font-size "72px" :font-weight "700"} styles)}]
                       children)))

  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(splat {}
       [:div "We explore"]
       [:div "We experience"]
       [:div "We explain"])

(splat {}
       [:div {:style {:align-items "left"}}
        [:div "A good explanation"]
        [:div {:style {:margin-left "1.5em"
                       :font-size "60px"}}
         [:div "explains how it works"]
         [:div "has wide reach"]
         [:div "is hard to vary"]]])

(splat {:text-align "center"} [:div "A document is a medium for explanations"])

(splat {}
       "Thank you to"
       [:a {:style {:color bright-teal}, :href "https://vergence.tech/"} "vergence.tech"]
       "for hosting.")
