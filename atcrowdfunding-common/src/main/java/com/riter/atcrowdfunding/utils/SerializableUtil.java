package com.riter.atcrowdfunding.utils;

import java.io.*;

public class SerializableUtil {

    /**
     * java对象序列化成字节数组
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object){
        ByteArrayOutputStream outByte = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = null;

        try {
            objectOut = new ObjectOutputStream(outByte);
            objectOut.writeObject(object);
            return outByte.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            try {
                objectOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字节数组反序列化成java对象
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes){
        ByteArrayInputStream inByte = new ByteArrayInputStream(bytes);
        ObjectInputStream objectIn = null;

        try {
            objectIn = new ObjectInputStream(inByte);
            return objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            try {
                objectIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
