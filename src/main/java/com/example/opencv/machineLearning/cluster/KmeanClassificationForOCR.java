package com.example.opencv.machineLearning.cluster;

import com.example.opencv.machineLearning.data.OcrDatabase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;

public class KmeanClassificationForOCR {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        OcrDatabase ocr = new OcrDatabase();

        Mat labels = new Mat();
        TermCriteria criteria = new TermCriteria(TermCriteria.COUNT, 10, 1);
        Core.kmeans(ocr.getTrainingDataMat(), 10, labels, criteria, 3, Core.KMEANS_PP_CENTERS);
        // 100-13/100,用Core.KMEANS_RANDOM_CENTERS也一樣
        System.out.println("labels=" + labels.dump());


        // 沒意義,因為只有10個樣本,分10類
		 /*

		 labels=new Mat();
		 Core.kmeans(ocr.getTestingDataMat(), 10, labels, criteria, 3, Core.KMEANS_PP_CENTERS);
		 System.out.println("labels="+labels.dump());
		*/
    }
}
