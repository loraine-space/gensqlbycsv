package cn.rofs.gensql.sqlopt.impl.modone;

import cn.rofs.gensql.dto.GenSqlResultDTO;
import cn.rofs.gensql.sqlopt.OptService;
import cn.rofs.gensql.utils.ColValueConvertUtils;

import java.util.Map;

import static cn.rofs.gensql.constant.SysConstant.KEY_PKC;
import static cn.rofs.gensql.constant.SysConstant.KEY_TN;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class OneInsertServiceImpl implements OptService {
    @Override
    public GenSqlResultDTO genSql(String curLine, Map<String, Object> headerMap) {
        // System.out.println("start insertService");
        Integer primaryKeyCounts = Integer.valueOf(headerMap.getOrDefault(KEY_PKC, 0).toString());
        String tableName = headerMap.get(KEY_TN).toString();
        String[] lineArr = curLine.split(",");

        StringBuffer result = new StringBuffer();

        StringBuilder insCol = new StringBuilder();
        StringBuilder insVal = new StringBuilder();
        StringBuilder delWhere = new StringBuilder();

        for (int i = 3; i < lineArr.length; i++) {
            String iLine = lineArr[i];
            String[] colArr;
            try {
                colArr = ColValueConvertUtils.convertWithCol(iLine);
            } catch (Exception e) {
                return GenSqlResultDTO.FAIL(e.toString());
            }
            String colName = colArr[0];
            String colValue = colArr[1];
            if (i <= primaryKeyCounts) {
                if (delWhere.length() > 0) {
                    delWhere.append(" and ");
                }
                delWhere.append(colName).append(" = ").append(colValue);
            }
            if (insCol.length() > 0) {
                insCol.append(", ");
            }
            insCol.append(colName);

            if (insVal.length() > 0) {
                insVal.append(", ");
            }
            insVal.append(colValue);
        }
        if (primaryKeyCounts > 0) {
            result.append("delete from ").append(tableName).append(" where ").append(delWhere).append(";\r\n");
        }
        result.append("insert into ").append(tableName).append("(").append(insCol).append(") values (")
                .append(insVal).append(");\r\n");
        return GenSqlResultDTO.SUCCESS(result);
    }
}
