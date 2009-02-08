; -- API START --

(ns nuttyprofessors.clojure.graphics
  (:import (java.awt Canvas Color GraphicsEnvironment Graphics2D)
	   (javax.swing JFrame WindowConstants)))

(def #^{:doc "Elapsed time since the window was created."
	:tag Long}
     tick nil)

(def #^{:doc "Global Graphics2D object used for drawing current frame."
	:tag Graphics2D} 
     g2d nil)

(defn radians [degrees] (Math/toRadians degrees))

(defmacro with-g2d-clone [& body]
  "Executes its body using a cloned g2d which is then disposted and discarded."
  `(binding [g2d (.create g2d)]
     ~@body
     (.dispose g2d)))

(defmacro rotate [angle & body]
  "Rotate the provided degrees."
  `(with-g2d-clone
    (.rotate g2d (radians ~angle))
    ~@body))

(defmacro translate [dx dy & body]
  "Translate the X and Y axis."
  `(with-g2d-clone
     (.translate g2d ~dx ~dy)
     ~@body))

(defn now
  "Current time in milliseconds."
  []
  (System/currentTimeMillis))

(def #^{:doc "Number of frames used to calculate FPS."} 
     fps-window 10)

(defn draw-fps
  "Draws the FPS."
  [timestamps]
  ;(println timestamps)
  (if (= fps-window (count timestamps))
    (let [ellapsedMillis (- (nth timestamps 0) (nth timestamps (dec fps-window)))
	  millisPerFrame (/ ellapsedMillis fps-window)
	  secsPerFrame (/ millisPerFrame 1000)
	  fps (/ 1 secsPerFrame)
	  string (format "%5.2f FPS" (double fps))]
      (.setColor g2d Color/BLUE)
      (.drawString g2d string 0 (.. g2d (getFontMetrics) (getMaxAscent))))))
    
(defn render-loop 
  "Endless loop for invoking the renderfn."
  [canvas width height renderfn]
  (let [bufferStrategy (.getBufferStrategy canvas)
	startTime (now)]

      (loop [timestamps []]
	(binding [g2d (.getDrawGraphics bufferStrategy)
		  tick (- (now) startTime)]

	  (doto g2d
	    (.setColor Color/BLACK)
	    (.fillRect 0 0 width height)
	    (.setColor Color/GREEN))

	  (with-g2d-clone (renderfn))	; invoke the supplied function

	  (with-g2d-clone (draw-fps timestamps))

	  (.dispose g2d)

	  (.show bufferStrategy))
	  ; Add a timestamp to the history used to determine FPS
	(recur (take fps-window (cons (now) timestamps))))))

(defn run-render-window [width height renderfn]
  "Creates a window then continuously draws new frames, delegating to the render function to draw each frame."
  (let [canvas (Canvas.)
	conf (.. GraphicsEnvironment (getLocalGraphicsEnvironment)
                  (getDefaultScreenDevice) (getDefaultConfiguration))
        frame (JFrame. conf)
        startTime (System/currentTimeMillis)]

    (.setSize canvas width height)
    (.setDefaultCloseOperation frame WindowConstants/EXIT_ON_CLOSE)
    (.. frame (getContentPane) (add canvas))

    (doto frame .pack .show)

    ; Create two buffers, so we're always writing into the next one that's off-screen.

    (.createBufferStrategy canvas 2)

    (render-loop canvas width height renderfn)))

; -- API END --


; -- CLIENT CODE START --
(def branch-angle 30)
(defn drawTree [length depth]
  (if (> depth 0)  (do
      (.drawLine g2d 0 0 length 0)
      (translate (int length) 0
        (rotate (- branch-angle)
          (drawTree (* length 0.75) (- depth 1)))
        (rotate branch-angle
          (drawTree (* length 0.75) (- depth 1)))
      )
    )))

(run-render-window 400 400 
		   #(translate 200 380 (rotate -90 (drawTree 50 12))))

; -- CLIENT CODE END --