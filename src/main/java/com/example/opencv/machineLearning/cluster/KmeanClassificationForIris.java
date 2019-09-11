package com.example.opencv.machineLearning.cluster;

import com.example.opencv.machineLearning.data.IrisDatabase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;

public class KmeanClassificationForIris {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        IrisDatabase iris = new IrisDatabase();

        Mat labels = new Mat();
        TermCriteria criteria = new TermCriteria(TermCriteria.COUNT, 10, 1);
        Core.kmeans(iris.getAllDataMat(), 3, labels, criteria, 12, Core.KMEANS_PP_CENTERS);
        System.out.println("labels=" + labels.dump());

    }
}
