(ns shooter.world
  (:require [quil.core :refer :all]))

(defn create-world []
  {:background [25 80 100]
   :ents []})

(defn add-entity [world entity]
  (update-in world [:ents] conj entity))

(defn clear-ents [world]
  (assoc world :ents []))

(defn update-ents [ents-coll]
  (for [entity ents-coll]
    (let [update-fn (:update-fn entity)]
      (update-fn entity))))

(defn update-world [world]
  (-> world
      (update-in [:ents] update-ents)))
