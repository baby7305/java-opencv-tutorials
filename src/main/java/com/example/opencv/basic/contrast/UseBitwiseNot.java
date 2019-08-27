package com.example.opencv.basic.contrast;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class UseBitwiseNot {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Core.bitwise_not(img, img);
        Imgcodecs.imwrite("C:\\opencv-data//lena-contrast3.jpg", img);
    }
}
