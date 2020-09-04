(ns fractal.core
  (:import [marvinplugins MarvinPluginCollection]
           [marvin.image MarvinImage]
           [marvin.io MarvinImageIO])
  (:gen-class))

(defn load-image [image-path] (MarvinImageIO/loadImage image-path))
(defn save-image [image image-path] (MarvinImageIO/saveImage image image-path))

(defn pos [scale orig-size orig-pos]
  (+ (* scale orig-size)
     (* (- 1 scale) orig-pos)
     -1))

(defn rotate [image]
  (let [width'  (.getHeight image)
        height' (.getWidth image)
        image'  (MarvinImage. width' height')]
    (do
      (doseq [w (range width')
              h (range height')]
        (.setIntColor image' w h (.getIntColor image (- height' h 1) w)))
      image')))

(defn beside [image1 image2 factor]
  (let [width'  (.getWidth image1)
        height' (.getHeight image1)
        image'  (MarvinImage. width' height')]
    (do
      (doseq [w (range width')
              h (range height')]
        (.setIntColor image' (* factor w) h (.getIntColor image1 w h)))
      (doseq [w (range width')
              h (range height')]
        (.setIntColor image' (pos factor width' w) h (.getIntColor image2 w h)))
      image')))

(defn above [image1 image2 factor]
  (let [width'  (.getWidth image1)
        height' (.getHeight image1)
        image'  (MarvinImage. width' height')]
    (do
      (doseq [w (range width')
              h (range height')]
        (.setIntColor image' w (* factor h) (.getIntColor image1 w h)))
      (doseq [w (range width')
              h (range height')]
        (.setIntColor image' w (pos factor height' h) (.getIntColor image2 w h)))
      image')))

(def george (load-image "./george.png"))
(def empty-image (MarvinImage. (.getWidth george) (.getHeight george)))

(defn p [image]
  (beside image (above empty-image image 0.5) 0.5))

(defn -main [& args]
  (save-image (p george) "test-output.png"))
