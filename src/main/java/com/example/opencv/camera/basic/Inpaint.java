package com.example.opencv.camera.basic;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.photo.Photo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Inpaint {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frmjavaSwing;
    double alpha = 2;
    double beta = 50;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Inpaint window = new Inpaint();
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
    public Inpaint() {
        initialize();
        System.out.println("Photo.INPAINT_NS=" + Photo.INPAINT_NS);
        System.out.println("Photo.INPAINT_TELEA=" + Photo.INPAINT_TELEA);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        final Mat source = Imgcodecs.imread("C:\\opencv-data//lena_dirty.jpg");

        BufferedImage image = matToBufferedImage(source);

        frmjavaSwing = new JFrame();
        frmjavaSwing.setTitle("影像修復");
        frmjavaSwing.setBounds(100, 100, 520, 550);
        frmjavaSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmjavaSwing.getContentPane().setLayout(null);

        final JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(10, 68, 438, 438);
        lblNewLabel.setIcon(new ImageIcon(image));
        frmjavaSwing.getContentPane().add(lblNewLabel);

        JButton btnNewButton = new JButton("影像修復");

        btnNewButton.setBounds(326, 10, 87, 23);
        frmjavaSwing.getContentPane().add(btnNewButton);

        JLabel lblNewLabel_1 = new JLabel("flag");
        lblNewLabel_1.setBounds(10, 14, 46, 15);
        frmjavaSwing.getContentPane().add(lblNewLabel_1);

        final JSlider slider = new JSlider();

        slider.setValue(0);
        slider.setMaximum(1);
        slider.setBounds(37, 8, 63, 25);
        frmjavaSwing.getContentPane().add(slider);

        final JLabel label = new JLabel("0");
        label.setBounds(114, 14, 46, 15);
        frmjavaSwing.getContentPane().add(label);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                label.setText(slider.getValue() + "");

            }
        });
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                BufferedImage newImage = matToBufferedImage(photoInpaint(source, slider.getValue()));
                lblNewLabel.setIcon(new ImageIcon(newImage));
            }
        });

    }

    public Mat photoInpaint(Mat source, int flag) {
        Mat destination = new Mat(source.rows(), source.cols(), CvType.CV_8UC3);
        Mat maskMat = Imgcodecs.imread("C:\\opencv-data//lena_dirtymask.jpg", 0);
        Photo.inpaint(source, maskMat, destination, 1, Photo.INPAINT_NS);
        return destination;
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
