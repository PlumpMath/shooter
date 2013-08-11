(ns shooter.core
  (:require [quil.core :refer [defsketch background]]
            [shooter.world :as world]
            [shooter.enemy :as enemy]
            [shooter.player :as player])
  (:gen-class))

(defn setup []
  )

(defn update-and-draw [world-atom]
  (swap! world-atom world/update-world)
  (apply background (:background @world-atom))
  (doseq [entity (:ents @world-atom)]
    ((:draw-fn entity) entity)))

(defn create-window [world-atom]
  (let [sketch (defsketch demo
                 :title "Tiny Shooter"
                 :setup setup
                 :draw #(update-and-draw world-atom)
                 :size [320 240])]
    (swap! world-atom assoc :sketch sketch)))

(defn create-test-stuff [world-atom]
  (swap! world-atom world/add-entity (enemy/create-enemy 200 100))
  (swap! world-atom world/add-entity (enemy/create-enemy 300 150))
  (swap! world-atom world/add-entity (enemy/create-enemy 400 100))
  (swap! world-atom world/add-entity (player/create-player 50 50))
  )

(defn -main [& args]
  (let [world-atom (atom (world/create-world))]
    (create-test-stuff world-atom)
    (create-window world-atom)
    world-atom))
