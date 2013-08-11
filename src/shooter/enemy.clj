(ns shooter.enemy
  (:require [shooter.entity :refer :all]
            [quil.core :refer :all]))

(defn rand-range [lower upper]
  (let [diff (- upper lower)]
    (+ lower (* diff (rand)))))

(defn update-enemy [enemy]
  (-> enemy
      (update-in [:pos] move (:speed enemy))
      (update-in [:pos] wrap)))

(defn draw-enemy [enemy]
  (let [[x y] (:pos enemy)
        color (:color enemy)]
    (apply fill color)
    (rect x y 20 20)))

(defn create-enemy [x y]
  (create-entity {:pos [x y]
                  :draw-fn #(draw-enemy %)
                  :update-fn #(update-enemy %)
                  :color [255 100 50]
                  :speed [-10 (rand-range -2.0 2.0)]}))

