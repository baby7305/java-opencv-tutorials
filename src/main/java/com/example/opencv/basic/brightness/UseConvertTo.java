package com.example.opencv.basic.brightness;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class UseConvertTo {
    static double alpha = 2;
    static double beta = 50;

    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg",
                    Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat destination = new Mat(source.rows(), source.cols(),
                    source.type());
            source.convertTo(destination, -1, alpha, beta);
            Imgcodecs.imwrite("C:\\opencv-data//lena-brightWithAlpha2Beta50.jpg", destination);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}