package com.example.opencv.matrix;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Scalar;

public class Statistics02 {
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
        Mat m4 = new Mat(2, 2, CvType.CV_32FC1, new Scalar(9));
        Core.max(m2, m4, m3);
        System.out.println("矩陣m2與m4各自值 Max=" + m3.dump());

        Mat m5 = new Mat();
        Core.min(m2, m4, m5);
        System.out.println("矩陣m2與m4各自值 Min=" + m5.dump());

        System.out.println("矩陣m2平均=" + Core.mean(m2));
        System.out.println("矩陣m4平均=" + Core.mean(m4));

        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();

        Core.meanStdDev(m2, mean, stddev);
        System.out.println("矩陣m2平均=" + mean.get(0, 0)[0]);
        System.out.println("矩陣m2標準差=" + stddev.get(0, 0)[0]);
        System.out.println("矩陣m2總和=" + Core.sumElems(m2).val[0]);
        System.out.println("矩陣m2非零元素個數=" + Core.countNonZero(m1));

        MinMaxLocResult m6 = new MinMaxLocResult();
        m6 = Core.minMaxLoc(m2);
        System.out.println("矩陣m2內最大值=" + m6.maxVal + ",最小值=" + m6.minVal);

        double norm = Core.norm(m1);
        System.out.println("矩陣m2基本範數=" + norm);

        //均勻分布
        Mat uniformlyDist = new Mat(3, 3, CvType.CV_32FC1);
        Core.randu(uniformlyDist, 100, 150);
        System.out.println("建立3x3的均勻分布的隨機(100~150)矩陣=" + uniformlyDist.dump());

        //常態分布
        Mat normallyDist = new Mat(3, 3, CvType.CV_32FC1);
        Core.randn(normallyDist, 10, 7.5);
        System.out.println("建立3x3的常態分布的(平均值=10,標準差=7.5)矩陣=" + normallyDist.dump());
    }
}
