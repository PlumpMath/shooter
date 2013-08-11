(ns shooter.entity
  (:require [quil.core :refer [width height]]))

(defn move [pos delta]
  (let [[x y] pos
        [dx dy] delta]
    [(+ x dx) (+ y dy)]))

(defn wrap-float [x lower upper]
  (cond
   (< upper x) lower
   (< x lower) upper
   :else x))

(defn wrap [pos]
  (let [[x y] pos]
    [(wrap-float x 0 (width)) (wrap-float y 0 (height))]))

(defn create-entity [settings]
  (merge {:pos [100 100]
          :color [255 255 255]
          :draw-fn nil
          :update-fn nil}
         settings))

