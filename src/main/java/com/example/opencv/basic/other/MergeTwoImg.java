package com.example.opencv.basic.other;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

//2張影像合併
public class MergeTwoImg {
    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//ncku.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat source1 = Imgcodecs.imread("C:\\opencv-data//jelly_studio_logo.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat destination = source.clone();

            //Imgproc.resize
            Rect roi = new Rect(50, 50, 90, 62);
            Mat destinationROI = source.submat(roi);
            source1.copyTo(destinationROI, source1);

            Imgcodecs.imwrite("C:\\opencv-data//merge2.jpg", source);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
