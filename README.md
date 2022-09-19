
[![Version](https://img.shields.io/badge/Version-1.0.2-ff69b4)](https://github.com/maue-opensource/gensqlbycsv/releases)
[![Java](https://img.shields.io/badge/Java-1.8%2B-green)](https://www.openlogic.com/openjdk-downloads)
[![License](https://img.shields.io/badge/License-MIT-green)](https://opensource.org/licenses/MIT)

This util is used for batch generate sql.

---

## How to use

### Part1. Use sqlTemplate

##### Step1. Build sqlTemplate

```mysql
insert into `user_info` (`id`, `name`, `age`, `sex`) values ('$[0]', '$[1]', $[2], '$[3]')
```

##### Step2. Build CSV files

```csv
id,name,age,sex
2022090901,sumin,17,woman
2022090902,maue,18,woman
```

##### Step3. Use func

```Java
class Test {
    public static void main(String[] args) {
        GenSqlUtils.generateBySqlTemplate("SqlTemplate","fileDirPath","dataFileName");        
    }
}
```

## How to get

### Part1. Maven

```xml
<dependency>
  <groupId>cn.rofs</groupId>
  <artifactId>gensqlbycsv</artifactId>
  <version>{version}</version>
</dependency>
```

### Part2. Download jar

1. [1.0.2-release](https://github.com/maue-opensource/gensqlbycsv/releases/download/v1.0.2/gensqlbycsv-1.0.2.jar)

[//]: # (2. [1.0.1-release]&#40;https://github.com/maue-opensource/gensqlbycsv/releases/download/v1.0.1/gensqlbycsv-1.0.1.jar&#41;)

## Finally

If you have any usage questions, please contact me(<822466858@qq.com>).