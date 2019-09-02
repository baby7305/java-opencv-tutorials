package com.example.opencv.camera.denoising;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.photo.Photo;

public class FastNlMeansDenoisingColored {

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat im = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Mat dst = new Mat();
        Photo.fastNlMeansDenoisingColored(im, dst);
        Imgcodecs.imwrite("C:\\opencv-data//fastNlMeansDenoisingColored_lena.jpg", dst);
    }
}
