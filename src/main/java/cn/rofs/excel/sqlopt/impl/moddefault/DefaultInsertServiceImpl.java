package cn.rofs.excel.sqlopt.impl.moddefault;

import cn.rofs.excel.ColValueConvertUtils;
import cn.rofs.excel.sqlopt.OptService;

import java.util.Map;

import static cn.rofs.excel.constant.SysConstant.*;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class DefaultInsertServiceImpl implements OptService {
    @Override
    public StringBuffer genSql(String curLine, Map<String, Object> headerMap) {
        // System.out.println("start insertService");
        Integer primaryKeyCounts = Integer.valueOf(headerMap.getOrDefault(KEY_PKC, 0).toString());
        String tableName = headerMap.get(KEY_TN).toString();
        String[] lineArr = curLine.split(",");

        StringBuffer result = new StringBuffer();

        StringBuffer insCol = new StringBuffer();
        StringBuffer insVal = new StringBuffer();
        StringBuffer delWhere = new StringBuffer();

        for (int i = 0; i < lineArr.length; i++) {
            String iLine = lineArr[i];
            String[] colArr = ColValueConvertUtils.convertWithCol(iLine);
            String colName = colArr[0];
            String colValue = colArr[1];
            if (i <= primaryKeyCounts) {
                if (delWhere.length() > 0) {
                    delWhere.append(" and");
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

        result.append("delete from ").append(tableName).append(" where").append(delWhere).append(";\r\n");
        result.append("insert into ").append(tableName).append("(").append(insCol).append(") values (")
                .append(insVal).append(");\r\n");
        return result;
    }
}
