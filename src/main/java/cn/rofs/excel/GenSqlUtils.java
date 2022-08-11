package cn.rofs.excel;

import cn.rofs.excel.dto.ResultDTO;
import cn.rofs.excel.enums.ModelTypeEnum;
import cn.rofs.excel.sqlopt.OptService;
import cn.rofs.excel.sqlopt.OptServiceBuilder;
import cn.rofs.excel.utils.DateUtils;
import cn.rofs.excel.utils.FileUtils;
import cn.rofs.excel.utils.LogUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.rofs.excel.constant.SysConstant.*;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 * @desc sql生成工具类
 */
public class GenSqlUtils {

    /**
     * @param dataFileName 文件名称(xx.csv)
     * @return
     */
    public static ResultDTO<Object> defaultGenerate(String dataFileName) {
        return defaultGenerate(dataFileName, ModelTypeEnum.DEFAULT);
    }

    /**
     * @param dataFileName 文件名称(xx.csv)
     * @param modelType    模版类型
     * @return
     */
    public static ResultDTO<Object> defaultGenerate(String dataFileName, ModelTypeEnum modelType) {
        return generate(dataFileName, DEFAULT_DATA_DIR_PATH, DEFAULT_RESULT_DIR_PATH, modelType);
    }

    /**
     * @param dataFileName  csv文件名称
     * @param dataDirPath   csv文件夹路径(xx/...)
     * @param resultDirPath sql文件夹路径(xx/...)
     * @param modelType     模版类型
     * @return
     */
    public static ResultDTO<Object> generate(String dataFileName, String dataDirPath, String resultDirPath, ModelTypeEnum modelType) {
        ResultDTO<Object> resultDTO = ResultDTO.SUCCESS();
        if (!checkDataFileNameIsCSV(dataFileName)) {
            return ResultDTO.FAIL("dataFileName不符合规范");
        }
        String[] dataFileName_ = dataFileName.split("[.]");
        String dataFullPath = dataDirPath + File.separator + dataFileName;
        String resultFullPath = resultDirPath + File.separator + dataFileName_[0] + "_" + DateUtils.getCurDatetime() + ".sql";

        String errorLogPath = LogUtils.genLogFilePathWithCsvName(dataFileName_[0]);

        File csvFile = new File(dataFullPath);
        if (!csvFile.exists()) {
            LogUtils.genErrorLog(errorLogPath,"csv文件不存在");
            return ResultDTO.FAIL("csv文件不存在");
        }
        BufferedReader br = null;
        StringBuilder resultSql = new StringBuilder();
        Map<String, Object> headerMap = new HashMap<>();
        FileWriter fw = null;
        resultSql.append("------START------").append(dataFileName).append("------START------\r\n");
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String curLine;
            int lineCount = 0;
            while ((curLine = br.readLine()) != null) {
                if (lineCount == 0) {
                    headerMap = handleHeaderData(curLine, modelType);
                    lineCount++;
                    continue;
                }
                switch (modelType) {
                    case DEFAULT:
                        OptService optService = OptServiceBuilder.getOptService(modelType + "-" + headerMap.get(KEY_OT).toString());
                        resultSql.append(optService.genSql(curLine, headerMap));
                        break;
                    default:
                        break;
                }
                lineCount++;
            }
            resultSql.append("------END------").append(dataFileName).append("------END------\r\n");
            // 判断输出文件夹是否存在
            FileUtils.mkdirs(resultDirPath);
            fw = new FileWriter(resultFullPath);
            fw.write(resultSql.toString());
            System.out.println("File: {" + resultFullPath + "} 创建成功.");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.genErrorLog(errorLogPath, e.getMessage());
            return ResultDTO.FAIL("读取csv文件出错");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    resultDTO = ResultDTO.FAIL("读取csv文件出错");
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    resultDTO =  ResultDTO.FAIL("读取csv文件出错");
                }
            }
        }
        return resultDTO;
    }

    /**
     * 检查数据文件名称是否符合规范
     *
     * @param dataFileName
     * @return
     */
    private static boolean checkDataFileNameIsCSV(String dataFileName) {
        if (dataFileName == null || dataFileName.length() < 1) return false;
        String PATTERN_REGEX_CSV = "[a-zA-Z0-9_@-]+\\.(csv|CSV)$";
        return Pattern.matches(PATTERN_REGEX_CSV, dataFileName);
    }

    /**
     * 处理首行数据
     *
     * @param curLine   行内容
     * @param modelType 模版类型
     * @return
     */
    private static Map<String, Object> handleHeaderData(String curLine, ModelTypeEnum modelType) {
        Map<String, Object> resultMap = new HashMap<>();
        if (ModelTypeEnum.DEFAULT.equals(modelType)) {
            String[] arr = curLine.split(",");
            for (String s : arr) {
                resultMap.put(s.split("::")[0], s.split("::")[1]);
            }
        }
        return resultMap;
    }


}
