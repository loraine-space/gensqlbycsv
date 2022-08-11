package cn.rofs.excel.constant;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 */
public class SysConstant {

    // CSV文件放置位置
    public static final String DEFAULT_DATA_DIR_PATH = "src/main/resources/excel/data";
    // SQL文件放置位置
    public static final String DEFAULT_RESULT_DIR_PATH = "src/main/resources/excel/result";

    public static final String DEFAULT_LOG_DIR_PATH = "src/main/resources/excel/log";

    public static final String OPT_TYPE_INS = "ins";
    public static final String OPT_TYPE_UPD = "upd";
    public static final String OPT_TYPE_DEL = "del";

    public static final String KEY_TN = "table_name";

    public static final String KEY_OT = "opt_type";

    public static final String KEY_PKC = "primary_key_counts";

    /**
     * col type and val separator
     */
    public static final String SEPARATOR_TV = "::";
}
