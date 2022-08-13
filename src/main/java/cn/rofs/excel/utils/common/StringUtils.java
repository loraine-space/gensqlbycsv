package cn.rofs.excel.utils.common;

import sun.security.util.ArrayUtil;

/**
 * @author rainofsilence
 * @date 2022/8/13 周六
 */
public class StringUtils {

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() < 1;
    }

    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }
}
