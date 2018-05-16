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
package org.mybatis.generator.api.dom.java;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains a list of Java reserved words.
 *
 * @author Jeff Butler
 *
 */
public class JavaReservedWords {

    private static Set<String> RESERVED_WORDS;

    static {
        String[] words = {"abstract",
                "assert",
                "boolean",
                "break",
                "byte",
                "case",
                "catch",
                "char",
                "class",
                "const",
                "continue",
                "default",
                "do",
                "double",
                "else",
                "enum",
                "extends",
                "final",
                "finally",
                "float",
                "for",
                "goto",
                "if",
                "implements",
                "import",
                "instanceof",
                "int",
                "interface",
                "long",
                "native",
                "new",
                "package",
                "private",
                "protected",
                "public",
                "return",
                "short",
                "static",
                "strictfp",
                "super",
                "switch",
                "synchronized",
                "this",
                "throw",
                "throws",
                "transient",
                "try",
                "void",
                "volatile",
                "while"
        };

        RESERVED_WORDS = new HashSet<String>(words.length);

        for (String word : words) {
            RESERVED_WORDS.add(word);
        }
    }

    /**
     * Utility class - no instances allowed
     */
    private JavaReservedWords() {
    }

    public static boolean containsWord(String word) {
        boolean rc;

        if (word == null) {
            rc = false;
        } else {
            rc = RESERVED_WORDS.contains(word);
        }

        return rc;
    }
}
