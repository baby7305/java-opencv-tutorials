package com.example.opencv.machineLearning.sort;

import com.example.opencv.machineLearning.data.IrisDatabase;
import org.opencv.core.Core;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;

public class KnnForIris {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        IrisDatabase iris = new IrisDatabase();
        KNearest knn = KNearest.create();

        System.out.println("knn.BRUTE_FORCE=" + knn.BRUTE_FORCE);
        System.out.println("knn.KDTREE=" + knn.KDTREE);
//1,3-->100%,2,5,6,7,8-->97.77,4-->95.5%,6
        knn.setDefaultK(8);
        knn.setIsClassifier(true);

        boolean r = knn.train(iris.getTrainingDataMat(), Ml.ROW_SAMPLE, iris.getTrainingLabelsMat());
        System.out.println("是否有訓練成功=" + r);

//隨便找3組測試

        float result0 = knn.predict(iris.getTestSample0FrTestMat());
        System.out.println("KNN預測result0=" + result0 + "類");

        float result1 = knn.predict(iris.getTestSample1FrTestMat());
        System.out.println("KNN預測result1=" + result1 + "類");

        float result2 = knn.predict(iris.getTestSample2FrTestMat());
        System.out.println("KNN預測result2=" + result2 + "類");

//測試精準值

//預測正確累加1
        int right = 0;
        float result;
        float[] answer = iris.getTestingLabels();
//System.out.println("組數"=iris.getTestingDataMat().rows());
        for (int i = 0; i < iris.getTestingDataMat().rows(); i++) {

            //System.out.println(iris.getTestingDataMat().row(i).dump());
            result = knn.predict(iris.getTestingDataMat().row(i));
            if (result == answer[i]) {
                right++;
            } else {
                System.out.println("預測錯誤!KNN預測是" + result + "正確是=" + answer[i]);
            }

        }
        System.out.println("KNN測試精準值=" + ((float) right / (float) iris.getTestingDataMat().rows()) * 100 + "%");


    }

}
