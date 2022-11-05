package com.jsfxr;

import java.io.*;

public class ByteArray {
    private byte[] data;
    private File file;
    private int pointer = 0;
    private boolean bigEndian = false;
    public ByteArray(File file) {
        this.file = file;
        read();
    }
    public ByteArray(String filename) {
        this(new File(filename));
    }
    public ByteArray(int amount) {
        data = new byte[amount];
    }
    public ByteArray(int amount, File file) {
        this(amount);
        this.file = file;
    }
    public ByteArray(int amount, String filename) {
        this(amount, new File(filename));
    }
    public ByteArray(byte[] data) {
        this.data = data;
    }
    public ByteArray(byte[] data, File file) {
        this(data);
        this.file = file;
    }
    public ByteArray(byte[] data, String filename) {
        this(data, new File(filename));
    }
    public File getDefaultFile() {
        return file;
    }
    public ByteArray setDefaultFile(File file) {
        this.file = file;
        return this;
    }
    public boolean isBigEndian() {
        return bigEndian;
    }
    public ByteArray setBigEndian(boolean bigEndian) {
        this.bigEndian = bigEndian;
        return this;
    }
    public ByteArray readByteOrderMark() {
        if (readUnsignedShort() == 0xFEFF) bigEndian = true;
        if (readUnsignedShort() == 0xFFFE) bigEndian = false;
        return this;
    }
    public ByteArray read() {
        try {
            data = new FileInputStream(file).readAllBytes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    public ByteArray writeBeforePointer() {
        writeBeforePointer(file);
        return this;
    }
    public ByteArray writeBeforePointer(File file) {
        if (file == null) return this;
        try {
            OutputStream out = new FileOutputStream(file);
            out.write(data, 0, pointer);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    public ByteArray writeAll() {
        writeAll(file);
        return this;
    }
    public ByteArray writeAll(File file) {
        if (file == null) return this;
        try {
            OutputStream out = new FileOutputStream(file);
            out.write(data);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    public int pointer() {
        return pointer;
    }
    public ByteArray pointer(int pointer) {
        this.pointer = pointer;
        return this;
    }
    public ByteArray skip(int amount) {
        pointer += amount;
        return this;
    }
    public byte[] array() {
        return data;
    }
    public byte[] arrayBeforePointer() {
        byte[] array = new byte[pointer];
        System.arraycopy(data, 0, array, 0, pointer);
        return array;
    }
    public int length() {
        return data.length;
    }
    public byte readByte() {
        return data[pointer++];
    }
    public int readUnsignedByte() {
        return Byte.toUnsignedInt(readByte());
    }
    public short readShort() {
        return bigEndian ? (short)((readUnsignedByte() << 8) | readUnsignedByte()) : (short)(readUnsignedByte() | (readUnsignedByte() << 8));
    }
    public int readUnsignedShort() {
        return Short.toUnsignedInt(readShort());
    }
    public int readInt() {
        return bigEndian ? (readUnsignedShort() << 16) | readUnsignedShort() : readUnsignedShort() | (readUnsignedShort() << 16);
    }
    public long readLong() {
        return bigEndian ? ((long)readInt() << 32) | readInt() : readInt() | ((long)readInt() << 32);
    }
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }
    public String readString(int length) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i++) {
            string.append((char)readUnsignedByte());
        }
        return string.toString();
    }
    public byte[] readArray(int length) {
        byte[] array = new byte[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readByte();
        }
        return array;
    }
    public boolean readBoolean() {
        return readByte() != 0;
    }
    public ByteArray writeByteOrderMark() {
        writeShort(bigEndian ? 0xFEFF : 0xFFFE);
        return this;
    }
    public ByteArray writeByte(byte value) {
        data[pointer++] = value;
        return this;
    }
    public ByteArray writeByte(int value) {
        writeByte((byte)value);
        return this;
    }
    public ByteArray writeShort(short value) {
        if (bigEndian) {
            writeByte((value >> 8) & 0xFF);
            writeByte(value & 0xFF);
        }
        else {
            writeByte(value & 0xFF);
            writeByte((value >> 8) & 0xFF);
        }
        return this;
    }
    public ByteArray writeShort(int value) {
        writeShort((short)value);
        return this;
    }
    public ByteArray writeInt(int value) {
        if (bigEndian) {
            writeShort((value >> 16) & 0xFFFF);
            writeShort(value & 0xFFFF);
        }
        else {
            writeShort(value & 0xFFFF);
            writeShort((value >> 16) & 0xFFFF);
        }
        return this;
    }
    public ByteArray writeLong(long value) {
        if (bigEndian) {
            writeInt((int)((value >> 32) & 0xFFFFFFFFL));
            writeInt((int)(value & 0xFFFFFFFFL));
        }
        else {
            writeInt((int)(value & 0xFFFFFFFFL));
            writeInt((int)((value >> 32) & 0xFFFFFFFFL));
        }
        return this;
    }
    public ByteArray writeFloat(float value) {
        writeInt(Float.floatToIntBits(value));
        return this;
    }
    public ByteArray writeDouble(double value) {
        writeLong(Double.doubleToLongBits(value));
        return this;
    }
    public ByteArray writeString(String value) {
        for (int i = 0; i < value.length(); i++) {
            writeByte((byte)value.charAt(i));
        }
        return this;
    }
    public ByteArray writeArray(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            writeByte(array[i]);
        }
        return this;
    }
    public ByteArray writeBoolean(boolean value) {
        writeByte(value ? 1 : 0);
        return this;
    }
}
