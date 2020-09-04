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

(defn p [image] (beside image image 0.3))

(defn -main [& args]
  (let [image (load-image "./test.png")]
    (save-image (p image) "test-output.png")))
