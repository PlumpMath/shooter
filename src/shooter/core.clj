(ns shooter.core
  (:require [quil.core :refer :all]
            [shooter.world :as world]
            [shooter.enemy :as enemy]
            [shooter.entity :as entity]
            [shooter.player :as player]
            [shooter.keyboard :as keyboard])
  (:gen-class))

(defn setup []
  (frame-rate 30))

(defn maybe-more-enemies [world]
  (if (< (count (world/get-ents-of-kind world :enemy)) 10)
    (world/add-entity world (enemy/create-enemy (:width world) (rand-int (:height world))))
    world))

(defn update-and-draw [world-atom]
  (swap! world-atom world/update-world)
  (world/draw @world-atom))

(defn create-window [world-atom]
  (let [sketch (defsketch demo
                 :title "Tiny Shooter"
                 :setup setup
                 :draw #(update-and-draw world-atom)
                 :key-pressed #(keyboard/key-pressed world-atom)
                 :key-released #(keyboard/key-released world-atom)
                 :size [(:width @world-atom) (:height @world-atom)])]
    (swap! world-atom assoc :sketch sketch)))

(defn init-world [world-atom]
  (swap! world-atom world/add-state-fn maybe-more-enemies)
  (swap! world-atom world/clear-ents)
  (swap! world-atom world/add-entity (player/create-player 50 50)))

(defn -main [& args]
  (let [world-atom (atom (world/create-world 1024 768))]
    (init-world world-atom)
    (create-window world-atom)
    world-atom))
