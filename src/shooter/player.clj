(ns shooter.player
  (:require [shooter.entity :refer :all]
            [quil.core :refer :all]))

(defn update-player [this]
  (-> this
      (update-in [:pos] move (:speed this))
      (update-in [:pos] wrap)))

(defn draw-player [enemy]
  (let [[x y] (:pos enemy)
        color (:color enemy)]
    (apply fill color)
    (rect x y 40 20)))

(defn create-player [x y]
  (create-entity {:pos [x y]
                  :draw-fn #(draw-player %)
                  :update-fn #(update-player %)
                  :color [25 100 250]
                  :speed [0 -3]}))
