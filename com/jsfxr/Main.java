package com.jsfxr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static int mouse_x;
    public static int mouse_y;
    public static int mouse_px;
    public static int mouse_py;
    public static int next_mouse_x;
    public static int next_mouse_y;
    public static boolean mouse_left;
    public static boolean mouse_right;
    public static boolean mouse_middle;
    public static boolean mouse_leftclick;
    public static boolean mouse_rightclick;
    public static boolean mouse_middleclick;
    public static boolean next_mouse_left;
    public static boolean next_mouse_right;
    public static boolean next_mouse_middle;
    public static boolean[] keys = new boolean[256];
    public static JFrame window = new JFrame("jsfxr - Java sfxr port");
    public static void update() {
        mouse_px = mouse_x;
        mouse_py = mouse_y;
        mouse_x = next_mouse_x;
        mouse_y = next_mouse_y;
        boolean left = mouse_left;
        boolean right = mouse_right;
        boolean middle = mouse_middle;
        mouse_left = next_mouse_left;
        mouse_right = next_mouse_right;
        mouse_middle = next_mouse_middle;
        mouse_leftclick = mouse_left && !left;
        mouse_rightclick = mouse_right && !right;
        mouse_middleclick = mouse_middle && !middle;
    }
    public static void main(String[] args) throws Exception {
        window.setLocation(100, 100);
        window.setDefaultCloseOperation(3);
        window.getContentPane().setPreferredSize(new Dimension(640, 480));
        window.pack();
        window.setResizable(false);
        window.setIconImage(new ImageIcon(Main.class.getResource("/data/sfxr.png")).getImage());
        window.add(new JPanel() {
            public void paint(Graphics g) {
                g.drawImage(Tools.image, 0, 0, this);
            }
        });
        window.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });
        window.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) next_mouse_left = true;
                if (e.getButton() == MouseEvent.BUTTON3) next_mouse_right = true;
                if (e.getButton() == MouseEvent.BUTTON2) next_mouse_middle = true;
            }
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) next_mouse_left = false;
                if (e.getButton() == MouseEvent.BUTTON3) next_mouse_right = false;
                if (e.getButton() == MouseEvent.BUTTON2) next_mouse_middle = false;
            }
        });
        window.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
            }
            public void mouseMoved(MouseEvent e) {
                next_mouse_x = e.getXOnScreen() - window.getContentPane().getLocationOnScreen().x;
                next_mouse_y = e.getYOnScreen() - window.getContentPane().getLocationOnScreen().y;
            }
        });
        window.setVisible(true);
        Application.font = Tools.loadTGA("/data/font.tga");
        Application.ld48 = Tools.loadTGA("/data/ld48.tga");
        Application.ld48.width = Application.ld48.pitch;
        Application.resetParams();
        Application.categories[0].name = "PICKUP/COIN";
        Application.categories[1].name = "LASER/SHOOT";
        Application.categories[2].name = "EXPLOSION";
        Application.categories[3].name = "POWERUP";
        Application.categories[4].name = "HIT/HURT";
        Application.categories[5].name = "JUMP";
        Application.categories[6].name = "BLIP/SELECT";
        while (true) {
            update();
            Application.drawScreen();
            window.repaint();
            Thread.sleep(10);
        }
    }
}
