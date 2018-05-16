# mybatis-generator-core-chinese-annotation（已删除ibatis2内容）
## 介绍
该项目是mybatis-generator-core 1.3.5 进行自定义注解生成的修改，
<a target=_blank href="https://github.com/mybatis/generator/releases">mybatis-generator-core 1.3.5官方下载地址</a>，
主要添加中文注释信息，详细修改内容如下：<br>
1.生成model对象的注释信息<br>
2.get/set方法的注释信息<br>
3.类注释信息<br>
4.增删改查条件的注释信息<br>
<br>
## 使用方式
maven工程的打包，执行命令：clean install ，加入到本地仓库,生成“1.3.5-chinese-annotation-by-orange1438”包名
<br>

## 源码剖析说明
1.剖析org.mybatis.generator.plugins.ToStringPlugin源码<br>
2.剖析org.mybatis.generator.plugins.MapperConfigPlugin源码<br>
3.剖析org.mybatis.generator.api.ShellRunner源码，Main入口<br>
3.剖析org.mybatis.generator.config.xml.ConfigurationParser源码，配置解析器，用于对generatorConfig.xml配置文件的解析<br>
4.剖析org.mybatis.generator.config.Context源码，封装<context>元素内容<br>

## 修改源码说明(原版本没有的功能)
1.数据表的备注信息的添加：在FullyQualifiedTable类中添加remark字段,并在org.mybatis.generator.internal.db.DatabaseIntrospector类calculateIntrospectedTables方法,添加一段获取数据库备注的代码<br>
```java
  //设置数据库表的备注信息
  //start
  Statement stmt = this.databaseMetaData.getConnection().createStatement();
  ResultSet rs = stmt.executeQuery(
          new StringBuilder()
          .append("SHOW TABLE STATUS LIKE '")
          .append(atn.getTableName())
          .append("'")
          .toString());
  while (rs.next())
      table.setRemark(rs.getString("COMMENT"));
  closeResultSet(rs);
  stmt.close();
  //end
```
<br>
2..非model类Example的注释方法的添加，方法名addExampleClassComment(TopLevelClass topLevelClass)<br>
3.重构部分org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl包里getGeneratedJavaFiles方法<br>
4.详细中文注释的添加，入口包函数在org.mybatis.generator.api.ShellRunner<br>
5.生成的中文注释信息可在修DefaultCommentGenerator类修改<br>
6.增加MybatisServicePlugin：service层的代码生成，个人觉得不完美，因为业务会变，所以service层也会变，仅供学习参考<br>
7.增加MapperPlugin：Mapper层有大量生成的重复方法，所以增加了统一继承IMapper接口实现<br>
8.为IMapper接口加入批量插入数据的方法<br>
9.删除ibatis2内容，并删除CaseInsensitiveLikePlugin插件（这个插件用来在XXXExample类中生成大小写敏感的LIKE方法插件本身用处不大，但是我们可以通过这个插件学习给XXXExample类添加额外的方法）），因此引用了ibatis2
10.为IMapper接口加入批量更新数据的方法<br>