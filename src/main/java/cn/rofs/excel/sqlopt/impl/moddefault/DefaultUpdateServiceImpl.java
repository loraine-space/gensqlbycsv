package cn.rofs.excel.sqlopt.impl.moddefault;

import cn.rofs.excel.sqlopt.OptService;
import cn.rofs.excel.utils.ColValueConvertUtils;

import java.util.Map;

import static cn.rofs.excel.constant.SysConstant.KEY_PKC;
import static cn.rofs.excel.constant.SysConstant.KEY_TN;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class DefaultUpdateServiceImpl implements OptService {
    @Override
    public StringBuffer genSql(String curLine, Map<String, Object> headerMap) {
        // System.out.println("start updateService");
        Integer primaryKeyCounts = Integer.valueOf(headerMap.getOrDefault(KEY_PKC, 0).toString());
        String tableName = headerMap.get(KEY_TN).toString();
        String[] lineArr = curLine.split(",");

        StringBuffer result = new StringBuffer();

        StringBuilder updChange = new StringBuilder();
        StringBuilder updWhere = new StringBuilder();

        for (int i = 0; i < lineArr.length; i++) {
            String iLine = lineArr[i];
            String[] colArr = ColValueConvertUtils.convertWithCol(iLine);
            String colName = colArr[0];
            String colValue = colArr[1];
            if (i <= primaryKeyCounts) {
                if (updWhere.length() > 0) {
                    updWhere.append(" and");
                }
                updWhere.append(colName).append(" = ").append(colValue);
                continue;
            }
            if (updChange.length() > 0) {
                updChange.append(", ");
            }
            updChange.append(colName).append(" = ").append(colValue);
        }
        result.append("update ").append(tableName).append(" set ").append(updChange).append(" where ").append(updWhere).append(";\r\n");
        return result;
    }
}
