package com.example.opencv.motion.opticFlow;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;


public class OpticalFlowFarneback {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String arg[]) throws Exception {

        JFrame frame2 = new JFrame("OpticalFlowFarneback");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(640, 480);
        frame2.setBounds(300, 100, frame2.getWidth() + 50, 50 + frame2.getHeight());
        Panel panel2 = new Panel();
        frame2.setContentPane(panel2);
        frame2.setVisible(true);
        //-- 2. Read the video stream
        VideoCapture capture = new VideoCapture();
        capture.open(0); //0表第1支CCD,1是第2支
        Mat webcam_image = new Mat();

        Mat matOpFlowThis = new Mat();
        Mat matOpFlowPrev = new Mat();

        Mat mRgba;
        System.out.println("OPTFLOW_USE_INITIAL_FLOW=" + Video.OPTFLOW_USE_INITIAL_FLOW);
        System.out.println("OPTFLOW_FARNEBACK_GAUSSIAN=" + Video.OPTFLOW_FARNEBACK_GAUSSIAN);

        capture.read(webcam_image);
        frame2.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

        if (capture.isOpened()) {
            while (true) {
                Thread.sleep(200);


                capture.read(webcam_image);
                if (!webcam_image.empty()) {
                    mRgba = webcam_image.clone();


                    if (matOpFlowPrev.rows() == 0) {

                        // first time through the loop so we need prev and this mats
                        // plus prev points
                        // get this mat
                        Imgproc.cvtColor(mRgba, matOpFlowThis, Imgproc.COLOR_RGBA2GRAY);

                        // copy that to prev mat
                        matOpFlowThis.copyTo(matOpFlowPrev);

                    } else {
                        // we've been through before so
                        // this mat is valid. Copy it to prev mat
                        matOpFlowThis.copyTo(matOpFlowPrev);

                        // get this mat
                        Imgproc.cvtColor(mRgba, matOpFlowThis, Imgproc.COLOR_RGBA2GRAY);

                    }


//	               	Parameters:

                    Mat flow = new Mat(mRgba.size(), CvType.CV_8UC1);

                    //Video.calcOpticalFlowFarneback(matOpFlowPrev, matOpFlowThis, flow, 0.5,1,1,1,7,1.5,1);
                    Video.calcOpticalFlowFarneback(matOpFlowPrev, matOpFlowThis, flow, 0.5, 3, 15, 3, 5, 1.2, 0);


                    //System.out.println(flow.dump());

                    float[] fdata = new float[2];
                    for (int i = 0; i < matOpFlowPrev.cols(); i = i + 10) {
                        for (int j = 0; j < matOpFlowPrev.rows(); j = j + 10) {

                            flow.get(j, i, fdata);
                            //0.Core.circle(mRgba, new Point(j,i), 1, new Scalar(0, 0, 255), 1);
                            Imgproc.circle(mRgba, new Point(i + (int) fdata[0], j + (int) fdata[1]), 1, new Scalar(0, 0, 255), 1);
                            //0.Core.line(mRgba, new Point(j,i), new Point(j+(int)fdata[0],i+(int)fdata[1]), new Scalar(0, 255, 255) , 1);
                            Imgproc.line(mRgba, new Point(i, j), new Point(i + (int) fdata[0], j + (int) fdata[1]), new Scalar(0, 255, 255), 1);

                        }
                    }


                    panel2.setimagewithMat(mRgba);  //
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
