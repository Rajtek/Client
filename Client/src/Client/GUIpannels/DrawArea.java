/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.GUIpannels;

/**
 *
 * @author Rajtek
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class DrawArea extends JComponent {

    private DrawAreaListener listener;
    // Image in which we're going to draw
    private BufferedImage image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    // Mouse coordinates
    private int currentX, currentY, oldX, oldY, stroke = 10;
    private boolean drawingEnabled = false;

    public void setListener(DrawAreaListener listener) {
        this.listener = listener;
    }

    public DrawArea() {
        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (drawingEnabled) {
                    // save coord x,y when mouse is pressed
                    oldX = e.getX();
                    oldY = e.getY();

                    if (g2 != null) {
                        g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(oldX, oldY, oldX, oldY);
                        repaint();
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawingEnabled) {
                    listener.drawingChanged(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()));
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawingEnabled) {
                    // coord x,y when drag mouse
                    currentX = e.getX();
                    currentY = e.getY();

                    if (g2 != null) {
                        // draw line if g2 context not null
                        g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                        g2.drawLine(oldX, oldY, currentX, currentY);
                        // refresh draw area to repaint
                        repaint();
                        // store current coords x,y as olds x,y
                        oldX = currentX;
                        oldY = currentY;
                    }
                }
            }
        });
    }
    public void setDrawingEnabled(boolean b){
        drawingEnabled=b;
    }
    public void updateImage(int[] data) {
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth());

        repaint();
    }

    public void increaseStroke() {
        if (stroke < 20) {
            stroke++;
        }
    }

    public void decreaseStroke() {
        if (stroke > 1) {
            stroke--;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
//            image = (BufferedImage) createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            // enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
            black();
        }

        g.drawImage(image, 0, 0, null);
    }

    // now we create exposed methods
    public void clearButton() {
        clear();
        listener.drawingChanged(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()));
    }

    private void clear() {
        Paint paint = g2.getPaint();
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(paint);
        repaint();
    }

    public void red() {
        g2.setPaint(Color.red);

    }

    public void black() {
        g2.setPaint(Color.black);
    }

    public void yellow() {
        g2.setPaint(Color.yellow);
    }

    public void green() {
        g2.setPaint(Color.green);
    }

    public void blue() {
        g2.setPaint(Color.blue);
    }

    public void white() {
        g2.setPaint(Color.white);
    }
}
