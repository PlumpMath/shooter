(ns shooter.core
  (:require [quil.core :refer [defsketch background width height]]
            [shooter.world :as world]
            [shooter.enemy :as enemy]
            [shooter.entity :as entity]
            [shooter.player :as player])
  (:gen-class))

(defn setup []
  )

(defn maybe-more-enemies [world]
  (if (< (count (world/get-ents-of-kind world :enemy)) 10)
    (world/add-entity world (enemy/create-enemy (:width world) (rand-int (:height world))))
    world))

(defn update-and-draw [world-atom]
  (swap! world-atom maybe-more-enemies)
  (swap! world-atom world/update-world)
  ((:background-fn @world-atom) @world-atom)
  (doseq [entity (:ents @world-atom)]
    ((:draw-fn entity) entity)))

(defn create-window [world-atom]
  (let [sketch (defsketch demo
                 :title "Tiny Shooter"
                 :setup setup
                 :draw #(update-and-draw world-atom)
                 :size [(:width @world-atom) (:height @world-atom)])]
    (swap! world-atom assoc :sketch sketch)))

(defn create-test-stuff [world-atom]
  (swap! world-atom world/add-entity (enemy/create-enemy 200 100))
  (swap! world-atom world/add-entity (enemy/create-enemy 300 150))
  (swap! world-atom world/add-entity (enemy/create-enemy 400 100))
  (swap! world-atom world/add-entity (player/create-player 50 50))
  )

(defn -main [& args]
  (let [world-atom (atom (world/create-world 1024 768))]
    (create-test-stuff world-atom)
    (create-window world-atom)
    world-atom))
