(ns mahoutinaction.ch14
  (:import [com.google.common.collect ConcurrentHashMultiset]
           [java.io BufferedReader File FileReader StringReader]
           [java.util ArrayList Arrays Collections TreeMap]
           [org.apache.lucene.analysis TokenStream]
           [org.apache.lucene.analysis.tokenattributes TermAttribute]
           [org.apache.lucene.analysis.standard StandardAnalyzer]
           [org.apache.lucene.util Version]
           [org.apache.mahout.classifier.sgd OnlineLogisticRegression L1]
           [org.apache.mahout.math RandomAccessSparseVector]
           [org.apache.mahout.math SequentialAccessSparseVector]
           [org.apache.mahout.vectorizer.encoders StaticWordValueEncoder
            ConstantValueEncoder Dictionary]))


(def features 10000)

(defn classify-ex
  []
  (let [traceDictionary   (TreeMap.)
        encoder           (StaticWordValueEncoder. "body")
        bias              (ConstantValueEncoder. "Intercept")
        lines             (ConstantValueEncoder. "Lines")
        analyzer          (StandardAnalyzer. (Version/LUCENE_31))
        numFeatures       10000
        learningAlgorithm (OnlineLogisticRegression. 20 numFeatures (L1.))
        base              (File. "resources/20news-bydate-train/")
        files             (ArrayList.)
        newsGroups        (Dictionary.)
        averageLL         0.0
        averageCorrect    0.0
        averageLineCount  0.0
        k                 0
        step              0.0
        bumps             [1 2 5]
        ]
    (.setProbes encoder 2)
    (.setTraceDictionary encoder traceDictionary)
    (.setTraceDictionary bias traceDictionary)
    (.setTraceDictionary lines traceDictionary)
    (.alpha learningAlgorithm 1)
    (.decayExponent learningAlgorithm 0.9)
    (.lambda learningAlgorithm 3.0e-5)
    (.learningRate learningAlgorithm 20)
    (doseq [newsgroup (.listFiles base)]
      (.intern newsGroups (.getName newsgroup))
      (.addAll files (Arrays/asList (.listFiles newsgroup))))
    (Collections/shuffle files)
    (printf "%d training files\n", (.size files))

    (doseq [file files]
      (let [reader     (BufferedReader. (FileReader. file))
            ng         (.. file getParentFile getName)
            actual     (.intern newsGroups ng)
            words      (ConcurrentHashMultiset/create)
            line-count (atom 0)]
        (dorun (map #(proc-line % line-count words) (line-seq reader)))
        ))
    ))