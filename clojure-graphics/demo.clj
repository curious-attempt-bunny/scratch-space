(ns demo
  (:use nuttyprofessors.clojure.graphics))

; Playing around with varying the angles, however it doesn't look very good
; because each frame is different from any prior frame, so it's jumpy
; like a timelapse.

(defn vary-branch-angle
  "Make small adjustments to the branch-angle, randomly."
  [branch-angle min-angle max-angle]
  (let [delta (- (rand 5) 2.5)
	new-angle (+ branch-angle delta)]
    (max min-angle (min max-angle new-angle))))

(defn drawTree [length depth branch-angle branch-deltafn]
  (if (> depth 0)  (do
      (.drawLine g2d 0 0 length 0)
      (translate (int length) 0
        (rotate (- branch-angle)
          (drawTree (* length 0.75) (dec depth) (branch-deltafn branch-angle) branch-deltafn))
        (rotate branch-angle
          (drawTree (* length 0.75) (dec depth) (branch-deltafn branch-angle) branch-deltafn))))))

(defn branch-delta
  [branch-angle]
  (vary-branch-angle branch-angle 20 40))

(run-render-window 400 400 
		   #(translate 200 380 (rotate -90 (drawTree 50 12 30 branch-delta))))

