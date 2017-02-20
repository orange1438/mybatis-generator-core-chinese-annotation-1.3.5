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
package org.mybatis.generator.api;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

import java.util.List;
import java.util.Properties;

/**
 * This class includes no-operation methods for almost every method in the
 * Plugin interface. Clients may extend this class to implement some or all of
 * the methods in a plugin.
 * <p>
 * This adapter does not implement the <tt>validate</tt> method - all plugins
 * must perform validation.
 *
 * @author Jeff Butler
 *
 */
public abstract class PluginAdapter implements Plugin {
    protected Context context;
    protected Properties properties;

    public PluginAdapter() {
        properties = new Properties();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return null;
    }

    /**
     * 上下文其余的java文件生成
     *
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return
     */
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
            IntrospectedTable introspectedTable) {
        return null;
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        return null;
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
            IntrospectedTable introspectedTable) {
        return null;
    }

    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                           TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    /**
     * 插入方法生成
     *
     * @param method            the generated insert method
     * @param interfaze         the partially implemented client interface. You can add
     *                          additional imported classes to the interface if
     *                          necessary.
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return
     */
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                               IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientInsertMethodGenerated(Method method,
                                               TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    /**
     * 生成的Mapper接口
     *
     * @param interfaze         the generated interface if any, may be null
     * @param topLevelClass     the generated implementation class if any, may be null
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return
     */
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                           TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
            Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    /**
     * 生成基础实体类
     * 在MBG生成record类的时候调用的插件的方法，在该方法中，传入两个参数
     *
     * @param topLevelClass     该类的实例就是表示当前正在生成的类的DOM结构
     * @param introspectedTable 代表的runtime环境，包含了所有context中的配置，一般从这个类中去查询生成对象的一些规则；
     * @return
     */
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       Plugin.ModelClassType modelClassType) {
        return true;
    }

    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              Plugin.ModelClassType modelClassType) {
        return true;
    }

    /**
     * 生成实体类注解KEY对象
     * @param topLevelClass
     *            the generated primary key class
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return
     */
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        return true;
    }

    /**
     * 生成带BLOB字段的对象
     * 在MBG生成key Class类的时候调用的插件的方法
     * @param topLevelClass
     *            the generated record with BLOBs class
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return
     */
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              Plugin.ModelClassType modelClassType) {
        return true;
    }

    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapCountByExampleElementGenerated(XmlElement element,
                                                        IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapDocumentGenerated(Document document,
                                           IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                                   IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapInsertElementGenerated(XmlElement element,
                                                IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientInsertSelectiveMethodGenerated(Method method,
                                                        Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientInsertSelectiveMethodGenerated(Method method,
                                                        TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public void initialized(IntrospectedTable introspectedTable) {
    }

    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element,
                                                        IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapBlobColumnListElementGenerated(XmlElement element,
                                                        IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerGenerated(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerApplyWhereMethodGenerated(Method method,
                                                     TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerCountByExampleMethodGenerated(Method method,
                                                         TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerDeleteByExampleMethodGenerated(Method method,
                                                          TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerInsertSelectiveMethodGenerated(Method method,
                                                          TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerSelectByExampleWithBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerUpdateByExampleSelectiveMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerUpdateByExampleWithBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectAllMethodGenerated(Method method,
                                                  Interface interfaze, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean clientSelectAllMethodGenerated(Method method,
                                                  TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean sqlMapSelectAllElementGenerated(XmlElement element,
                                                   IntrospectedTable introspectedTable) {
        return true;
    }
}
