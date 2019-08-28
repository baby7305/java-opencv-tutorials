package com.example.opencv.basic.other;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Mosaic {
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat im = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Imgproc.resize(im, im, new Size(), 0.1, 0.1, Imgproc.INTER_NEAREST);
        Imgproc.resize(im, im, new Size(), 10.0, 10.0, Imgproc.INTER_NEAREST);
        Imgcodecs.imwrite("C:\\opencv-data//Mosaic-lena.jpg", im);
    }
}
