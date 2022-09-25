package cn.rofs.gensql;

import cn.rofs.gensql.dto.CommonDataDTO;
import cn.rofs.gensql.dto.GenSqlResultDTO;
import cn.rofs.gensql.dto.ResultDTO;
import cn.rofs.gensql.enums.ModelTypeEnum;
import cn.rofs.gensql.sqlopt.OptService;
import cn.rofs.gensql.sqlopt.OptServiceBuilder;
import cn.rofs.gensql.utils.DateUtils;
import cn.rofs.gensql.utils.EscapeCharUtils;
import cn.rofs.gensql.utils.FileUtils;
import cn.rofs.gensql.utils.LogUtils;
import cn.rofs.gensql.utils.common.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.rofs.gensql.constant.SysConstant.DEFAULT_FILE_DIR_PATH;
import static cn.rofs.gensql.constant.SysConstant.KEY_OT;
import static cn.rofs.gensql.enums.ModelTypeEnum.DEFAULT;
import static cn.rofs.gensql.enums.ModelTypeEnum.ONE;

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
    public static ResultDTO defaultGenerate(String dataFileName) {
        return defaultGenerate(DEFAULT, dataFileName);
    }

    /**
     * @param modelType    模版类型
     * @param dataFileName 文件名称(xx.csv)
     * @return
     */
    public static ResultDTO defaultGenerate(ModelTypeEnum modelType, String dataFileName) {
        return generate(modelType, DEFAULT_FILE_DIR_PATH, dataFileName);
    }

    /**
     * @param modelType     模版类型
     * @param dataFileNames 文件名称...(xx.csv)
     * @return
     */
    public static ResultDTO defaultGenerate(ModelTypeEnum modelType, CharSequence... dataFileNames) {
        return generate(modelType, DEFAULT_FILE_DIR_PATH, dataFileNames);
    }

    /**
     * 这种生成方式会自动根据`fileDirPath`找到对应的数据文件目录、生成结果文件目录、错误日志目录
     *
     * @param modelType    模版类型
     * @param fileDirPath  文件路径
     * @param dataFileName csv文件名称(xx.csv)
     * @return
     */
    public static ResultDTO generate(ModelTypeEnum modelType, String fileDirPath, String dataFileName) {
        ResultDTO resultDTO = ResultDTO.SUCCESS();
        if (StringUtils.isAnyEmpty(dataFileName, fileDirPath)) {
            return ResultDTO.FAIL("There is a null value with `dataFileName` and `fileDirPath`.");
        }
        if (!checkDataFileNameIsCSV(dataFileName)) {
            return ResultDTO.FAIL("`dataFileName` is non-compliant.");
        }
        // 生成文件路径、名称等信息
        CommonDataDTO commonData = genCommonData(dataFileName, fileDirPath);

        File csvFile = new File(commonData.getDataFileDirPath() + File.separator + dataFileName);
        if (!csvFile.exists()) {
            return ResultDTO.FAIL("dataFile does not exist.");
        }
        Map<String, Object> headerMap = new HashMap<>();
        BufferedReader br = null;
        StringBuilder resultSql = new StringBuilder();
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
            return ResultDTO.FAIL("Program exception: {" + e.toString() + "}");
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
     * TODO: new feature 支持多excel文件生成sql到一个文件中
     * PS: 该功能优先级不高 也可以考虑不做
     *
     * @param modelType     模版类型
     * @param fileDirPath   文件路径
     * @param dataFileNames csv文件名称...(xx.csv)
     * @return
     */
    public static ResultDTO generate(ModelTypeEnum modelType, String fileDirPath, CharSequence... dataFileNames) {
        return ResultDTO.FAIL("该版本暂不支持该功能");
    }

    /**
     * 根据SqlTemplate生成sql
     *
     * @param sqlTemplate  sql模版
     * @param fileDirPath  文件目录
     * @param dataFileName 文件名称
     * @return
     */
    public static ResultDTO generateBySqlTemplate(String sqlTemplate, String fileDirPath, String dataFileName) {
        ResultDTO defaultCheckResult = defaultCheck(fileDirPath, dataFileName);
        if (!defaultCheckResult.isSuccess()) {
            return defaultCheckResult;
        }
        // 生成文件路径、名称等信息
        CommonDataDTO commonData = genCommonData(dataFileName, fileDirPath);

        File csvFile = new File(commonData.getDataFileDirPath() + File.separator + dataFileName);
        if (!csvFile.exists()) {
            return ResultDTO.FAIL("dataFile does not exist.");
        }

        BufferedReader br = null;
        StringBuilder resultSql = new StringBuilder();
        FileWriter fw = null;
        resultSql.append("------START------").append(dataFileName).append("------START------\r\n");
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String curLine;
            // 行数
            int lineCount = 0;
            // 需要替换换的参数数量
            int paramCount = 0;
            while ((curLine = br.readLine()) != null) {
                if (lineCount == 0) {
                    paramCount = curLine.split(",").length;
                    lineCount++;
                    continue;
                }

                String sql = sqlTemplate;
                String[] items = curLine.split(",");
                for (int i = 0; i < items.length; i++) {
                    sql = sql.replace("$[" + i + "]", numberEmptyToNull(items[i], sql, i));
                }
                // 存在最后一行是空参数的情况
                if (items.length < paramCount) {
                    int index = paramCount - 1;
                    sql = sql.replace("$[" + index + "]", numberEmptyToNull(null, sql, index));
                }
                // 转义字符
                sql = EscapeCharUtils.escape(sql);
                resultSql.append(sql);
            }
            resultSql.append("------END------").append(dataFileName).append("------END------\r\n");
            // 判断输出文件夹是否存在
            FileUtils.mkdirs(commonData.getResultFileDirPath());
            fw = new FileWriter(commonData.getResultFileDirPath() + File.separator + commonData.getResultFileName());
            fw.write(resultSql.toString());
            System.out.println("File: {" + commonData.getResultFileDirPath() + File.separator + commonData.getResultFileName() + "} 创建成功.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultDTO.FAIL("Program exception: {" + e.toString() + "}");
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
        return ResultDTO.SUCCESS();
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

    private static CommonDataDTO genCommonData(@NotNull String dataFileName, String fileDirPath) {
        CommonDataDTO target = new CommonDataDTO();
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
        return target;
    }

    /**
     * 检查文件及目录是否符合
     *
     * @param fileDirPath
     * @param dataFileName
     * @return
     */
    private static ResultDTO defaultCheck(String fileDirPath, String dataFileName) {
        if (StringUtils.isAnyEmpty(dataFileName, fileDirPath)) {
            return ResultDTO.FAIL("There is a null value with `dataFileName` and `fileDirPath`.");
        }
        if (!checkDataFileNameIsCSV(dataFileName)) {
            return ResultDTO.FAIL("`dataFileName` is non-compliant.");
        }
        return ResultDTO.SUCCESS();
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
     * 数值类型的空参数转换
     *
     * @param curParam
     * @param sql
     * @param index
     * @return
     */
    private static String numberEmptyToNull(String curParam, String sql, int index) {
        if (StringUtils.isEmpty(curParam) && !sql.contains("'$[" + index + "]'")) {
            return "null";
        }
        return curParam == null ? "" : curParam;
    }
}
