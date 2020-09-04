(ns fractal.core
  (:require [fractal.image :refer [beside above load-image save-image]])
  (:import [marvinplugins MarvinPluginCollection]
           [marvin.image MarvinImage]
           [marvin.io MarvinImageIO])
  (:gen-class))

(def george (load-image "./george.png"))

(defn p [image]
  (beside image (above image image 0.5) 0.5))

(defn right-push [image n factor]
  (if (= n 0)
    image
    (beside image
            (right-push image (- n 1) factor)
            factor)))

(defn -main [& args]
  (save-image (right-push george 2 0.75) "test-output.png"))
