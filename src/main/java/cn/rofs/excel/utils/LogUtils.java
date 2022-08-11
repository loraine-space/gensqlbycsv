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

    public static String genLogFilePath() {
        return genLogFilePathWithCsvName(null);
    }

    public static String genLogFilePathWithCsvName(String csvName) {
        StringBuilder sbLogPath = new StringBuilder();
        sbLogPath.append(SysConstant.DEFAULT_LOG_DIR_PATH).append(File.separator).append(DateUtils.getCurDate()).append(File.separator);
        FileUtils.mkdirs(sbLogPath.toString());
        StringBuilder sbLogName = new StringBuilder();
        sbLogName.append("error_");
        if (csvName != null && csvName.length() > 0) {
            sbLogName.append(csvName).append("_");
        }
        sbLogName.append(DateUtils.getCurDatetime()).append(".log");
        FileUtils.mkFile(sbLogPath.append(sbLogName).toString());
        return sbLogPath.toString();
    }


}
