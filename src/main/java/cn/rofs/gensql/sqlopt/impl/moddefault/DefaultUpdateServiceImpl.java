package cn.rofs.gensql.sqlopt.impl.moddefault;

import cn.rofs.gensql.dto.GenSqlResultDTO;
import cn.rofs.gensql.sqlopt.OptService;
import cn.rofs.gensql.utils.ColValueConvertUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static cn.rofs.gensql.constant.SysConstant.KEY_PKC;
import static cn.rofs.gensql.constant.SysConstant.KEY_TN;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class DefaultUpdateServiceImpl implements OptService {
    @Override
    public GenSqlResultDTO genSql(String curLine, @NotNull Map<String, Object> headerMap) {
        // System.out.println("start updateService");
        Integer primaryKeyCounts = Integer.valueOf(headerMap.getOrDefault(KEY_PKC, 0).toString());
        if (primaryKeyCounts < 1) {
            return GenSqlResultDTO.FAIL("Update sql must have a primary key.");
        }
        String tableName = headerMap.get(KEY_TN).toString();
        String[] lineArr = curLine.split(",");

        StringBuffer result = new StringBuffer();

        StringBuilder updChange = new StringBuilder();
        StringBuilder updWhere = new StringBuilder();

        for (int i = 0; i < lineArr.length; i++) {
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
        return GenSqlResultDTO.SUCCESS(result);
    }
}
