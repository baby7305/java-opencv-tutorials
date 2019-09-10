package com.example.opencv.machineLearning.sort;

import com.example.opencv.machineLearning.data.OcrDatabase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;


public class KnnForOcr {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        OcrDatabase ocr = new OcrDatabase();
        Mat results = new Mat();
        Mat neighborResponses = new Mat();
        Mat dist = new Mat();

        KNearest knn = KNearest.create();
        //knn.setDefaultK(10);
        //knn.setIsClassifier(true);
        boolean r = knn.train(ocr.getTrainingDataMat(), Ml.ROW_SAMPLE, ocr.getTrainingLabelsMat());


        System.out.println("是否有訓練成功=" + r);

        //隨便找3組測試

        float result0 = knn.predict(ocr.getTestSample0FrTestMat());
        System.out.println("KNN預測result0=" + result0 + "類");

        float result1 = knn.predict(ocr.getTestSample1FrTestMat());
        System.out.println("KNN預測result1=" + result1 + "類");

        float result2 = knn.predict(ocr.getTestSample2FrTestMat());
        System.out.println("KNN預測result2=" + result2 + "類");

        //測試精準值

        //預測正確累加1
        int right = 0;
        float result;
        float[] answer = ocr.getTestingLabels();
        //System.out.println("組數"=iris.getTestingDataMat().rows());
        for (int i = 0; i < ocr.getTestingDataMat().rows(); i++) {

            //System.out.println(iris.getTestingDataMat().row(i).dump());
            // result=knn.predict(ocr.getTestingDataMat().row(i));
            result = knn.findNearest(ocr.getTestingDataMat().row(i), 10, results, neighborResponses, dist);
            if (result == answer[i]) {
                right++;
            } else {
                System.out.println("預測錯誤!KNN預測是" + result + ",正確是=" + answer[i]);
            }

        }
        //100%
        System.out.println("KNN測試精準值=" + ((float) right / (float) ocr.getTestingDataMat().rows()) * 100 + "%");

        //again test 7
        result = knn.predict(ocr.sample7);
        System.out.println("預測7結果=" + result);
    }
}
