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

import org.mybatis.generator.api.DAOMethodNameCalculator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.rules.Rules;

/**
 * @author Jeff Butler
 *
 */
public class ExtendedDAOMethodNameCalculator implements DAOMethodNameCalculator {

    /**
     *
     */
    public ExtendedDAOMethodNameCalculator() {
        super();
    }

    public String getInsertMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());

        return sb.toString();
    }

    /**
     * 1. if this will be the only updateByPrimaryKey, then the result should be
     * updateByPrimaryKey. 2. If the other method is enabled, but there are
     * seperate base and blob classes, then the method name should be
     * updateByPrimaryKey 3. Else the method name should be
     * updateByPrimaryKeyWithoutBLOBs
     */
    public String getUpdateByPrimaryKeyWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        sb.append("update");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByPrimaryKeyWithBLOBs()) {
            sb.append("ByPrimaryKey");
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ByPrimaryKey");
        } else {
            sb.append("ByPrimaryKeyWithoutBLOBs");
        }

        return sb.toString();
    }

    /**
     * 1. if this will be the only updateByPrimaryKey, then the result should be
     * updateByPrimaryKey. 2. If the other method is enabled, but there are
     * seperate base and blob classes, then the method name should be
     * updateByPrimaryKey 3. Else the method name should be
     * updateByPrimaryKeyWithBLOBs
     */
    public String getUpdateByPrimaryKeyWithBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByPrimaryKeyWithoutBLOBs()) {
            sb.append("ByPrimaryKey");
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ByPrimaryKey");
        } else {
            sb.append("ByPrimaryKeyWithBLOBs");
        }

        return sb.toString();
    }

    public String getDeleteByExampleMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByExample");

        return sb.toString();
    }

    public String getDeleteByPrimaryKeyMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByPrimaryKey");

        return sb.toString();
    }

    /**
     * 1. if this will be the only selectByExample, then the result should be
     * selectByExample. 2. Else the method name should be
     * selectByExampleWithoutBLOBs
     */
    public String getSelectByExampleWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByExample");

        Rules rules = introspectedTable.getRules();

        if (rules.generateSelectByExampleWithBLOBs()) {
            sb.append("WithoutBLOBs");
        }

        return sb.toString();
    }

    /**
     * 1. if this will be the only selectByExample, then the result should be
     * selectByExample. 2. Else the method name should be
     * selectByExampleWithBLOBs
     */
    public String getSelectByExampleWithBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByExample");

        Rules rules = introspectedTable.getRules();

        if (rules.generateSelectByExampleWithoutBLOBs()) {
            sb.append("WithBLOBs");
        }

        return sb.toString();
    }

    public String getSelectByPrimaryKeyMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByPrimaryKey");

        return sb.toString();
    }

    public String getUpdateByPrimaryKeySelectiveMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByPrimaryKeySelective");

        return sb.toString();
    }

    public String getCountByExampleMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("count");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByExample");

        return sb.toString();
    }

    public String getUpdateByExampleSelectiveMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("ByExampleSelective");

        return sb.toString();
    }

    public String getUpdateByExampleWithBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByExampleWithoutBLOBs()) {
            sb.append("ByExample");
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ByExample");
        } else {
            sb.append("ByExampleWithBLOBs");
        }

        return sb.toString();
    }

    public String getUpdateByExampleWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        sb.append("update");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByExampleWithBLOBs()) {
            sb.append("ByExample");
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ByExample");
        } else {
            sb.append("ByExampleWithoutBLOBs");
        }

        return sb.toString();
    }

    public String getInsertSelectiveMethodName(
            IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert");
        sb.append(introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName());
        sb.append("Selective");

        return sb.toString();
    }
}
