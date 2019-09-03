package com.example.opencv.detection.contours;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

//最內橢圓
public class FitEllipseContainUnknowPoints {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Mat.zeros(250, 250, CvType.CV_8UC3);

        MatOfPoint2f contours = new MatOfPoint2f();
        List<Point> points = new ArrayList<Point>();
        //contours
        int X, Y;
        for (int i = 0; i < 20; i++) {
            X = (int) (Math.random() * 200 + 40);
            Y = (int) (Math.random() * 200 + 40);
            Imgproc.circle(img, new Point(X, Y), 2, new Scalar(0, 0, 255));    //red
            points.add(new Point(X, Y));

        }
        contours.fromList(points);
        RotatedRect r = Imgproc.fitEllipse(contours);
        Imgproc.ellipse(img, r, new Scalar(0, 255, 0), 3, 8);

        Imgcodecs.imwrite("C:\\opencv-data//rectContainPoint-fitEllipse.jpg", img);

    }

}
