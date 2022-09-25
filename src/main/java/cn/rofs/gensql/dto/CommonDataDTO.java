package cn.rofs.gensql.dto;

/**
 * @author rainofsilence
 * @date 2022/8/13 周六
 */
public class CommonDataDTO {

    // 数据文件名称不带后缀
    private String dataFileNameWithoutSuffix;
    // 数据文件所在目录
    private String dataFileDirPath;
    // 结果文件所在目录
    private String resultFileDirPath;
    // 日志文件所在目录
    private String logFileDirPath;
    // 日志文件名称
    private String logFileName;
    // 结果文件名称
    private String resultFileName;

    public String getDataFileNameWithoutSuffix() {
        return dataFileNameWithoutSuffix;
    }

    public void setDataFileNameWithoutSuffix(String dataFileNameWithoutSuffix) {
        this.dataFileNameWithoutSuffix = dataFileNameWithoutSuffix;
    }

    public String getDataFileDirPath() {
        return dataFileDirPath;
    }

    public void setDataFileDirPath(String dataFileDirPath) {
        this.dataFileDirPath = dataFileDirPath;
    }

    public String getResultFileDirPath() {
        return resultFileDirPath;
    }

    public void setResultFileDirPath(String resultFileDirPath) {
        this.resultFileDirPath = resultFileDirPath;
    }

    public String getLogFileDirPath() {
        return logFileDirPath;
    }

    public void setLogFileDirPath(String logFileDirPath) {
        this.logFileDirPath = logFileDirPath;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}
