package com.example.opencv.paint.fill;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FloodFill {

    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//test-floodFill.jpg");
            System.out.println("Imgproc.FLOODFILL_FIXED_RANGE=" + Imgproc.FLOODFILL_FIXED_RANGE);
            System.out.println("Imgproc.FLOODFILL_MASK_ONLY=" + Imgproc.FLOODFILL_MASK_ONLY);
            Point seedPoint = new Point(85, 89);
            Mat mask = new Mat();
            Rect rect = new Rect();
            Imgproc.floodFill(source, mask, seedPoint, new Scalar(0, 130, 120), rect, new Scalar(20, 20, 20), new Scalar(20, 20, 20), 4);
            Imgcodecs.imwrite("C:\\opencv-data//do-test-flood.jpg", source);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
