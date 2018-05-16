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
public class DefaultDAOMethodNameCalculator implements DAOMethodNameCalculator {

    /**
     *
     */
    public DefaultDAOMethodNameCalculator() {
        super();
    }

    public String getInsertMethodName(IntrospectedTable introspectedTable) {
        return "insert";
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
        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByPrimaryKeyWithBLOBs()) {
            return "updateByPrimaryKey";
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByPrimaryKey";
        } else {
            return "updateByPrimaryKeyWithoutBLOBs";
        }
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
        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByPrimaryKeyWithoutBLOBs()) {
            return "updateByPrimaryKey";
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByPrimaryKey";
        } else {
            return "updateByPrimaryKeyWithBLOBs";
        }
    }

    public String getDeleteByExampleMethodName(
            IntrospectedTable introspectedTable) {
        return "deleteByExample";
    }

    public String getDeleteByPrimaryKeyMethodName(
            IntrospectedTable introspectedTable) {
        return "deleteByPrimaryKey";
    }

    /**
     * 1. if this will be the only selectByExample, then the result should be
     * selectByExample. 2. Else the method name should be
     * selectByExampleWithoutBLOBs
     */
    public String getSelectByExampleWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        Rules rules = introspectedTable.getRules();

        if (!rules.generateSelectByExampleWithBLOBs()) {
            return "selectByExample";
        } else {
            return "selectByExampleWithoutBLOBs";
        }
    }

    /**
     * 1. if this will be the only selectByExample, then the result should be
     * selectByExample. 2. Else the method name should be
     * selectByExampleWithBLOBs
     */
    public String getSelectByExampleWithBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        Rules rules = introspectedTable.getRules();

        if (!rules.generateSelectByExampleWithoutBLOBs()) {
            return "selectByExample";
        } else {
            return "selectByExampleWithBLOBs";
        }
    }

    public String getSelectByPrimaryKeyMethodName(
            IntrospectedTable introspectedTable) {
        return "selectByPrimaryKey";
    }

    public String getUpdateByPrimaryKeySelectiveMethodName(
            IntrospectedTable introspectedTable) {
        return "updateByPrimaryKeySelective";
    }

    public String getCountByExampleMethodName(
            IntrospectedTable introspectedTable) {
        return "countByExample";
    }

    public String getUpdateByExampleSelectiveMethodName(
            IntrospectedTable introspectedTable) {
        return "updateByExampleSelective";
    }

    public String getUpdateByExampleWithBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByExampleWithoutBLOBs()) {
            return "updateByExample";
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByExample";
        } else {
            return "updateByExampleWithBLOBs";
        }
    }

    public String getUpdateByExampleWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable) {
        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByExampleWithBLOBs()) {
            return "updateByExample";
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByExample";
        } else {
            return "updateByExampleWithoutBLOBs";
        }
    }

    public String getInsertSelectiveMethodName(
            IntrospectedTable introspectedTable) {
        return "insertSelective";
    }
}
