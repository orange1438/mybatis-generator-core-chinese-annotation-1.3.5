package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统一Mapper生成
 *
 * @author orange1438
 *         github: github.com/orange1438
 *         date: 2017/02/19 20:05
 */
public class MapperPlugin extends PluginAdapter {
    private String interfaceName;
    private boolean deleteMethod = true;

    private FullyQualifiedJavaType interfaceType;

    private FullyQualifiedJavaType E;
    private FullyQualifiedJavaType M;
    private FullyQualifiedJavaType MLIST;
    private FullyQualifiedJavaType ID;


    @Override
    public boolean validate(List<String> warnings) {
        interfaceName = this.properties.getProperty("interfaceName");
        String deleteMethod = this.properties.getProperty("deleteMethod");
        if ("FALSE".equals(deleteMethod.toUpperCase())) {
            this.deleteMethod = Boolean.getBoolean(deleteMethod);
        } else {
            this.deleteMethod = true;
        }

        E = new FullyQualifiedJavaType("E");
        M = new FullyQualifiedJavaType("M");
        MLIST = new FullyQualifiedJavaType("List<M>");
        ID = new FullyQualifiedJavaType("ID");

        String interfacePack = context.getJavaClientGeneratorConfiguration().getTargetPackage();
        interfaceType = new FullyQualifiedJavaType(interfacePack + "." + interfaceName);
        return true;
    }

    /**
     * 生成的Mapper接口
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (deleteMethod) {
            // 清空导入的包
            interfaze.clearImportedTypes();
            interfaze.clearMethod();

            //获取实体类
            FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

            //注解的添加
            FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType("org.springframework.stereotype.Repository");
            interfaze.addImportedType(serviceType);
            interfaze.addAnnotation("@Repository");

            //import接口
            // 解决多个表，而重复生成IMaper的问题
            interfaceType.clearTypeArgument();
            interfaze.addImportedType(interfaceType);

            FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
            interfaze.addImportedType(exampleType);

            if (introspectedTable.getPrimaryKeyColumns() == null) {
                interfaze.addImportedType(new FullyQualifiedJavaType("该表没设置主键"));
            } else {
                interfaze.addImportedType(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType());
            }


            interfaze.addSuperInterface(
                    new FullyQualifiedJavaType(interfaceType.getShortName()
                            + "<"
                            + entityType.getShortName()
                            + "," + exampleType.getShortName()
                            + "," + introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName()
                            + ">"));

            //import实体类
            interfaze.addImportedType(entityType);
            return true;
        } else return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        if (deleteMethod) {
            List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
            Interface interface1 = new Interface(interfaceType);
            interface1.setVisibility(JavaVisibility.PUBLIC);

            // 导入必要的类
            interface1.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
            interface1.addImportedType(new FullyQualifiedJavaType("java.util.List"));
            interface1.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));

            // 添加泛型支持
            interfaceType.addTypeArgument(new FullyQualifiedJavaType("M, E, ID extends Serializable"));

            // 添加方法并加注释
            Method method = countByExample(introspectedTable);
            interface1.addMethod(method);

            method = deleteByExample(introspectedTable);
            interface1.addMethod(method);

            method = deleteByPrimaryKey(introspectedTable);
            interface1.addMethod(method);

            method = insert(introspectedTable);
            interface1.addMethod(method);

            method = insertSelective(introspectedTable);
            interface1.addMethod(method);

            method = insertBatch(introspectedTable);
            interface1.addMethod(method);

            method = insertBatchSelective(introspectedTable);
            interface1.addMethod(method);

            method = selectByExampleWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            method = selectByExample(introspectedTable);
            interface1.addMethod(method);

            method = selectByPrimaryKey(introspectedTable);
            interface1.addMethod(method);

            method = updateByPrimaryKeySelective(introspectedTable);
            interface1.addMethod(method);

            method = updateByPrimaryKeyWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            method = updateByPrimaryKey(introspectedTable);
            interface1.addMethod(method);

            method = updateByExample(introspectedTable);
            interface1.addMethod(method);

            method = updateByExampleSelective(introspectedTable);
            interface1.addMethod(method);

            method = updateByExampleWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            addExampleClassComment(interface1);

            String project = context.getJavaClientGeneratorConfiguration().getTargetProject();
            GeneratedJavaFile file = new GeneratedJavaFile(interface1, project, context.getJavaFormatter());
            files.add(file);
            return files;
        } else return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

    private void addExampleClassComment(JavaElement javaElement) {
        javaElement.addJavaDocLine("/**");
        javaElement.addJavaDocLine(" * 通用IMapper<M, E, ID>");
        javaElement.addJavaDocLine(" * M:实体类");
        javaElement.addJavaDocLine(" * E:Example");
        javaElement.addJavaDocLine(" * ID:主键的变量类型");
        javaElement.addJavaDocLine(" *");
        javaElement.addJavaDocLine(" * @author orange1438");
        javaElement.addJavaDocLine(" *         github: https://github.com/orange1438");
        javaElement.addJavaDocLine(" *         date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        javaElement.addJavaDocLine(" */");
    }

    /**
     * 添加方法
     */
    protected Method countByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("countByExample");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method deleteByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("deleteByExample");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method deleteByPrimaryKey(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("deleteByPrimaryKey");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(ID, "id"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insert(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insert");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertSelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertBatch(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatch");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MLIST, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertBatchSelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatchSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MLIST, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method selectByExampleWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByExampleWithBLOBs");
        method.setReturnType(new FullyQualifiedJavaType("List<M>"));
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method selectByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByExample");
        method.setReturnType(new FullyQualifiedJavaType("List<M>"));
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method selectByPrimaryKey(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByPrimaryKey");
        method.setReturnType(new FullyQualifiedJavaType("M"));
        method.addParameter(new Parameter(ID, "id"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByPrimaryKeySelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByPrimaryKeySelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByPrimaryKeyWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByPrimaryKeyWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByPrimaryKey(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByPrimaryKey");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByExample");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter record = new Parameter(M, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByExampleSelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByExampleSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter record = new Parameter(M, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByExampleWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByExampleWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter record = new Parameter(M, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
}
