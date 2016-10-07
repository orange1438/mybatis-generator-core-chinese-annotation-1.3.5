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
package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

public class ToStringPlugin extends PluginAdapter {

    private boolean useToStringFromRoot;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        useToStringFromRoot = isTrue(properties.getProperty("useToStringFromRoot"));
    }

    /**
     * 实现了validate方法，validate方法是所有的plugin都必须实现的方法
     *
     * @param warnings add strings to this list to specify warnings. For example, if
     *                 the plugin is invalid, you should specify why. Warnings are
     *                 reported to users after the completion of the run.
     * @return
     */
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 在MBG生成record类的时候调用的插件的方法，在该方法中，传入两个参数
     *
     * @param topLevelClass     该类的实例就是表示当前正在生成的类的DOM结构
     * @param introspectedTable 代表的runtime环境，包含了所有context中的配置，一般从这个类中去查询生成对象的一些规则；
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);

        /* 这个方法总是返回了true，让后面的实现了相同方法的插件能正常运行； */
        return true;
    }

    /**
     * 在MBG生成key Class类的时候调用的插件的方法
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    /**
     * 具体生成toString方法
     *
     * @param introspectedTable
     * @param topLevelClass
     */
    private void generateToString(IntrospectedTable introspectedTable,
                                  TopLevelClass topLevelClass) {
        //首先创建一个Method对象，注意，这个Method是org.mybatis.generator.api.dom.java.Method，
        //这个Method是MBG中对对象DOM的一个抽象；因为我们要添加方法，所以先创建一个；
        Method method = new Method();

        //设置这个方法的可见性为public，代码已经在一步一步构建这个方法了
        method.setVisibility(JavaVisibility.PUBLIC);

        //设置方法的返回类型，这里注意一下的就是，returnType是一个FullyQualifiedJavaType；
        //这个FullyQualifiedJavaType是MGB中对Java中的类型的一个DOM封装，这个类在整个MBG中大量使用；
        //FullyQualifiedJavaType提供了几个静态的方法，比如getStringInstance，就直接返回了一个对String类型的封装；
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());

        //设置方法的名称，至此，方法签名已经装配完成；
        method.setName("toString"); //$NON-NLS-1$

        //判断当前MBG运行的环境是否支持Java5（这里就可以看出来IntrospectedTable类的作用了，主要是查询生成环境的作用）
        if (introspectedTable.isJava5Targeted()) {
            //如果支持Java5，就在方法上面生成一个@Override标签；
            method.addAnnotation("@Override"); //$NON-NLS-1$
        }

        //访问上下文对象（这个context对象是在PluginAdapter初始化完成后，通过setContext方法设置进去的，
        //通过getCommentGenerator方法得到注释生成器，并调用addGeneralMethodComment为当前生成的方法添加注释；
        //因为我们没有提供自己的注释生成器，所以默认的注释生成器只是说明方法是通过MBG生成的，对应的是哪个表而已；
        //这句代码其实非常有意义，通过这句代码，我们基本就可能了解到MBG中对于方法注释的生成方式了；
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        //OK，调用addBodyLine开始为方法添加代码了
        //可以看到，确实，只是简单的把要生成的代码通过String拼装到了method的body中而已；
        //想到了什么？确实，我想到了Servelt的输出方法。MBG默认的方法体具体的实现，就是像Servlet那样通过String输出的；
        //所以，这才会为我们后面准备用Velocity来重写MBG提供了依据，我们只是给MBG添加一个MVC的概念；
        method.addBodyLine("StringBuilder sb = new StringBuilder();"); //$NON-NLS-1$
        method.addBodyLine("sb.append(getClass().getSimpleName());"); //$NON-NLS-1$
        method.addBodyLine("sb.append(\" [\");"); //$NON-NLS-1$
        method.addBodyLine("sb.append(\"Hash = \").append(hashCode());"); //$NON-NLS-1$

        //接下来要准备拼装类的字段了；
        StringBuilder sb = new StringBuilder();

        //通过topLevelClass得到当前类的所有字段，注意，这里的Field是org.mybatis.generator.api.dom.java.Field
        //这个Field是MBG对字段的一个DOM封装
        for (Field field : topLevelClass.getFields()) {

            //得到字段的名称；
            String property = field.getName();

            //重置StringBuilder；
            sb.setLength(0);

            //添加字段的输出代码；
            sb.append("sb.append(\"").append(", ").append(property) //$NON-NLS-1$ //$NON-NLS-2$
                    .append("=\")").append(".append(").append(property) //$NON-NLS-1$ //$NON-NLS-2$
                    .append(");"); //$NON-NLS-1$

            //把这个字段的toString输出到代码中；所以才看到我们最后生成的代码结果中，每一个字段在toString方法中各占一行；
            method.addBodyLine(sb.toString());
        }

        method.addBodyLine("sb.append(\"]\");"); //$NON-NLS-1$
        if (useToStringFromRoot && topLevelClass.getSuperClass() != null) {
            method.addBodyLine("sb.append(\", from super class \");"); //$NON-NLS-1$
            method.addBodyLine("sb.append(super.toString());"); //$NON-NLS-1$
        }
        method.addBodyLine("return sb.toString();"); //$NON-NLS-1$

        //把拼装好的方法DOM添加到topLevelClass中，完成方法添加；
        topLevelClass.addMethod(method);
    }
}
