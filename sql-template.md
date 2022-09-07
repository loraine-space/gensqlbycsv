# sql-template

通过`SqlTemplate`来生成`sql`的使用方法说明

### 1. 构造sql模版

通过"$[num]"的来指代参数, `num`表示在csv文件中的col number

```MySQL
insert into user_info (id, name, age, sex) values ('$[0]', '$[1]', $[2], '$[3]')
```
### 2. 构造csv文件

```csv
2022090901,sumin,17,woman
2022090902,maue,18,woman
```

### 3. 执行

```Java
GenSqlUtils.generateBySqlTemplate("SqlTemplate","fileDirPath","dataFileName");
```