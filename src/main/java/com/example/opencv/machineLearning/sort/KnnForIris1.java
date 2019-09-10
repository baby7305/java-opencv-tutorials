package com.example.opencv.machineLearning.sort;

import com.example.opencv.machineLearning.data.IrisDatabase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;

public class KnnForIris1 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        IrisDatabase iris = new IrisDatabase();
        KNearest knn = KNearest.create();
//1,3-->100%,2,5,6,7,8-->97.77,4-->95.5%,6
//knn.setDefaultK(8);
//knn.setIsClassifier(true);
        boolean r = knn.train(iris.getTrainingDataMat(), Ml.ROW_SAMPLE, iris.getTrainingLabelsMat());
        System.out.println("是否有訓練成功=" + r);

//隨便找3組測試
        Mat results = new Mat();
        Mat neighborResponses = new Mat();
        Mat dist = new Mat();

//k=3
        float result0 = knn.findNearest(iris.getTestSample0FrTestMat(), 3, results, neighborResponses, dist);
        System.out.println("KNN預測result0=" + result0 + "類");
        System.out.println("results=" + results.dump());
        System.out.println("neighborResponses=" + neighborResponses.dump());
        System.out.println("dists=" + dist.dump());
        System.out.println("=============================================");


        float result1 = knn.findNearest(iris.getTestSample1FrTestMat(), 4, results, neighborResponses, dist);
        System.out.println("KNN預測result1=" + result1 + "類");
        System.out.println("results=" + results.dump());
        System.out.println("neighborResponses=" + neighborResponses.dump());
        System.out.println("dists=" + dist.dump());
        System.out.println("=============================================");

        float result2 = knn.findNearest(iris.getTestSample2FrTestMat(), 4, results, neighborResponses, dist);
        System.out.println("KNN預測result2=" + result2 + "類");
        System.out.println("results=" + results.dump());
        System.out.println("neighborResponses=" + neighborResponses.dump());
        System.out.println("dists=" + dist.dump());
        System.out.println("=============================================");
//測試精準值

//預測正確累加1
        int right = 0;
        float result;
        float[] answer = iris.getTestingLabels();
//System.out.println("組數"=iris.getTestingDataMat().rows());
        for (int i = 0; i < iris.getTestingDataMat().rows(); i++) {

            //System.out.println(iris.getTestingDataMat().row(i).dump());
            //1.3-->100,2,4,5-->97.77778%
            result = knn.findNearest(iris.getTestingDataMat().row(i), 8, results, neighborResponses, dist);
            if (result == answer[i]) {
                right++;
            } else {
                System.out.println("預測錯誤!KNN預測是" + result + "正確是=" + answer[i]);
            }

        }
//95%
        System.out.println("KNN測試精準值=" + ((float) right / (float) iris.getTestingDataMat().rows()) * 100 + "%");


    }

}
