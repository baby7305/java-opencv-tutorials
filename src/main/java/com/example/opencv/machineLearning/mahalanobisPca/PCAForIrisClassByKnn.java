package com.example.opencv.machineLearning.mahalanobisPca;

import com.example.opencv.machineLearning.data.IrisDatabase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;

public class PCAForIrisClassByKnn {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        IrisDatabase iris = new IrisDatabase();
        KNearest knn = KNearest.create();

//1,3-->100%,2,5,6,7,8-->97.77,4-->95.5%,6
        knn.setDefaultK(8);
        knn.setIsClassifier(true);
//knn.setAlgorithmType(knn.KDTREE);
        knn.setAlgorithmType(knn.BRUTE_FORCE);
        knn.setEmax(1);


        Mat meanTrain = new Mat();
        Mat vectorsTrain = new Mat();
        Mat trainIrisDataT = iris.getTrainingDataMat().t();

        Core.PCACompute(trainIrisDataT, meanTrain, vectorsTrain, 2);
        Mat pcaTrainIris = vectorsTrain.t();

        boolean r = knn.train(pcaTrainIris, Ml.ROW_SAMPLE, iris.getTrainingLabelsMat());
        System.out.println("是否有訓練成功=" + r);


        Mat meanTest = new Mat();
        Mat vectorsTest = new Mat();
        Mat testIrisDataT = iris.getTestingDataMat().t();
        Core.PCACompute(testIrisDataT, meanTest, vectorsTest, 2);

        Mat pcaTestIris = vectorsTest.t();


//測試精準值

//預測正確累加1
        int right = 0;
        float result;
        float[] answer = iris.getTestingLabels();
//System.out.println("組數"=iris.getTestingDataMat().rows());
        for (int i = 0; i < iris.getTestingDataMat().rows(); i++) {

            //System.out.println(iris.getTestingDataMat().row(i).dump());
            result = knn.predict(pcaTestIris.row(i));
            if (result == answer[i]) {
                right++;
            } else {
                System.out.println("預測錯誤!KNN預測是" + result + "正確是=" + answer[i]);
            }

        }
        System.out.println("KNN測試精準值=" + ((float) right / (float) iris.getTestingDataMat().rows()) * 100 + "%");
    }

}
