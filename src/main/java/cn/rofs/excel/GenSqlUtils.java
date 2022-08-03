package cn.rofs.excel;

import cn.rofs.excel.dto.ResultDTO;
import cn.rofs.excel.enums.ModelTypeEnum;

import java.io.File;

import static cn.rofs.excel.constant.SysConstant.DEFAULT_DATA_DIR_PATH;
import static cn.rofs.excel.constant.SysConstant.DEFAULT_RESULT_DIR_PATH;

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
        String[] dataFileName_ = dataFileName.split("[.]");
        String dataFullPath = DEFAULT_DATA_DIR_PATH + File.separator + dataFileName;
        System.out.println("dataFullPath = " + dataFullPath);
        String resultFullPath = DEFAULT_RESULT_DIR_PATH + File.separator + dataFileName_[0] + ".sql";
        System.out.println("resultFullPath = " + resultFullPath);
        return generate(dataFullPath, resultFullPath, modelType);
    }

    /**
     * @param dataFullPath   csv文件全路径(xx/.../xx.csv)
     * @param resultFullPath sql文件全路径(xx/.../xx.sql)
     * @param modelType      模版类型
     * @return
     */
    public static ResultDTO<Object> generate(String dataFullPath, String resultFullPath, ModelTypeEnum modelType) {
        return ResultDTO.SUCCESS();
    }

    /**
     * 检查数据文件名称是否符合规范
     *
     * @param dataFileName
     * @return
     */
    private boolean checkDataFileName(String dataFileName) {
        // TODO 增则检验待做
        return true;
    }
}
