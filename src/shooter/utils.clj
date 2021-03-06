(ns shooter.utils
  (:require [quil.core :refer :all]))

(defn rand-range [lower upper]
  (let [diff (- upper lower)]
    (+ lower (* diff (rand)))))

(defn intersect? [[x1 y1] [w1 h1] [x2 y2] [w2 h2]]
  (and (< x1 (+ x2 w2))
       (< y1 (+ y2 h2))
       (< x2 (+ x1 w1))
       (< y2 (+ y1 h1))))

(defn pulse [cycle-speed lower upper]
  (+ lower
     (* (- upper lower)
        (sin (* (millis) 0.001 cycle-speed)))))