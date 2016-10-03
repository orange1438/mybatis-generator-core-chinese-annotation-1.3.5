# mybatis-generator-core-chinese-annotation
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
maven工程的打包，执行命令：clean install -Dmaven.test.skip=true（一定要跳过测试路径），加入到本地仓库,生成“1.3.5-chinese-annotation”包名
<br>
## 源码剖析说明
1.剖析org.mybatis.generator.plugins.ToStringPlugin源码<br>
2.剖析org.mybatis.generator.plugins.MapperConfigPlugin源码<br>
3.剖析org.mybatis.generator.api.ShellRunner源码，Main入口<br>
3.剖析org.mybatis.generator.config.xml.ConfigurationParser源码，配置解析器，用于对generatorConfig.xml配置文件的解析<br>
4.剖析org.mybatis.generator.config.Context源码，封装<context>元素内容<br>