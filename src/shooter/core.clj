(ns shooter.core
  (:require [quil.core :refer :all]
            [shooter.world :as world]
            [shooter.enemy :as enemy]
            [shooter.entity :as entity]
            [shooter.player :as player]
            [shooter.states :as states]
            [shooter.keyboard :as keyboard])
  (:gen-class))

(defn setup [world-atom]
  (frame-rate 30)
  (swap! world-atom world/add-state-fn states/maybe-more-enemies)
  (swap! world-atom world/clear-ents)
  (swap! world-atom world/add-entity (player/create-player 50 50)))

(defn update-and-draw [world-atom]
  (swap! world-atom world/update-world)
  (world/draw @world-atom))

(defn create-window [world-atom]
  (let [sketch (defsketch demo
                 :title "Tiny Shooter"
                 :setup #(setup world-atom)
                 :draw #(update-and-draw world-atom)
                 :key-pressed #(keyboard/key-pressed world-atom)
                 :key-released #(keyboard/key-released world-atom)
                 :size [(:width @world-atom) (:height @world-atom)])]
    (swap! world-atom assoc :sketch sketch)))

(defn -main [& args]
  (-> (atom (world/create-world 1024 768))
    create-window))
