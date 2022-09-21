package cn.rofs.gensql.sqlopt;

import cn.rofs.gensql.dto.GenSqlResultDTO;

import java.util.Map;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public interface OptService {

    GenSqlResultDTO genSql(String curLine, Map<String,Object> headerMap);
}
