package cn.rofs.gensql.utils;

import cn.rofs.gensql.dto.CommonDataDTO;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author rainofsilence
 * @date 2022/8/10 周三
 */
public class LogUtils {

    public static void saveLog(@NotNull CommonDataDTO commonData, String errMsg, Integer lineCount) {
        FileUtils.mkdirs(commonData.getLogFileDirPath());
        FileUtils.mkFile(commonData.getLogFileDirPath() + File.separator + commonData.getLogFileName());
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(commonData.getLogFileDirPath() + File.separator + commonData.getLogFileName(), true));
            bw.write("csvLineCount: " + (lineCount + 1) + ", exception: {" + errMsg + "}\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
