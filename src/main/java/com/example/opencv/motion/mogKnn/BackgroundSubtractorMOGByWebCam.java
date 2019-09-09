package com.example.opencv.motion.mogKnn;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;


public class BackgroundSubtractorMOGByWebCam {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String arg[]) throws Exception {
        JFrame frame1 = new JFrame("Camera");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(640, 480);
        frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());
        Panel panel1 = new Panel();
        frame1.setContentPane(panel1);
        frame1.setVisible(true);

        JFrame frame2 = new JFrame("Threshold");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(640, 480);
        frame2.setBounds(300, 100, frame1.getWidth() + 50, 50 + frame1.getHeight());
        Panel panel2 = new Panel();
        frame2.setContentPane(panel2);
        frame2.setVisible(true);
        //-- 2. Read the video stream
        VideoCapture capture = new VideoCapture();//0表第1支CCD,1是第2支
        capture.open(0);
        Mat webcam_image = new Mat();

        BackgroundSubtractorMOG2 bg = new Video().createBackgroundSubtractorMOG2();

        Mat frame = new Mat();
        Mat back = new Mat();
        Mat fore = new Mat();


        capture.read(webcam_image);
        frame1.setSize(webcam_image.width() + 40, webcam_image.height() + 60);
        frame2.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

        if (capture.isOpened()) {
            Mat rgb = new Mat();
            while (true) {


                capture.read(webcam_image);
                if (!webcam_image.empty()) {
                    Mat fgmask = new Mat();
                    bg.apply(webcam_image, fgmask, 0.01);


                    panel1.setimagewithMat(webcam_image);
                    panel2.setimagewithMat(fgmask);  //
                    frame1.repaint();
                    frame2.repaint();
                } else {
                    System.out.println("無補抓任何畫面!");
                    break;
                }
            }
        }
        return;
    }


}
