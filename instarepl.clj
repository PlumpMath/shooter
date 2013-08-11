(use 'data-to-html.core)
(use 'quil.core)
(use 'shooter.core :reload)
(use 'shooter.world :reload)
(use 'shooter.enemy :reload)
(use 'shooter.player :reload)
(use 'shooter.shot :reload)

(def world-atom (-main))

(comment
  world-atom
  (inspect (vec (:ents @world-atom)))
  (count (:ents @world-atom))
  (swap! world-atom clear-ents)
  (:player-dir @world-atom)
  (swap! world-atom move-player [10 0])
  (swap! world-atom add-entity (create-enemy 300 150))
  (swap! world-atom add-entity (create-player 50 50))
  (swap! world-atom add-entity (create-shot 50 50))
  (count (get-ents-of-kind @world-atom :enemy))
  (get-player-index @world-atom)
  (swap! world-atom move-player [10 0])

  (nth (get-in @world-atom [:ents]) 15)
  )


