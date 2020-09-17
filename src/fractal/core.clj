(ns fractal.core
  (:require [fractal.image :refer [beside above flip load-image save-image]])
  (:gen-class))

(def george (load-image "./george.png"))

(defn right-push [image n factor]
  (if (= n 0)
    image
    (beside image
            (right-push image (- n 1) factor)
            factor)))

(defn p1 [p] (beside p (above p p 0.5) 0.5))
(defn p2 [p] (right-push p 2 0.75))
(defn p3 [p] (flip p))
(defn p4 [p] (beside p (flip p) 0.5))
(defn p5 [p] (above p (flip p) 0.5))

(defn -main [& args]
  (save-image ((comp p5 p4) george) "test-output.png"))
