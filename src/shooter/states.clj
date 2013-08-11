(ns shooter.states
  (:require [quil.core :refer :all]
            [shooter.world :as world]
            [shooter.enemy :as enemy]
            [shooter.entity :as entity]
            [shooter.player :as player]
            [shooter.keyboard :as keyboard]))

(defn maybe-more-enemies [world]
  (if (< (count (world/get-ents-of-kind world :enemy)) 10)
    (world/add-entity world (enemy/create-enemy (:width world) (rand-int (:height world))))
    world))
