package com.example.opencv.advance.watershed;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Watershed {
    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg",
                    Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat gray = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
            Imgproc.cvtColor(source, gray, Imgproc.COLOR_BGR2GRAY);
            Mat binary = Mat.zeros(gray.rows(), gray.cols(), CvType.CV_8UC1);
            Imgproc.threshold(gray, binary, 100, 255, Imgproc.THRESH_BINARY);


            Mat fg = new Mat(source.size(), CvType.CV_8U);
            Imgproc.erode(binary, fg, new Mat(), new Point(-1, -1), 2);

            Mat bg = new Mat(source.size(), CvType.CV_8U);
            Imgproc.dilate(binary, bg, new Mat(), new Point(-1, -1), 3);
            Imgproc.threshold(bg, bg, 1, 128, Imgproc.THRESH_BINARY_INV);

            Mat markers = new Mat(binary.size(), CvType.CV_8U, new Scalar(0));
            Core.add(fg, bg, markers);


            Mat tmp = new Mat(binary.size(), CvType.CV_8U, new Scalar(0));
            Mat tmper = new Mat(binary.size(), CvType.CV_8U, new Scalar(0));
            Mat tmper1 = new Mat(binary.size(), CvType.CV_8U, new Scalar(0));


            //Watershed
            markers.convertTo(tmper, CvType.CV_32S);
            Imgproc.watershed(source, tmper);
            tmper.convertTo(tmp, CvType.CV_8U);
            //markers.convertTo(tmp, CvType.CV_8U);
            tmper.convertTo(tmper1, CvType.CV_8U, 255, 255);
            Imgcodecs.imwrite("C:\\opencv-data//lena-watered.jpg", markers);
            Imgcodecs.imwrite("C:\\opencv-data//lena-watered1.jpg", tmp);
            Imgcodecs.imwrite("C:\\opencv-data//lena-watered2.jpg", tmper);
            Imgcodecs.imwrite("C:\\opencv-data//lena-watered3.jpg", tmper1);

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

}
