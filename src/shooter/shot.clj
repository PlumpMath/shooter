(ns shooter.shot
  (:require [shooter.entity :refer :all]
            [quil.core :refer :all]))

(defn update-shot [this]
  (-> this
      (update-in [:pos] move (:speed this))))

(defn draw-shot [this]
  (let [[x y] (:pos this)
        color (:color this)]
    (apply fill color)
    (rect x y 30 5)))

(defn create-shot [x y]
  (create-entity {:pos [x y]
                  :draw-fn #(draw-shot %)
                  :update-fn #(update-shot %)
                  :color [255 0 0]
                  :speed [10 0]}))
