(use 'data-to-html.core)
(use 'quil.core)
(use 'shooter.core :reload)
(use 'shooter.world :reload)
(use 'shooter.enemy :reload)
(use 'shooter.player :reload)
(use 'shooter.shot :reload)

(def world-atom (-main))

(count (:ents @world-atom))

@world-atom

(inspect (vec (:ents @world-atom)))
(swap! world-atom clear-ents)

(swap! world-atom add-entity (create-player 50 50))
(swap! world-atom add-entity (create-enemy 300 150))

