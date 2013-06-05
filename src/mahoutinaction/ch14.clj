(ns mahoutinaction.ch14
  (:import [java.io StringReader]
           [org.apache.lucene.analysis TokenStream]
           [org.apache.lucene.analysis.tokenattributes TermAttribute]
           [org.apache.lucene.analysis.standard StandardAnalyzer]
           [org.apache.lucene.util Version]
           [org.apache.mahout.math RandomAccessSparseVector]
           [org.apache.mahout.math SequentialAccessSparseVector]
           [org.apache.mahout.vectorizer.encoders StaticWordValueEncoder]))

(defn tokenizing-and-vectorizing-text
  "tokenizes, vectorizes, and prints a piece of text"
  []
  (let [encoder    (StaticWordValueEncoder. "text")
        analyzer   (StandardAnalyzer. (Version/LUCENE_31))
        in         (StringReader. "text to magically vectorize")
        ts         (.tokenStream analyzer "body", in)
        termAtt    (.addAttribute ts TermAttribute)
        v1         (RandomAccessSparseVector. 100)
        termBuffer (.termBuffer termAtt)
        termLen    (.termLength termAtt)
        w          (String. termBuffer 0 termLen)]
    (.addToVector encoder w 1.0 v1)
    (printf "%s\n" (SequentialAccessSparseVector. v1))))