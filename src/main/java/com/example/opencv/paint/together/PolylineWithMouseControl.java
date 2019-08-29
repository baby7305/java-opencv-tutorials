package com.example.opencv.paint.together;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PolylineWithMouseControl {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frmjavaSwing;

    public List<Point> allPoint = new ArrayList<Point>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PolylineWithMouseControl window = new PolylineWithMouseControl();
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
    public PolylineWithMouseControl() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        final Mat source = Imgcodecs.imread("C:\\opencv-data//DSC_0625.jpg");

        BufferedImage image = matToBufferedImage(source);

        frmjavaSwing = new JFrame();
        frmjavaSwing.setTitle("多邊形繪製使用滑鼠練習");
        frmjavaSwing.setBounds(100, 100, 491, 425);
        frmjavaSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmjavaSwing.getContentPane().setLayout(null);

        final JLabel lblLocation = new JLabel("");
        lblLocation.setBounds(10, 10, 112, 25);
        frmjavaSwing.getContentPane().add(lblLocation);

        final JLabel lblLocation2 = new JLabel("");
        lblLocation2.setBounds(121, 10, 112, 25);
        frmjavaSwing.getContentPane().add(lblLocation2);

        JPanel panel = new JPanel();
        panel.setBounds(10, 45, 429, 348);
        frmjavaSwing.getContentPane().add(panel);

        final JLabel lblNewLabel = new JLabel("");
        panel.add(lblNewLabel);


        lblNewLabel.setIcon(new ImageIcon(image));

        JButton btnNewButton = new JButton("封閉化");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BufferedImage newImage = matToBufferedImage(paintAndSetPolyClose());
                lblNewLabel.setIcon(new ImageIcon(newImage));
            }
        });
        btnNewButton.setBounds(243, 10, 89, 25);
        frmjavaSwing.getContentPane().add(btnNewButton);


        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                lblLocation2.setText("");
                lblLocation.setText("加入點:X:" + arg0.getX() + ",Y:" + arg0.getY());

                BufferedImage newImage = matToBufferedImage(paintPointAndPolylines(arg0.getX(), arg0.getY()));
                lblNewLabel.setIcon(new ImageIcon(newImage));
            }
        });
    }

    public Mat paintPointAndPolylines(int x, int y) {
        Mat src = Imgcodecs.imread("C:\\opencv-data//DSC_0625.jpg");

        List<Point> allPoint = getAllPoint();
        allPoint.add(new Point(x, y));
        setAllPoint(allPoint);
        //畫滑鼠點擊的點
        Imgproc.circle(src, new Point(x, y), 2, new Scalar(0, 55, 255), 2);

        //畫出Polyline
        if (allPoint.size() >= 2) {
            MatOfPoint mop1 = new MatOfPoint();
            List<MatOfPoint> allMatOfPoint = new ArrayList<MatOfPoint>();
            mop1.fromList(allPoint);
            allMatOfPoint.add(mop1);
            Imgproc.polylines(src, allMatOfPoint, false, new Scalar(255, 255, 255), 2);

        }

        return src;

    }

    public Mat paintAndSetPolyClose() {
        Mat src = Imgcodecs.imread("C:\\opencv-data//DSC_0625.jpg");
        List<Point> allPoint = getAllPoint();
        //畫出Polyline
        if (allPoint.size() >= 2) {
            MatOfPoint mop1 = new MatOfPoint();
            List<MatOfPoint> allMatOfPoint = new ArrayList<MatOfPoint>();
            mop1.fromList(allPoint);
            allMatOfPoint.add(mop1);
            Imgproc.polylines(src, allMatOfPoint, true, new Scalar(255, 255, 255), 2);

        }

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


    public List<Point> getAllPoint() {
        return allPoint;
    }

    public void setAllPoint(List<Point> allPoint) {
        this.allPoint = allPoint;
    }

}
