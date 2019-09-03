package com.example.opencv.detection.contours;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

//boundingRect
public class RectContainUnknowPoints {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Mat.zeros(250, 250, CvType.CV_8UC3);

        MatOfPoint contours = new MatOfPoint();
        List<Point> points = new ArrayList<Point>();
        //contours
        int X, Y;
        for (int i = 0; i < 20; i++) {
            X = (int) (Math.random() * 200 + 10);
            Y = (int) (Math.random() * 200 + 10);
            Imgproc.circle(img, new Point(X, Y), 2, new Scalar(0, 255, 0));    //red
            points.add(new Point(X, Y));

        }
        contours.fromList(points);
        Rect r = Imgproc.boundingRect(contours);
        //Core.rectangle(img, new Point(minX,minY), new Point(maxX,maxY), new Scalar(0, 0, 255));
        Imgproc.rectangle(img, r.tl(), r.br(), new Scalar(0, 0, 255));

        Imgcodecs.imwrite("C:\\opencv-data//rectContainPoint-boundingRect.jpg", img);

    }

}
