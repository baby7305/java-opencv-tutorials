package com.example.opencv.matrix;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class LinearAlgebra03 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        Mat m1 = new Mat(2, 2, CvType.CV_32FC1);
        m1.put(0, 0, 1);
        m1.put(0, 1, 2);
        m1.put(1, 0, 3);
        m1.put(1, 1, 4);

        Mat m2 = m1.clone();

        System.out.println("矩陣m2是複製m1,所有元素=" + m2.dump());

        Mat m3 = new Mat();
        System.out.println("矩陣m2的trace(跡)=" + Core.trace(m2).val[0]);

        //求eigenvalues,eigenvectors
        Mat eigenvalues = new Mat();
        Mat eigenvectors = new Mat();
        //boolean computeEigenvectors = true;
        Core.eigen(m1, eigenvalues, eigenvectors);
        System.out.println("m1特徵值=" + eigenvalues.dump());
        System.out.println("m1特徵向量=" + eigenvectors.dump());

        //協方差/共變數矩陣(covariance matrix)
        Mat covar = new Mat(2, 2, CvType.CV_32FC1);
        Mat mean = new Mat(1, 2, CvType.CV_32F);
        Core.calcCovarMatrix(m2, covar, mean, Core.COVAR_ROWS | Core.COVAR_NORMAL, CvType.CV_32F);
        System.out.println("m2的協方差/共變數矩陣=" + covar.dump() + "by col平均" + mean.dump());

        //兩兩比較
        Mat compare = new Mat();
        Mat m4 = new Mat(2, 2, CvType.CV_32FC1, new Scalar(9));
        Core.compare(m1, m4, compare, Core.CMP_GT);//是否大於
        System.out.println("m1是否大於m4各元素:" + compare.dump());

        compare = new Mat();
        Core.compare(m1, m4, compare, Core.CMP_LT);//是否小於
        System.out.println("m1是否小於m4各元素:" + compare.dump());

        compare = new Mat();
        Core.compare(m1, m4, compare, Core.CMP_EQ);//是否等於
        System.out.println("m1是否等於m4各元素:" + compare.dump());

        compare = new Mat();
        Core.compare(m1, m4, compare, Core.CMP_GT);//是否大於等於
        System.out.println("m1是否大於等於m4各元素:" + compare.dump());

        compare = new Mat();
        Core.compare(m1, m4, compare, Core.CMP_LT);//是否小於等於
        System.out.println("m1是否小於等於m4各元素:" + compare.dump());
    }
}
