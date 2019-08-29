package com.example.opencv.paint.together;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RectContainPoint {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Mat.zeros(250, 250, CvType.CV_8UC3);

        List<Integer> pointX = new ArrayList<Integer>();
        List<Integer> pointY = new ArrayList<Integer>();
        int X, Y, maxX, maxY, minX, minY;
        for (int i = 0; i < 20; i++) {
            X = (int) (Math.random() * 200 + 10);
            Y = (int) (Math.random() * 200 + 10);
            Imgproc.circle(img, new Point(X, Y), 2, new Scalar(0, 255, 0));
            pointX.add(X);
            pointY.add(Y);

        }
        maxX = Collections.max(pointX);
        maxY = Collections.max(pointY);
        minX = Collections.min(pointX);
        minY = Collections.min(pointY);

        Imgproc.rectangle(img, new Point(minX, minY), new Point(maxX, maxY), new Scalar(0, 0, 255));

        Imgcodecs.imwrite("C:\\opencv-data//rectContainPoint.jpg", img);

    }

}
