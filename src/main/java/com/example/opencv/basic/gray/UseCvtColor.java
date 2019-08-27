package com.example.opencv.basic.gray;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class UseCvtColor {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try {

            Mat src = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
            Mat dst = new Mat(src.rows(), src.cols(), src.type());
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
            Imgcodecs.imwrite("C:\\opencv-data//lena-gray.jpg", dst);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        System.exit(0);
    }
}