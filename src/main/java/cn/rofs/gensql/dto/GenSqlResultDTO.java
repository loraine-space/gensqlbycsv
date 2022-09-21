package cn.rofs.gensql.dto;

/**
 * @author rainofsilence
 * @date 2022/8/13 周六
 */
public class GenSqlResultDTO {

    private StringBuffer sql;

    private String err;

    public GenSqlResultDTO() {
    }

    public GenSqlResultDTO(StringBuffer sql, String err) {
        this.sql = sql;
        this.err = err;
    }

    public StringBuffer getSql() {
        return sql;
    }

    public void setSql(StringBuffer sql) {
        this.sql = sql;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public static GenSqlResultDTO SUCCESS(StringBuffer sql) {
        return new GenSqlResultDTO(sql, null);
    }

    public static GenSqlResultDTO FAIL(String err) {
        return new GenSqlResultDTO(null, err);
    }

}
