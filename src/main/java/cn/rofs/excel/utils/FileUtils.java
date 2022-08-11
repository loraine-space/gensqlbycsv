package cn.rofs.excel.utils;

import java.io.File;

/**
 * @author rainofsilence
 * @date 2022/8/6 周六
 */
public class FileUtils {

    public static void mkdirs(String path) {
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        File f;
        try {
            f = new File(path);
            if (!f.exists()) {
                boolean b = f.mkdirs();
                if (b) {
                    System.out.println("目录: {"+path + "} 创建成功");
                } else {
                    System.out.println("目录: {"+path + "} 创建成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mkFile(String path) {
        File f;
        try {
            f = new File(path);
            if (!f.exists()) {
                boolean b = f.createNewFile();
                if (b) {
                    System.out.println("文件: {"+path + "} 创建成功");
                } else {
                    System.out.println("文件: {"+path + "} 创建成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
