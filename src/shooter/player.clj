(ns shooter.player
  (:require [shooter.entity :refer :all]
            [shooter.world :as world]
            [shooter.shot :as shot]
            [quil.core :refer :all]))

(defn decrease-shoot-timer [this]
  (update-in this [:shoot-timer] - 0.05))

(defn check-shoot-timer [this]
  (assoc this :shoot (neg? (:shoot-timer this))))

(defn wrap-shoot-timer [this]
  (if (:shoot this)
    (assoc this :shoot-timer 1.0)
    this))

(defn shooting [this]
  (-> this
      decrease-shoot-timer
      check-shoot-timer
      wrap-shoot-timer))

(defn moving [this]
  (-> this
      (update-in [:pos] move (:speed this))
      (update-in [:pos] wrap)))

(defn update-player [this world]
  (-> this
      shooting
      moving))

(defn draw-player [enemy]
  (let [[x y] (:pos enemy)
        color (:color enemy)]
    (apply fill color)
    (rect x y 40 20)))

(defn player-affect-world [world player]
  (if (= 1.0 (:shoot-timer player))
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
                  :shoot-timer 1.0
                  :shoot false
                  :speed [0 -3]}))
