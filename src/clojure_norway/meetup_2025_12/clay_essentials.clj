(ns clojure-norway.meetup-2025-12.clay-essentials
  (:require [clojure.string :as str]
            [tablecloth.api :as tc]))

;; # Clay
;;
;; - Clay is a notebook you control from your REPL.
;; - Clay is written by Daniel and contributors
;;   https://github.com/scicloj/clay/graphs/contributors

;; ## Clay installation
;;
;; You typically control Clay with two key bindings:
;;
;; - `clay-make-ns-html`: View your namespace
;; - `clay-make-last-sexp`: View your last form
;;
;; Instructions for Calva, Emacs, Neovim and Intellij is found in Clay's documentation:
;; https://scicloj.github.io/clay/#setup

;; ## Datasets with Tablecloth

;; You can use all the normal Clojure data structures:

(def color-names (-> "https://gist.githubusercontent.com/mordka/c65affdefccb7264efff77b836b5e717/raw/e65646a07849665b28a7ee641e5846a1a6a4a758/colors-list.txt"
                     slurp str/split-lines vec))

{:hello "world"
 :numbers (map (partial * 4) (range 10))
 :text (str (str/join ", " (repeatedly 10 #(rand-nth color-names)))
            " space pirates.")}

;; However, datasets (tables) are often convenient.
;; This is an example dataset provided by Noj:

(require '[scicloj.metamorph.ml.rdatasets :as rdatasets])
(rdatasets/gapminder-gapminder)

;; To operate on the dataset, we can use Tablecloth, a nice, high-level dataset API.

(require '[tablecloth.api :as tc])

;; Now, order rows by year, then country.

(def ds
  (-> (rdatasets/gapminder-gapminder)
      (tc/order-by [:year :country])))

;; ## Plots with Tableplot
;;
;; Tableplot plots tables with near-zero friction.

(require '[scicloj.tableplot.v1.plotly :as plotly])

;; GDP per year, group by country:

(-> ds
    (plotly/layer-line {:=x :year
                        :=y :gdp-percap
                        :=color :country}))

;; Why does gdp per capita decrease in Kuwait?

(def kuwait? (comp #{"Kuwait"} :country))

(def ds-kuwait
  (-> ds
      (tc/select-rows kuwait?)))

;; Population has generally risen.

(-> ds-kuwait
    (plotly/layer-line {:=x :year
                        :=y :gdp-percap})
    (plotly/layer-line {:=x :year
                        :=y :pop}))

;; What about total gdp?

(-> ds-kuwait
    (tc/* :gdp [:gdp-percap :pop])
    (plotly/layer-line {:=x :year
                        :=y :gdp}))



;; GDP has been rising, overall, even if GDP per capita has been falling.

;; Wikipedia on what happened:
;;
;; > From 1946 to 1982, Kuwait underwent large-scale modernization, largely
;; > based on income from oil production. In the 1980s, Kuwait experienced a
;; > period of geopolitical instability and an economic crisis following the
;; > stock market crash. It suffered pro-Iranian attacks during the Iran–Iraq
;; > War, as a result of Kuwait's financial support to Iraq. In 1990, the state
;; > of Kuwait was invaded, had a puppet regime installed, and was subsequently
;; > annexed by Ba'athist Iraq under the leadership of Saddam Hussein following
;; > disputes over oil production. The Iraqi occupation of Kuwait ended on 26
;; > February 1991, after a U.S. and Saudi Arabia–led international coalition
;; > expelled Iraqi forces from the country during the Gulf War.[24]
;;
;; Source: https://en.wikipedia.org/wiki/Kuwait
