#lang racket
(require "engine.rkt")
;;flappy bird

(game (bounds 600 480)
      (title "flappy bird")
      (oval #:id "bird" #:x 30 #:y 80 #:width 60 #:height 30 #:fill-color "orange"
            #:when-colliding (lambda ()
                               (tell "bird" 'set 'stop? #t)
                               (text #:content "You Lose" #:text-size 20
                                     #:x 250 #:y 200 #:color "blue")
                               (tell "clock" 'set 'stop? #t)
                               )
            #:object-class "block"
            )
      (rectangle #:id "cilent" #:x 0 #:y 0 #:width 600 #:height 480)
      (when-left-clicking #:thunk (lambda ()
                                    (tell "bird" 'set 'velocity-y -0.56)
                                    (tell "bird" 'set 'accelerate-y 0.002)))
      (when-left-clicking #:object-class "block"
                          #:thunk (lambda ()
                                    (display "hello")))
      
      (every #:interval 1800
             #:thunk (lambda ()
                       (rectangle #:x 600 #:y 0 #:height (random 50 260) #:width 20 #:velocity-x -0.1
                                  #:class "block" #:fill-color "green"
                                  )
                       (rectangle #:x 600 #:y 300 #:height (random 50 180) #:width 20 #:velocity-x -0.1
                                  #:class "block" #:fill-color "blue"
                                  )
                       ; 这个地方应该有一个纵坐标大于屏幕高度的判断。如果大于了说明掉到底部了 也要GG
                       )
             #:id "clock"
             )
      )