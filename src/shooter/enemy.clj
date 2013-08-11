(ns shooter.enemy
  (:require [shooter.entity :refer :all]
            [quil.core :refer :all]))

(defn rand-range [lower upper]
  (let [diff (- upper lower)]
    (+ lower (* diff (rand)))))

(defn intersect? [[x1 y1] [w1 h1] [x2 y2] [w2 h2]]
  (and (< x1 (+ x2 w2))
       (< y1 (+ y2 h2))
       (< x2 (+ x1 w1))
       (< y2 (+ y1 h1))))

(defn get-shots [world]
  (let [ents (:ents world)]
    (filter #(= :shot (:kind %)) ents)))

(defn hit? [this world]
  (let [pos (:pos this)
        size (:size this)
        shots (get-shots world)]
    (not (empty? (filter (fn [shot]
                           (intersect? pos size (:pos shot) (:size shot)))
                         shots)))))

(hit? {:kind :enemy
       :pos [29 29]
       :size [20 1]}
      {:ents [{:kind :shot
               :pos [10 20]
               :size [20 9]}]})

(defn die-if-hit-by-shot [this world]
  (if (hit? this world)
    (die this)
    this))

(defn update-enemy [enemy world]
  (-> enemy
      (die-if-hit-by-shot world)
      (update-in [:pos] move (:speed enemy))
      (update-in [:pos] wrap)))

(defn draw-enemy [enemy]
  (let [[x y] (:pos enemy)
        [w h] (:size enemy)
        color (:color enemy)]
    (apply fill color)
    (rect x y w h)))

(defn create-enemy [x y]
  (create-entity {:pos [x y]
                  :draw-fn #(draw-enemy %)
                  :update-fn #(update-enemy %1 %2)
                  :color [255 100 50]
                  :speed [-5 (rand-range -2.0 2.0)]}))

