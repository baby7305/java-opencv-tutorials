package com.example.opencv.machineLearning.HaarAdaboost;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;

public class FaceDetectorByWebCam {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String arg[]) {
        VideoCapture capture = new VideoCapture();
        capture.open(0);
        JFrame frame1 = new JFrame("show image");
        frame1.setTitle("人臉檢測");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(640, 480);
        frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());
        Panel panel1 = new Panel();
        frame1.setContentPane(panel1);
        frame1.setVisible(true);


        if (!capture.isOpened()) {
            System.out.println("Error");
        } else {
            Mat webcam_image = new Mat();
            capture.read(webcam_image);
            frame1.setSize(webcam_image.width() + 10, webcam_image.height() + 20);

            CascadeClassifier faceDetector = new CascadeClassifier("C:\\opencv-data//haarcascades//haarcascade_frontalface_alt.xml");
            MatOfRect faceDetections = new MatOfRect();


            while (true) {
                capture.read(webcam_image);
                if (!webcam_image.empty()) {
                    faceDetector.detectMultiScale(webcam_image, faceDetections);
                    for (Rect rect : faceDetections.toArray()) {
                        Imgproc.rectangle(webcam_image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                new Scalar(0, 255, 0), 3);
                    }

                    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

                    panel1.setimagewithMat(webcam_image);
                    frame1.repaint();


                } else {
                    capture.release();
                    break;
                }
            }
        }
    }


}
