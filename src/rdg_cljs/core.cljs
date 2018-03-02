(ns rdg-cljs.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(enable-console-print!)

(def react-data-grid
  ; Since react isn't loaded yet 
  (delay (js/React.createFactory js/ReactDataGrid)))

(defui ReactDataGridExample
  Object
  (componentWillMount
    [this]
    (let [rowCount 500]
      (set! (.-columns this)
        #js [#js{:key "id"    :name "ID"}
             #js{:key "title" :name "Title"}
             #js{:key "count" :name "Count"}]) 
      (set! (.-rowCount this) rowCount)
      (set! (.-rows this)
        (loop [i 0
               rows #js []]
          (if (>= i rowCount)
            rows
            (recur (inc i)
              (doto rows
                (.push 
                 #js {:id i 
                      :title (str "Simi - part " i)
                      :count (* 1073 i)}))))))     
      (set! (.-rowGetter this)
        #(nth (.-rows this) %)))) 

  (render [this]
    (dom/div nil "Hello, world!"

        (@react-data-grid
         #js   {:columns (.-columns this)
                :rowGetter (.-rowGetter this)
                :rowsCount (.-rowCount this)
                :minHeight 500}))))  

(def react-data-grid-example (om/factory ReactDataGridExample))

(defn ^:export rootApp
  [element-id]
  (js/ReactDOM.render (react-data-grid-example) (gdom/getElement element-id)))
