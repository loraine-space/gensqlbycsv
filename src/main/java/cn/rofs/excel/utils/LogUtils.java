package cn.rofs.excel.utils;

import cn.rofs.excel.constant.SysConstant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author rainofsilence
 * @date 2022/8/10 周三
 */
public class LogUtils {

    public static void genErrorLog(String path, String errMsg) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(errMsg + "\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
