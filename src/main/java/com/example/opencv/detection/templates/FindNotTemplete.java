package com.example.opencv.detection.templates;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FindNotTemplete {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frmjavaSwing;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FindNotTemplete window = new FindNotTemplete();
                    window.frmjavaSwing.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public FindNotTemplete() {
        initialize();
        System.out.println("Imgproc.TM_SQDIFF=" + Imgproc.TM_SQDIFF);
        System.out.println("Imgproc.TM_SQDIFF_NORMED=" + Imgproc.TM_SQDIFF_NORMED);
        System.out.println("Imgproc.TM_CCORR=" + Imgproc.TM_CCORR);
        System.out.println("Imgproc.TM_CCORR_NORMED=" + Imgproc.TM_CCORR_NORMED);
        System.out.println("Imgproc.TM_CCOEFF=" + Imgproc.TM_CCOEFF);
        System.out.println("Imgproc.TM_CCOEFF_NORMED =" + Imgproc.TM_CCOEFF_NORMED);

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        final Mat source = Imgcodecs.imread("C:\\opencv-data//DSC_0864.jpg");
        final Mat template = Imgcodecs.imread("C:\\opencv-data//ladybug.jpg");

        BufferedImage image = matToBufferedImage(source);

        frmjavaSwing = new JFrame();
        frmjavaSwing.setTitle("opencv 找出某物練習");
        frmjavaSwing.setBounds(100, 100, 684, 421);
        frmjavaSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmjavaSwing.getContentPane().setLayout(null);

        final JLabel showKsizeValue = new JLabel("0");
        showKsizeValue.setBounds(224, 10, 46, 15);
        frmjavaSwing.getContentPane().add(showKsizeValue);

        final JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(10, 70, 550, 284);
        lblNewLabel.setIcon(new ImageIcon(image));
        frmjavaSwing.getContentPane().add(lblNewLabel);

        final JSlider slider_method = new JSlider();
        slider_method.setMaximum(5);
        slider_method.setValue(0);

        slider_method.setBounds(64, 10, 150, 25);
        frmjavaSwing.getContentPane().add(slider_method);

        JLabel lblAlpha = new JLabel("Method");
        lblAlpha.setBounds(10, 20, 46, 15);
        frmjavaSwing.getContentPane().add(lblAlpha);

        JLabel lblNewLabel_1 = new JLabel("Template");
        lblNewLabel_1.setBounds(257, 10, 86, 15);
        frmjavaSwing.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\opencv-data//ladybug.jpg"));
        lblNewLabel_2.setBounds(326, 8, 295, 52);
        frmjavaSwing.getContentPane().add(lblNewLabel_2);

        slider_method.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                //System.out.println(slider_alpha.getValue());
                showKsizeValue.setText(slider_method.getValue() + "");
                Mat src = Imgcodecs.imread("C:\\opencv-data//DSC_0864.jpg");
                BufferedImage newImage = matToBufferedImage(findTemplete(src, template, slider_method.getValue()));
                lblNewLabel.setIcon(new ImageIcon(newImage));
            }
        });
    }

    public Mat findTemplete(Mat src, Mat template, int match_method) {

        int result_cols = src.cols() - template.cols() + 1;
        int result_rows = src.rows() - template.rows() + 1;
        // System.out.println("result_cols="+result_cols+",result_rows="+result_rows);

        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        Imgproc.matchTemplate(src, template, result, match_method);
        Core.normalize(result, result, 0, 255, Core.NORM_MINMAX, -1, new Mat());
        MinMaxLocResult mmr = Core.minMaxLoc(result);
        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        Imgproc.rectangle(src, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(0, 255, 0));

        Imgproc.rectangle(result, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(0, 255, 0));
        // System.out.println( matchLoc.y + template.rows());

        return src;
    }

    public BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;
        matrix.get(0, 0, data);
        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;
                // bgr to rgb
                byte b;
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = b;
                }
                break;
            default:
                return null;
        }
        BufferedImage image2 = new BufferedImage(cols, rows, type);
        image2.getRaster().setDataElements(0, 0, cols, rows, data);
        return image2;
    }
}
