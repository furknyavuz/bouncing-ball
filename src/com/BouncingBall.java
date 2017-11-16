package com;

import static java.lang.System.err;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BouncingBall extends JFrame {
    // Define named-constants
    private static final int CANVAS_WIDTH = 1024;
    private static final int CANVAS_HEIGHT = 768;
    private static final int UPDATE_INTERVAL = 30; // milliseconds
    private Color ballColor = Color.RED;

    private int x = 100;
    private int y = 100;
    private int size = 250;
    private int xSpeed = 3;
    private int ySpeed = 5;

    private BouncingBall() {
        DrawCanvas canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("Bouncing Ball");
        this.setVisible(true);

        Thread updateThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    update();
                    repaint();
                    try {
                        Thread.sleep(UPDATE_INTERVAL);
                    } catch (InterruptedException ignore) {
                        err.println(ignore.getMessage());
                    }
                }
            }
        };
        updateThread.start();
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        Random r = new Random();
        if (x > CANVAS_WIDTH - size || x < 0) {
            xSpeed = -xSpeed;
            ballColor = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
        if (y > CANVAS_HEIGHT - size || y < 0) {
            ySpeed = -ySpeed;
            ballColor = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
    }

    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);
            g.setColor(ballColor);
            g.fillOval(x, y, size, size);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BouncingBall();
            }
        });
    }
}