package cn.rofs.excel.opt;

import java.util.Map;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public interface OptService {

    StringBuffer genSql(String curLine, Map<String,Object> headerMap);
}
