package com.example.opencv.camera.denoising;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.photo.Photo;

import java.util.ArrayList;
import java.util.List;

public class FastNlMeansDenoisingMulti {
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat im = Imgcodecs.imread("C:\\opencv-data//lena.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat im1 = Imgcodecs.imread("C:\\opencv-data//lena1.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat im2 = Imgcodecs.imread("C:\\opencv-data//lena2.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        List<Mat> srcList = new ArrayList<Mat>();
        Mat dst = new Mat();
        srcList.add(im1);
        srcList.add(im2);
        srcList.add(im);
        Photo.fastNlMeansDenoisingMulti(srcList, dst, 2, 1);
        Imgcodecs.imwrite("C:\\opencv-data//fastNlMeansDenoisingMulti_lena.jpg", dst);
    }
}
