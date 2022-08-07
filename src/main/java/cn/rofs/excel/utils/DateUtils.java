package cn.rofs.excel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rainofsilence
 * @date 2022/8/6 周六
 */
public class DateUtils {

    public static String getCurDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
