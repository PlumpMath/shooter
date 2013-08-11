(ns shooter.keyboard
  (:require [quil.core :refer :all])
  (:import [java.awt.event KeyEvent]))

(def keymap {KeyEvent/VK_RIGHT :right
             KeyEvent/VK_LEFT :left
             KeyEvent/VK_UP :up
             KeyEvent/VK_DOWN :down})

(defn key-pressed [world-atom]
  (if-let [k (get keymap (key-code))]
    (swap! world-atom update-in [:keys] conj k)))

(defn key-released [world-atom]
  (if-let [k (get keymap (key-code))]
    (swap! world-atom update-in [:keys] disj k)))

(defn display-keys [world-atom]
  (fill 255)
  (text (str "Keys: " (:keys @world-atom)) 20 100))