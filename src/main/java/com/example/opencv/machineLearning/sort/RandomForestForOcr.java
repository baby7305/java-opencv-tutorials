package com.example.opencv.machineLearning.sort;

import com.example.opencv.machineLearning.data.OcrDatabase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.Ml;
import org.opencv.ml.RTrees;


public class RandomForestForOcr {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        OcrDatabase ocr = new OcrDatabase();
        Mat rTreePriors = new Mat();
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, 50, 0.1);

        RTrees rtree = RTrees.create();
        //4-->60%,5-->90%,6-->70%,8,9-->50%,
        rtree.setMaxDepth(5);
        //3,4-->70%,2-->90%
        rtree.setMinSampleCount(2);
        rtree.setRegressionAccuracy(0);
        rtree.setUseSurrogates(false);
        rtree.setMaxCategories(2);
        rtree.setPriors(rTreePriors);
        rtree.setCalculateVarImportance(false);
        //8,10-->90%,9,11,20,30-->70%,12,40-->80,5,50-->70%,7-->60%
        //將以上參數設定給onlineOCR version使用
        rtree.setActiveVarCount(10);
        rtree.setTermCriteria(criteria);


        boolean r = rtree.train(ocr.getTrainingDataMat(), Ml.ROW_SAMPLE, ocr.getTrainingLabelsMat());

        System.out.println("是否有訓練成功=" + r);

        //隨便找3組測試

        float result0 = rtree.predict(ocr.getTestSample0FrTestMat());
        System.out.println("隨機森林預測result0=" + result0 + "類");

        float result1 = rtree.predict(ocr.getTestSample1FrTestMat());
        System.out.println("隨機森林預測result1=" + result1 + "類");

        float result2 = rtree.predict(ocr.getTestSample2FrTestMat());
        System.out.println("隨機森林預測result2=" + result2 + "類");

        //測試精準值

        //預測正確累加1
        int right = 0;
        float result;
        float[] answer = ocr.getTestingLabels();
        //System.out.println("組數"=iris.getTestingDataMat().rows());
        for (int i = 0; i < ocr.getTestingDataMat().rows(); i++) {

            //System.out.println(iris.getTestingDataMat().row(i).dump());
            result = rtree.predict(ocr.getTestingDataMat().row(i));
            if (result == answer[i]) {
                right++;
            } else {
                System.out.println("預測錯誤!隨機森林預測是" + result + ",正確是=" + answer[i]);
            }

        }
        System.out.println("隨機森林測試精準值=" + ((float) right / (float) ocr.getTestingDataMat().rows()) * 100 + "%");

        //again test 7
        result = rtree.predict(ocr.sample7);
        System.out.println("預測7結果=" + result);


    }
}
