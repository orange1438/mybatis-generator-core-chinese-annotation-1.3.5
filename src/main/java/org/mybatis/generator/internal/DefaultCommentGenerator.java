/**
 * Copyright 2006-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.internal;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * The Class DefaultCommentGenerator.
 * 默认的注释的生成
 *
 * @author Jeff Butler
 */
public class DefaultCommentGenerator implements CommentGenerator {

    /**
     * The properties.
     */
    private Properties properties;

    /**
     * The suppress date.
     */
    private boolean suppressDate;

    /**
     * The suppress all comments.
     */
    private boolean suppressAllComments;

    /**
     * The addition of table remark's comments.
     * If suppressAllComments is true, this option is ignored
     */
    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;

    /**
     * Instantiates a new default comment generator.
     */
    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addJavaFileComment(org.mybatis.generator.api.dom.java.CompilationUnit)
     */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        compilationUnit.addFileCommentLine("/* github.com/orange1438 */");
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     * 删除mapper.xml中的注释
     *
     * @param xmlElement the xml element
     */
    public void addComment(XmlElement xmlElement) {
        // add no document level comments by default
        // 删除mapper.xml中的注释
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addRootComment(org.mybatis.generator.api.dom.xml.XmlElement)
     */
    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addConfigurationProperties(java.util.Properties)
     */
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement       the java element
     * @param markAsDoNotDelete the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        if (markAsDoNotDelete) {
            sb.append(" * do_not_delete_during_merge\n");
        }
        sb.append(" * @author orange1438 code generator");
        String s = getDateString();
        if (s != null) {
            sb.append("\n * ");
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     *
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            // 我就喜欢这个格式化，不服自己修改
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
    }

    /**
     * 我的类注释,用于非实体类Criteria的注释
     *
     * @param javaElement
     */
    @Override
    public void addExampleClassComment(JavaElement javaElement) {
        // * @author Acooly Code Generator
        // * Date: 2016-04-05 20:12:59
        if (suppressAllComments) {
            return;
        }
        javaElement.addJavaDocLine("/**");
        javaElement.addJavaDocLine(" * 本文件由 github.com/orange1438/mybatis-generator-core-chinese-annotation1.3.5-chinese-annotation 自动生成");
        addJavadocTag(javaElement, false);
        javaElement.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addTopLevelClassComment(org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        // 添加类注释
        if (suppressAllComments || !addRemarkComments) {
            return;
        }

        topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$

        String remarks = introspectedTable.getFullyQualifiedTable().getRemark();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine + " " + introspectedTable.getFullyQualifiedTable());  //$NON-NLS-1$
            }
        }

        addJavadocTag(topLevelClass, false);

        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addEnumComment(org.mybatis.generator.api.dom.java.InnerEnum, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$

        sb.append(" * This addEnumComment,中文注释自行修改、编译源码"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        // 添加字段注释
        StringBuffer sb = new StringBuffer();
        boolean defaultFlag = false;
        //对应表中字段的备注(数据库中自己写的备注信息)
        if (introspectedColumn.getRemarks() != null
                && !introspectedColumn.getRemarks().equals("")) {
            sb.append(" * " + introspectedColumn.getRemarks());
            defaultFlag = true;
        }
        if (introspectedColumn.getDefaultValue() != null
                && !introspectedColumn.getDefaultValue().equals("")) {
            if (defaultFlag) {
                sb.append("  默认：" + introspectedColumn.getDefaultValue());
            } else {
                sb.append(" * 默认：" + introspectedColumn.getDefaultValue());
            }
        }

        if (sb.length() > 0) {
            field.addJavaDocLine("/** ");
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        if ("distinct".equals(field.getName())) {
            sb.append(" 过滤重复数据");
        } else if ("orderByClause".equals(field.getName())) {
            sb.append(" 排序字段");
        } else if ("oredCriteria".equals(field.getName())) {
            sb.append(" 查询条件");
        }
        if (sb.length() > 0) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" *" + sb.toString());
            field.addJavaDocLine(" */");
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGeneralMethodComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable)
     * 修改mapper接口中的注释
     */
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(" *");
        if (method.isConstructor()) {
            sb.append(" 构造查询条件");
        }
        String method_name = method.getName();
        if ("toString".equals(method_name)
                || "hashCode".equals(method_name)
                || "equals".equals(method_name)) {
            return;
        } else if ("setOrderByClause".equals(method_name)) {
            sb.append(" 设置排序字段");
        } else if ("setDistinct".equals(method_name)) {
            sb.append(" 设置过滤重复数据");
        } else if ("getOredCriteria".equals(method_name)) {
            sb.append(" 获取当前的查询条件实例");
        } else if ("isDistinct".equals(method_name)) {
            sb.append(" 是否过滤重复数据");
        } else if ("getOrderByClause".equals(method_name)) {
            sb.append(" 获取排序字段");
        } else if ("createCriteria".equals(method_name)) {
            sb.append(" 创建一个查询条件");
        } else if ("createCriteriaInternal".equals(method_name)) {
            sb.append(" 内部构建查询条件对象");
        } else if ("clear".equals(method_name)) {
            sb.append(" 清除查询条件");
        } else if ("countByExample".equals(method_name)) {
            sb.append(" 根据指定的条件获取数据库记录数");
        } else if ("deleteByExample".equals(method_name)) {
            sb.append(" 根据指定的条件删除数据库符合条件的记录");
        } else if ("deleteByPrimaryKey".equals(method_name)) {
            sb.append(" 根据主键删除数据库的记录");
        } else if ("insert".equals(method_name)) {
            sb.append(" 新写入数据库记录");
        } else if ("insertSelective".equals(method_name)) {
            sb.append(" 动态字段,写入数据库记录");
        } else if ("selectByExample".equals(method_name)) {
            sb.append(" 根据指定的条件查询符合条件的数据库记录");
        } else if ("selectByPrimaryKey".equals(method_name)) {
            sb.append(" 根据指定主键获取一条数据库记录");
        } else if ("updateByExampleSelective".equals(method_name)) {
            sb.append(" 动态根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByExample".equals(method_name)) {
            sb.append(" 根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKeySelective".equals(method_name)) {
            sb.append(" 动态字段,根据主键来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKey".equals(method_name)) {
            sb.append(" 根据主键来更新符合条件的数据库记录");
        }

        final List<Parameter> parameterList = method.getParameters();
        if (!parameterList.isEmpty()) {
            if ("or".equals(method_name)) {
                sb.append(" 增加或者的查询条件,用于构建或者查询");
            }
        } else if ("or".equals(method_name)) {
            sb.append(" 创建一个新的或者查询条件");
        }

        method.addJavaDocLine("/** ");
        method.addJavaDocLine(sb.toString());

        String paramterName;
        for (Parameter parameter : parameterList) {
            sb.setLength(0);
            sb.append(" * @param "); //$NON-NLS-1$
            paramterName = parameter.getName();
            sb.append(paramterName);
            if ("orderByClause".equals(paramterName)) {
                sb.append(" 排序字段"); //$NON-NLS-1$
            } else if ("distinct".equals(paramterName)) {
                sb.append(" 是否过滤重复数据");
            } else if ("criteria".equals(paramterName)) {
                sb.append(" 过滤条件实例");
            }
            method.addJavaDocLine(sb.toString());
        }

        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     * getter方法
     */
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/** "); //$NON-NLS-1$

        sb.append(" * 获取 "); //$NON-NLS-1$
        if (introspectedColumn.getRemarks() != null
                && !introspectedColumn.getRemarks().equals("")) {
            sb.append(introspectedColumn.getRemarks())
                    .append(" ");
        }
        sb.append(introspectedTable.getFullyQualifiedTable())
                .append('.')
                .append(introspectedColumn.getActualColumnName());

        method.addJavaDocLine(sb.toString());

        sb.setLength(0);

        sb.append(" * @return "); //$NON-NLS-1$
        if (introspectedColumn.getRemarks() != null
                && !introspectedColumn.getRemarks().equals("")) {
            sb.append(introspectedColumn.getRemarks());
        } else {
            sb.append(introspectedTable.getFullyQualifiedTable())
                    .append('.')
                    .append(introspectedColumn.getActualColumnName());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addSetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     * setter方法
     */
    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/** "); //$NON-NLS-1$

        sb.append(" * 设置 ");  //$NON-NLS-1$
        if (introspectedColumn.getRemarks() != null
                && !introspectedColumn.getRemarks().equals("")) {
            sb.append(introspectedColumn.getRemarks())
                    .append(" ");
        }
        sb.append(introspectedTable.getFullyQualifiedTable())
                .append('.')
                .append(introspectedColumn.getActualColumnName());

        method.addJavaDocLine(sb.toString());

        // 参数
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ").append(parm.getName() + " ");
        if (introspectedColumn.getRemarks() != null
                && !introspectedColumn.getRemarks().equals("")) {
            sb.append(introspectedColumn.getRemarks());
        } else {
            sb.append(introspectedTable.getFullyQualifiedTable())
                    .append('.')
                    .append(introspectedColumn.getActualColumnName());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        // add no document level comments by default
        // 删除生成GeneratedCriteria对象的注释信息的注释
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String shortName = innerClass.getType().getShortName();
        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * ")
                .append(introspectedTable.getFullyQualifiedTable().getRemark())
                .append(introspectedTable.getFullyQualifiedTable());
        if ("GeneratedCriteria".equals(shortName)) {
            sb.append("的基本动态SQL对象.");
        } else if ("Criterion".equals(shortName)) {
            sb.append("的动态SQL对象.");
        }

        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable, boolean)
     * 删除生成Criteria对象的注释信息的注释
     */
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        // add no document level comments by default
        // 生成Criteria对象的注释信息的注释
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ").append(introspectedTable.getFullyQualifiedTable().getRemark())
                .append(introspectedTable.getFullyQualifiedTable()).append("的映射实体");

        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" */");
    }
}
