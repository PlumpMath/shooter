(ns shooter.world
  (:require [quil.core :refer :all]
            [shooter.entity :refer [move]]
            [shooter.utils :refer :all]))

(defn default-background-fn [world]
  (background (pulse 3.0 100 200)
              (pulse 0.9 100 100)
              (pulse 1.6 50  250)))

(defn create-world [width height]
  {:width width
   :height height
   :background-fn #(default-background-fn %)
   :ents []
   :state-fns #{}
   :keys #{}})

(defn add-state-fn [world f]
  (update-in world [:state-fns] conj f))

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

(defn make-ents-affect-world [world]
  (let [ents-coll (:ents world)]
    (reduce affect world ents-coll)))

(defn remove-dead [world]
  (update-in world [:ents] (fn [ents]
                             (remove #(:dead %) ents))))

(defn update-state-fns [world]
  (let [state-fns (:state-fns world)]
    (reduce #(%2 %1) world state-fns)))

(defn draw [world]
  ((:background-fn world) world)
  (doseq [entity (:ents world)]
    ((:draw-fn entity) entity)))

(defn update-world [world]
  (-> world
      update-state-fns
      (update-in [:ents] update-ents world)
      (make-ents-affect-world)
      (remove-dead)))
