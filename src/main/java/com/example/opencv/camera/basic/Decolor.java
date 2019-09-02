package com.example.opencv.camera.basic;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.photo.Photo;

public class Decolor {
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat im = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Mat color_boost = new Mat();
        Mat grayscale = new Mat();
        Photo.decolor(im, grayscale, color_boost);
        Imgcodecs.imwrite("C:\\opencv-data//decolor_boost.jpg", color_boost);
        Imgcodecs.imwrite("C:\\opencv-data//decolor_grayscale.jpg", grayscale);
    }
}
