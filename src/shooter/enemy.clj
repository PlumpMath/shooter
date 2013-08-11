(ns shooter.enemy
  (:require [shooter.entity :refer :all]
            [shooter.world :refer :all]
            [quil.core :refer :all]))

(defn rand-range [lower upper]
  (let [diff (- upper lower)]
    (+ lower (* diff (rand)))))

(defn intersect? [[x1 y1] [w1 h1] [x2 y2] [w2 h2]]
  (and (< x1 (+ x2 w2))
       (< y1 (+ y2 h2))
       (< x2 (+ x1 w1))
       (< y2 (+ y1 h1))))

(defn hit? [this world]
  (let [pos (:pos this)
        size (:size this)
        shots (get-ents-of-kind world :shot)]
    (not (empty? (filter (fn [shot]
                           (intersect? pos size (:pos shot) (:size shot)))
                         shots)))))

(hit? {:kind :enemy
       :pos [29 29]
       :size [20 1]}
      {:ents [{:kind :shot
               :pos [10 20]
               :size [20 19]}]})

(defn die-if-hit-by-shot [this world]
  (if (hit? this world)
    (die this)
    this))

(defn update-enemy [enemy world]
  (-> enemy
      (die-if-hit-by-shot world)
      (update-in [:pos] move (:speed enemy))
      die-if-outside
      ;(update-in [:pos] wrap)
      ))

(defn draw-enemy [enemy]
  (let [[x y] (:pos enemy)
        [w h] (:size enemy)
        color (:color enemy)]
    (stroke 0)
    (apply fill color)
    (ellipse x y w h)
    (fill 255 255 255)
    (ellipse x (- y 10) (* 0.6 w) h)))

(defn create-enemy [x y]
  (create-entity {:pos [x y]
                  :size [50 20]
                  :draw-fn #(draw-enemy %)
                  :update-fn #(update-enemy %1 %2)
                  :color [255 (rand 255) 50]
                  :kind :enemy
                  :speed [(rand-range -10 -5) (rand-range -2.0 2.0)]}))

