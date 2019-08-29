package com.example.opencv.paint.fill;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FloodFill1 {

    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//DSC_0068.jpg");

            Point seedPoint = new Point(30, 30);
            Mat mask = new Mat();
            Rect rect = new Rect();
            Imgproc.floodFill(source, mask, seedPoint, new Scalar(0, 130, 120), rect, new Scalar(10, 10, 10), new Scalar(20, 20, 20), 4);
            Imgcodecs.imwrite("C:\\opencv-data//flood-DSC_0068.jpg", source);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}