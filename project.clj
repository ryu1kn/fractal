(defproject fractal "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "foo"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.github.downgoon/MarvinPlugins "1.5.5"]
                 [com.github.downgoon/MarvinFramework "1.5.5"]]
  :main ^:skip-aot fractal.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
