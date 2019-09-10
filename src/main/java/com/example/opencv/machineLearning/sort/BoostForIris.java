package com.example.opencv.machineLearning.sort;

import com.example.opencv.machineLearning.data.IrisDatabase;
import org.opencv.core.Core;
import org.opencv.ml.Boost;
import org.opencv.ml.Ml;


//與13_9_2比較(3元vs 2元)
public class BoostForIris {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        IrisDatabase iris = new IrisDatabase();

        Boost boost = Boost.create();
        boost.setBoostType(Boost.DISCRETE);
        boost.setWeakCount(3);
        boost.setMinSampleCount(4);
        boost.setMaxCategories(4);
        boost.setMaxDepth(2);


        boolean r = boost.train(iris.getTrainingDataMat(), Ml.ROW_SAMPLE, iris.getTrainingLabelsMat());
        System.out.println("是否有訓練成功=" + r);

//隨便找3組測試

        float result0 = boost.predict(iris.getTestSample0FrTestMat());
        System.out.println("Boost預測result0=" + result0 + "類");

        float result1 = boost.predict(iris.getTestSample1FrTestMat());
        System.out.println("Boost預測result1=" + result1 + "類");

        float result2 = boost.predict(iris.getTestSample2FrTestMat());
        System.out.println("Boost預測result2=" + result2 + "類");

//測試精準值

//預測正確累加1
        int right = 0;
        float result;
        float[] answer = iris.getTestingLabels();
//System.out.println("組數"=iris.getTestingDataMat().rows());
        for (int i = 0; i < iris.getTestingDataMat().rows(); i++) {

            //System.out.println(iris.getTestingDataMat().row(i).dump());
            result = boost.predict(iris.getTestingDataMat().row(i));
            if (result == answer[i]) {
                right++;
            } else {
                System.out.println("預測錯誤!Boost預測是" + result + "正確是=" + answer[i]);
            }

        }
        System.out.println("Boost測試精準值=" + ((float) right / (float) iris.getTestingDataMat().rows()) * 100 + "%");


    }

}
