(ns fractal.core
  (:import [marvinplugins MarvinPluginCollection]
           [marvin.image MarvinImage]
           [marvin.io MarvinImageIO])
  (:gen-class))

(defn load-image [image-path] (MarvinImageIO/loadImage image-path))
(defn save-image [image image-path] (MarvinImageIO/saveImage image image-path))

(defn rotate [image]
  (let [width'  (.getHeight image)
        height' (.getWidth image)
        image'  (MarvinImage. width' height')]
    (do
      (doseq [w (range width')
              h (range height')]
        (.setIntColor image' w h (.getIntColor image (- height' h 1) w)))
      image')))

(defn -main [& args]
  (let [image (load-image "./test.png")]
    (save-image (rotate image) "test-output.png")))
