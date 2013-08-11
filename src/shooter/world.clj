(ns shooter.world
  (:require [quil.core :refer :all]))

(defn create-world [width height]
  {:width width
   :height height
   :background [25 80 100]
   :ents []})

(defn add-entity [world entity]
  (update-in world [:ents] conj entity))

(defn clear-ents [world]
  (assoc world :ents []))

(defn get-ents-of-kind [world kind]
  (let [ents (:ents world)]
    (filter #(= kind (:kind %)) ents)))

(defn update-ents [ents-coll world]
  (for [entity ents-coll]
    (let [update-fn (:update-fn entity)]
      (update-fn entity world))))

(defn affect [world entity]
  (if-let [affect-fn (:affect-fn entity)]
    (affect-fn world entity)
    world))

(defn ents-affect-world [world]
  (let [ents-coll (:ents world)]
    (reduce affect world ents-coll)))

(defn remove-dead [world]
  (update-in world [:ents] (fn [ents]
                             (remove #(:dead %) ents))))

(defn update-world [world]
  (-> world
      (update-in [:ents] update-ents world)
      (ents-affect-world)
      (remove-dead)))
