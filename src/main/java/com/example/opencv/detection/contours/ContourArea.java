package com.example.opencv.detection.contours;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

//找輪廓且畫出
public class ContourArea {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//只能灰階
        Mat source = Imgcodecs.imread("C:\\opencv-data//shape-sample1.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.GaussianBlur(source, source, new Size(3, 3), 10, 0);
        Imgproc.Canny(source, destination, 5, 100);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Mat hierarchy = new Mat(source.rows(), source.cols(), CvType.CV_8UC1, new Scalar(0));
        Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat drawing = Mat.zeros(destination.size(), CvType.CV_8UC3);
        System.out.println("發現總共有" + contours.size() + "個輪廓!");


        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(drawing, contours, i, new Scalar(255, 0, 0, 255), 3);
            System.out.println("輪廓" + i + "的面積=" + Imgproc.contourArea(contours.get(i)) + ",周長=" + Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true));
        }

        Imgcodecs.imwrite("C:\\opencv-data//shape-sample-ok1.jpg", drawing);
        System.out.println("Done!");
    }
}
