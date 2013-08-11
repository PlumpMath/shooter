(ns shooter.player
  (:require [shooter.entity :refer :all]
            [shooter.world :as world]
            [shooter.shot :as shot]
            [quil.core :refer :all]))

(defn update-player [this world]
  (-> this
      (update-in [:pos] move (:speed this))
      (update-in [:pos] wrap)))

(defn draw-player [enemy]
  (let [[x y] (:pos enemy)
        color (:color enemy)]
    (apply fill color)
    (rect x y 40 20)))

(defn player-affect-world [world player]
  (if (< (rand) 0.03)
    (let [[x y] (:pos player)]
      (world/add-entity world (shot/create-shot x y)))
    world))

(defn create-player [x y]
  (create-entity {:pos [x y]
                  :draw-fn #(draw-player %)
                  :update-fn #(update-player %1 %2)
                  :affect-fn #(player-affect-world %1 %2)
                  :color [25 100 250]
                  :kind :player
                  :speed [0 -3]}))
