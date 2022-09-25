package cn.rofs.gensql.sqlopt.impl.modone;

import cn.rofs.gensql.dto.GenSqlResultDTO;
import cn.rofs.gensql.sqlopt.OptService;
import cn.rofs.gensql.utils.ColValueConvertUtils;

import java.util.Map;

import static cn.rofs.gensql.constant.SysConstant.KEY_TN;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class OneDeleteServiceImpl implements OptService {
    @Override
    public GenSqlResultDTO genSql(String curLine, Map<String, Object> headerMap) {
        // System.out.println("start deleteService");
        String tableName = headerMap.get(KEY_TN).toString();
        String[] lineArr = curLine.split(",");

        StringBuffer result = new StringBuffer();

        StringBuilder resultWhere = new StringBuilder();

        for (int i = 3; i < lineArr.length; i++) {
            String iLine = lineArr[i];
            String[] colArr;
            try {
                colArr = ColValueConvertUtils.convertWithCol(iLine);
            } catch (Exception e) {
                return GenSqlResultDTO.FAIL(e.toString());
            }
            String colName = colArr[0];
            String coValue = colArr[1];
            if (resultWhere.length() > 0) {
                resultWhere.append(" and ");
            }
            resultWhere.append(colName).append(" = ").append(coValue);
        }

        result.append("delete from ").append(tableName).append(" where ").append(resultWhere).append(";\r\n");
        return GenSqlResultDTO.SUCCESS(result);
    }
}
