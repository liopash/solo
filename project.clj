(defproject solo "0.1.0-SNAPSHOT"
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [swank-clojure/swank-clojure "1.4.3"]
                 [log4j/log4j "1.2.17"]
                 
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]

                 [ring/ring-headers "0.3.0"]
                 [ring/ring-codec "1.1.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.1"]

                 [hiccup "1.0.5"]
                 
                 [compojure "1.6.0"]]
  :aliases {"make-jumpstart-jsf" ["with-profile" "jumpstart-jsf" "do" ["clean"] "jar"]}
  :plugins [[lein-swank "1.4.5"]
            [lein-ring "0.12.4"]]
  :ring {:handler solo.web/app
         :nrepl {:start? true
                 :port 9998}}
  :profiles {:jumpstart-jsf {:resource-paths ^:replace ["jumpstart/resources"]
                             :aot :all
                             :main solo.jumpstart.jsf
                             :source-paths ^:replace ["jumpstart/src"]}})
