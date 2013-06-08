(ns mahoutinaction.core
  (:use [mahoutinaction.ch02 :as ch02]
        [mahoutinaction.ch14 :as ch14]
        [mahoutinaction.ch15 :as ch15]))

(def INTRO "resources/intro.csv")
(def CH15SAMPLE "resources/ch15.sample.txt")

(defn -main []
  (do
    ;; Chapter 2
    ;(println "Recommend")
    ;(doall (map #(println %)
    ;            (ch02/recommend INTRO 1 1)))
    ;(println (str "Evaluation: "
    ;              (ch02/evaluator INTRO)))
    ;(let [stats (precision INTRO)]
    ;  (println (str "Precision: " (.getPrecision stats) " "
    ;                "Recall: " (.getRecall stats))))
    ;; Chapter 15
    ;(println "AUC metric")
    ;(ch15/auc-example-from-file CH15SAMPLE)
    ;(ch15/passing-data-for-auc-metric-classes)
    (ch14/classify-ex)))