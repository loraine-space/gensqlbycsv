package cn.rofs.excel;

import cn.rofs.excel.dto.CommonDataDTO;
import cn.rofs.excel.dto.GenSqlResultDTO;
import cn.rofs.excel.dto.ResultDTO;
import cn.rofs.excel.enums.ModelTypeEnum;
import cn.rofs.excel.sqlopt.OptService;
import cn.rofs.excel.sqlopt.OptServiceBuilder;
import cn.rofs.excel.utils.DateUtils;
import cn.rofs.excel.utils.FileUtils;
import cn.rofs.excel.utils.LogUtils;
import cn.rofs.excel.utils.common.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.rofs.excel.constant.SysConstant.DEFAULT_FILE_DIR_PATH;
import static cn.rofs.excel.constant.SysConstant.KEY_OT;
import static cn.rofs.excel.enums.ModelTypeEnum.DEFAULT;
import static cn.rofs.excel.enums.ModelTypeEnum.ONE;

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
        return defaultGenerate(dataFileName, DEFAULT);
    }

    /**
     * @param dataFileName 文件名称(xx.csv)
     * @param modelType    模版类型
     * @return
     */
    public static ResultDTO<Object> defaultGenerate(String dataFileName, ModelTypeEnum modelType) {
        return generate(dataFileName, DEFAULT_FILE_DIR_PATH, modelType);
    }

    /**
     * 这种生成方式会自动根据`fileDirPath`找到对应的数据文件目录、生成结果文件目录、错误日志目录
     *
     * @param dataFileName csv文件名称(xx.csv)
     * @param fileDirPath  文件路径
     * @param modelType    模版类型
     * @return
     */
    public static ResultDTO<Object> generate(String dataFileName, String fileDirPath, ModelTypeEnum modelType) {
        ResultDTO<Object> resultDTO = ResultDTO.SUCCESS();
        if (StringUtils.isAnyEmpty(dataFileName, fileDirPath)) {
            return ResultDTO.FAIL("There is a null value with `dataFileName` and `fileDirPath`.");
        }
        if (!checkDataFileNameIsCSV(dataFileName)) {
            return ResultDTO.FAIL("`dataFileName` is non-compliant.");
        }
        // 生成文件路径、名称等信息
        CommonDataDTO commonData = new CommonDataDTO();
        genCommonData(dataFileName, fileDirPath, commonData);

        File csvFile = new File(commonData.getDataFileDirPath() + File.separator + dataFileName);
        if (!csvFile.exists()) {
            return ResultDTO.FAIL("dataFile does not exist.");
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
                if (DEFAULT.equals(modelType) && lineCount == 0) {
                    headerMap = handleHeaderData(curLine, modelType);
                    lineCount++;
                    continue;
                }

                if (ONE.equals(modelType)) {
                    headerMap = handleHeaderData(curLine, modelType);
                }

                OptService optService = OptServiceBuilder.getOptService(modelType + "-" + headerMap.get(KEY_OT).toString());
                GenSqlResultDTO genSqlResult = optService.genSql(curLine, headerMap);
                if (StringUtils.isEmpty(genSqlResult.getErr())) {
                    resultSql.append(genSqlResult.getSql());
                    lineCount++;
                    continue;
                }
                LogUtils.saveLog(commonData, genSqlResult.getErr(), lineCount);
                lineCount++;
            }
            resultSql.append("------END------").append(dataFileName).append("------END------\r\n");
            // 判断输出文件夹是否存在
            FileUtils.mkdirs(commonData.getResultFileDirPath());
            fw = new FileWriter(commonData.getResultFileDirPath() + File.separator + commonData.getResultFileName());
            fw.write(resultSql.toString());
            System.out.println("File: {" + commonData.getResultFileDirPath() + File.separator + commonData.getResultFileName() + "} 创建成功.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultDTO.FAIL("Program exception: {" + e.getMessage() + "}");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultDTO.FAIL("Program exception: {" + e.getMessage() + "}");
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultDTO.FAIL("Program exception: {" + e.getMessage() + "}");
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
    private static @NotNull Map<String, Object> handleHeaderData(String curLine, ModelTypeEnum modelType) {
        Map<String, Object> resultMap = new HashMap<>();
        if (DEFAULT.equals(modelType)) {
            String[] arr = curLine.split(",");
            for (String s : arr) {
                resultMap.put(s.split("::")[0], s.split("::")[1]);
            }
        } else if (ONE.equals(modelType)) {
            String[] arr = curLine.split(",");
            for (int i = 0; i < 3; i++) {
                String s = arr[i];
                resultMap.put(s.split("::")[0], s.split("::")[1]);
            }
        }
        return resultMap;
    }

    private static void genCommonData(@NotNull String dataFileName, String fileDirPath, @NotNull CommonDataDTO target) {
        target.setDataFileNameWithoutSuffix(dataFileName.split("[.]")[0]);
        StringBuilder sbFileDirPath = new StringBuilder(fileDirPath);
        if (!fileDirPath.endsWith(File.separator)) {
            sbFileDirPath.append(File.separator);
        }
        target.setDataFileDirPath(sbFileDirPath + "data");
        target.setResultFileDirPath(sbFileDirPath + "result");
        target.setLogFileDirPath(sbFileDirPath + "log");
        target.setLogFileName("log_" + DateUtils.getCurDatetime() + "_" + target.getDataFileNameWithoutSuffix() + ".log");
        target.setResultFileName(target.getDataFileNameWithoutSuffix() + "_result_" + DateUtils.getCurDatetime() + ".sql");
    }

}
