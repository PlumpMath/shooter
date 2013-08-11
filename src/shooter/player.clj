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
      ;(update-in [:pos] move (:speed this))
      (update-in [:pos] wrap)))

(defn move-player-if [this pred dir]
  (if pred
    (update-in this [:pos] move dir)
    this))

(defn keyboard-move [this pressed-keys]
  (-> this
      (move-player-if (:left pressed-keys) [-10 0])
      (move-player-if (:right pressed-keys) [10 0])
      (move-player-if (:up pressed-keys) [0 -10])
      (move-player-if (:down pressed-keys) [0 10])))

(defn update-player [this world]
  (-> this
      shooting
      (keyboard-move (:keys world))
      moving))

(defn draw-player [this]
  (let [[x y] (:pos this)
        [w h] (:size this)
        color (:color this)]
    (apply fill color)
    (rect x y w h)
    (apply fill (map (partial + 50) color))
    (rect (+ x (* w 0.5)) y (* w 0.5) (* h 0.5))))

(defn player-affect-world [world player]
  (if (= 1.0 (:shoot-timer player))
    (let [[x y] (:pos player)]
      (world/add-entity world (shot/create-shot x y)))
    world))

(defn create-player [x y]
  (create-entity {:pos [x y]
                  :size [80 50]
                  :draw-fn #(draw-player %)
                  :update-fn #(update-player %1 %2)
                  :affect-fn #(player-affect-world %1 %2)
                  :color [25 100 250]
                  :kind :player
                  :shoot-timer 1.0
                  :shoot false
                  :speed [0 -3]}))

