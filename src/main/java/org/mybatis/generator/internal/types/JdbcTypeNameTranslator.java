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
package org.mybatis.generator.internal.types;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO - Delete Me
 *
 * @author Jeff Butler
 */
public class JdbcTypeNameTranslator {

    private static Map<Integer, String> typeToName;
    private static Map<String, Integer> nameToType;

    static {
        typeToName = new HashMap<Integer, String>();
        typeToName.put(Types.ARRAY, "ARRAY");
        typeToName.put(Types.BIGINT, "BIGINT");
        typeToName.put(Types.BINARY, "BINARY");
        typeToName.put(Types.BIT, "BIT");
        typeToName.put(Types.BLOB, "BLOB");
        typeToName.put(Types.BOOLEAN, "BOOLEAN");
        typeToName.put(Types.CHAR, "CHAR");
        typeToName.put(Types.CLOB, "CLOB");
        typeToName.put(Types.DATALINK, "DATALINK");
        typeToName.put(Types.DATE, "DATE");
        typeToName.put(Types.DECIMAL, "DECIMAL");
        typeToName.put(Types.DISTINCT, "DISTINCT");
        typeToName.put(Types.DOUBLE, "DOUBLE");
        typeToName.put(Types.FLOAT, "FLOAT");
        typeToName.put(Types.INTEGER, "INTEGER");
        typeToName.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
        typeToName.put(Types.LONGVARBINARY, "LONGVARBINARY");
        typeToName.put(Types.LONGVARCHAR, "LONGVARCHAR");
        typeToName.put(Types.NCHAR, "NCHAR");
        typeToName.put(Types.NCLOB, "NCLOB");
        typeToName.put(Types.NVARCHAR, "NVARCHAR");
        typeToName.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
        typeToName.put(Types.NULL, "NULL");
        typeToName.put(Types.NUMERIC, "NUMERIC");
        typeToName.put(Types.OTHER, "OTHER");
        typeToName.put(Types.REAL, "REAL");
        typeToName.put(Types.REF, "REF");
        typeToName.put(Types.SMALLINT, "SMALLINT");
        typeToName.put(Types.STRUCT, "STRUCT");
        typeToName.put(Types.TIME, "TIME");
        typeToName.put(Types.TIMESTAMP, "TIMESTAMP");
        typeToName.put(Types.TINYINT, "TINYINT");
        typeToName.put(Types.VARBINARY, "VARBINARY");
        typeToName.put(Types.VARCHAR, "VARCHAR");

        nameToType = new HashMap<String, Integer>();
        nameToType.put("ARRAY", Types.ARRAY);
        nameToType.put("BIGINT", Types.BIGINT);
        nameToType.put("BINARY", Types.BINARY);
        nameToType.put("BIT", Types.BIT);
        nameToType.put("BLOB", Types.BLOB);
        nameToType.put("BOOLEAN", Types.BOOLEAN);
        nameToType.put("CHAR", Types.CHAR);
        nameToType.put("CLOB", Types.CLOB);
        nameToType.put("DATALINK", Types.DATALINK);
        nameToType.put("DATE", Types.DATE);
        nameToType.put("DECIMAL", Types.DECIMAL);
        nameToType.put("DISTINCT", Types.DISTINCT);
        nameToType.put("DOUBLE", Types.DOUBLE);
        nameToType.put("FLOAT", Types.FLOAT);
        nameToType.put("INTEGER", Types.INTEGER);
        nameToType.put("JAVA_OBJECT", Types.JAVA_OBJECT);
        nameToType.put("LONGVARBINARY", Types.LONGVARBINARY);
        nameToType.put("LONGVARCHAR", Types.LONGVARCHAR);
        nameToType.put("NCHAR", Types.NCHAR);
        nameToType.put("NCLOB", Types.NCLOB);
        nameToType.put("NVARCHAR", Types.NVARCHAR);
        nameToType.put("LONGNVARCHAR", Types.LONGNVARCHAR);
        nameToType.put("NULL", Types.NULL);
        nameToType.put("NUMERIC", Types.NUMERIC);
        nameToType.put("OTHER", Types.OTHER);
        nameToType.put("REAL", Types.REAL);
        nameToType.put("REF", Types.REF);
        nameToType.put("SMALLINT", Types.SMALLINT);
        nameToType.put("STRUCT", Types.STRUCT);
        nameToType.put("TIME", Types.TIME);
        nameToType.put("TIMESTAMP", Types.TIMESTAMP);
        nameToType.put("TINYINT", Types.TINYINT);
        nameToType.put("VARBINARY", Types.VARBINARY);
        nameToType.put("VARCHAR", Types.VARCHAR);
    }

    /**
     * Utility Class - no instances
     */
    private JdbcTypeNameTranslator() {
        super();
    }

    /**
     * Translates from a java.sql.Types values to the proper iBATIS string
     * representation of the type.
     *
     * @param jdbcType
     *            a value from java.sql.Types
     * @return the iBATIS String representation of a JDBC type
     */
    public static String getJdbcTypeName(int jdbcType) {
        String answer = typeToName.get(jdbcType);
        if (answer == null) {
            answer = "OTHER";
        }

        return answer;
    }

    /**
     * Translates from the string representation of the type to the
     * java.sql.Types value.
     *
     * @param jdbcTypeName
     *            the iBATIS String representation of a JDBC type
     * @return a value from java.sql.Types
     */
    public static int getJdbcType(String jdbcTypeName) {
        Integer answer = nameToType.get(jdbcTypeName);
        if (answer == null) {
            answer = Types.OTHER;
        }

        return answer;
    }
}
