(ns fractal.image
  (:import [marvinplugins MarvinPluginCollection]
           [marvin.image MarvinImage]
           [marvin.io MarvinImageIO])
  (:gen-class))

(defn confirm [predicate message value]
  (do (when-not (predicate value) (throw (Exception. message))))
      value)

(defn square? [image] (= (.getHeight image) (.getWidth image)))
(def get-size #(.getWidth %))

(defn load-image [image-path]
  (->> image-path (MarvinImageIO/loadImage) (confirm square? "Image must be a square")))
(defn empty-image [size] (MarvinImage. size size))
(defn save-image [image image-path] (MarvinImageIO/saveImage image image-path))

(defn copy-point [image [w h] image' [w' h']]
  (.setIntColor image' w' h' (.getIntColor image w h)))

(defn pos [scale orig-size orig-pos]
  (+ (* scale orig-size)
     (* (- 1 scale) orig-pos)
     -1))

(defn rotate [image]
  (let [size    (get-size image)
        image'  (empty-image size)]
    (do
      (doseq [w (range size)
              h (range size)]
        (copy-point image [(- size h 1) w] image' [w h]))
      image')))

(defn flip [image]
  (let [size    (get-size image)
        image'  (empty-image size)]
    (do
      (doseq [w (range size)
              h (range size)]
        (copy-point image [w (- size h 1)] image' [w h]))
      image')))

(defn beside [image1 image2 factor]
  (let [size    (get-size image1)
        image'  (empty-image size)]
    (do
      (doseq [w (range size)
              h (range size)]
        (copy-point image1 [w h] image' [(* factor w) h])
        (copy-point image2 [w h] image' [(pos factor size w) h]))
      image')))

(defn above [image1 image2 factor]
  (let [size    (get-size image1)
        image'  (empty-image size)]
    (do
      (doseq [w (range size)
              h (range size)]
        (copy-point image1 [w h] image' [w (* factor h)])
        (copy-point image2 [w h] image' [w (pos factor size h)]))
      image')))
