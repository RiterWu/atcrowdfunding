package com.riter.atcrowdfunding.utils;

import java.io.File;

/**
 * 主要用来产生算法。
 */
public class AlgorithmUtil {

    /**
     * 使用hash算法产生图片上传的随机目录。
     *
     * @param realPath 真实路径
     * @param fileName 文件名
     * @return 绝对路径
     */
    public static String makePath(String realPath, String fileName) {
        // 1.根据文件名产生32位int类型的hashCode
        int hashCode = fileName.hashCode();
        // 2.获取int类型的低4位。  值范围:0~15
        int dir1 = hashCode & 0xf;
        // 3.获取int类型的5~8位。 值范围:0~15
        int dir2 = (hashCode & 0xf0) >> 4;
        // 4.和真正的路径做拼接
        String path = realPath + "\\" + dir1 + "\\" + dir2 + "\\" + fileName;
        // 5.判断路径上的目录是否都存在，不存在则创建。
        File pathDir = new File(path);
        if (!pathDir.exists()) {
            pathDir.mkdirs();
        }
        return path;
    }
}
