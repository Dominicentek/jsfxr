package com.jsfxr;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tools {
    public static BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    public static Graphics g = image.getGraphics();
    public static class Spriteset {
        public int[] data;
        public int width;
        public int height;
        public int pitch;
    }
    public static Spriteset loadTGA(String filename) {
        try {
            ByteArray file = new ByteArray(Tools.class.getResourceAsStream(filename).readAllBytes());
            int id_length = 0;
            Spriteset spriteset = new Spriteset();
            id_length = file.readUnsignedByte();
            file.skip(11);
            int width = file.readUnsignedShort();
            int height = file.readUnsignedShort();
            int channels = file.readUnsignedByte() / 8;
            file.skip(id_length + 1);
            spriteset.data = new int[width * height];
            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < width; x++) {
                    int pixel = file.readUnsignedByte() | (file.readUnsignedByte() << 8) | (file.readUnsignedByte() << 16);
                    spriteset.data[y * width + x] = pixel;
                }
            }
            spriteset.height = height;
            spriteset.width = height;
            spriteset.pitch = width;
            return spriteset;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void clearScreen(int color) {
        color |= 0xFF000000;
        g.setColor(new Color(color));
        g.fillRect(0, 0, 640, 480);
    }
    public static void drawBar(int sx, int sy, int w, int h, int color) {
        color |= 0xFF000000;
        g.setColor(new Color(color));
        g.fillRect(sx, sy, w, h);
    }
    public static void drawBox(int sx, int sy, int w, int h, int color) {
        color |= 0xFF000000;
        g.setColor(new Color(color));
        g.drawRect(sx, sy, w, h);
    }
    public static void drawSprite(Spriteset sprites, int sx, int sy, int i, int color) {
        for (int y = 0; y < sprites.height; y++) {
            int spoffset = y * sprites.pitch + i * sprites.width;
            for (int x = 0; x < sprites.width; x++) {
                int p = sprites.data[spoffset++];
                if (p != 0x300030) {
                    image.setRGB(sx + x, sy + y, color | 0xFF000000);
                }
            }
        }
    }
    public static void drawText(Spriteset font, int sx, int sy, int color, String string) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            drawSprite(font, sx + i * 8, sy, string.charAt(i) - ' ', color);
        }
    }
    public static boolean mouseInBox(int x, int y, int width, int height) {
        return Main.mouse_x >= x && Main.mouse_x < x + width && Main.mouse_y >= y && Main.mouse_y < y + height;
    }
}
