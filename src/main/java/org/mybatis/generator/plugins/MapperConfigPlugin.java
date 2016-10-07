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

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * This plugin generates a MapperConfig file containing mapper entries for SQL
 * maps generated for MyBatis3. This demonstrates hooking into the code
 * generation lifecycle and generating additional XML files.
 * <p>
 * This plugin accepts three properties:
 * <ul>
 * <li><tt>fileName</tt> (optional) the name of the generated file. this
 * defaults to "SqlMapConfig.xml" if not specified.</li>
 * <li><tt>targetPackage</tt> (required) the name of the package where the file
 * should be placed. Specified like "com.mycompany.sql".</li>
 * <li><tt>targetProject</tt> (required) the name of the project where the file
 * should be placed.</li>
 * </ul>
 * <p>
 * Note: targetPackage and targetProject follow the same rules as the
 * targetPackage and targetProject values on the sqlMapGenerator configuration
 * element.
 * <p>
 * 比较有用的一个插件，可以用来帮助生成一个默认的MapperConfig.xml文件骨架，在这个骨架文件中完成了本次生成的mapper.xml文件的配置；
 * 该插件支持的配置属性有：
 * fileName：配置文件名称，默认为MapperConfig.xml；
 * targetPackage：配置文件所在的包，同MBG配置文件中的所有targetPackage配置；
 * targetProject：配置文件所在目录，同MBG配置文件中的所有targetProject配置；
 *
 * @author Jeff Butler
 */
public class MapperConfigPlugin extends PluginAdapter {

    private List<String> mapperFiles;

    public MapperConfigPlugin() {
        mapperFiles = new ArrayList<String>();
    }

    /**
     * validate方法是所有的plugin都必须实现的方法
     */
    public boolean validate(List<String> warnings) {
        boolean valid = true;

        //stringHasValue方法是通过静态引入工具类org.mybatis.generator.internal.util.StringUtility的；
        //该方法用于判断传入的参数中是否含有targetProject这个参数；
        //这里要注意两个点，第一，我们在扩展或者使用别人的框架的时候，比如stringHasValue这种方法，我们完全可以自己写一个hasLength方法，
        //但是，使用框架中已经存在的API来完成这些功能，是一个扩展框架的一个良好的实践，这可以保证框架在API级别的一致性；
        //第二，properties属性是Plugin在创建的时候，通过setProperties方法传入的，是一个Properties类型数据；
        if (!stringHasValue(properties
                .getProperty("targetProject"))) { //$NON-NLS-1$

            //如果没有传入必填的参数，就把警告信息添加到传入的warnings列表中，该列表的内容会在MBG运行过程中统一日志；
            //这里需要注意的是getString方法，该方法是通过静态引入org.mybatis.generator.internal.util.messages.Messages
            //这个Messages类是MBG对国际化消息的一个封装，在后面扩展时候会讲到MBG的代码结构；
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetProject")); //$NON-NLS-1$
            valid = false;
        }

        //同理，判断是否传入了targetPackage参数
        if (!stringHasValue(properties
                .getProperty("targetPackage"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetPackage")); //$NON-NLS-1$
            valid = false;
        }

        return valid;
    }

    /**
     * 为MBG添加额外需要生成的文件
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        //创建一个XML文档，注意这个Document不是JAVA DOM的，而是org.mybatis.generator.api.dom.xml.Document
        //在这里传入了两个静态常量，这两个常量就是mybatis配置文件需要用到的DTD，
        //在XmlConstants里面还有很多常量，比如MYBATIS3_MAPPER_SYSTEM_ID和MYBATIS3_MAPPER_PUBLIC_ID（看名字应该知道是什么内容吧~）
        Document document = new Document(
                XmlConstants.MYBATIS3_MAPPER_CONFIG_PUBLIC_ID,
                XmlConstants.MYBATIS3_MAPPER_CONFIG_SYSTEM_ID);

        //接着创建根目录，<configuration>，和JavaDOM基本一样，就不啰嗦了；
        XmlElement root = new XmlElement("configuration"); //$NON-NLS-1$
        document.setRootElement(root);

        //添加注释源码已删，因为这里做的有点不太规范，最好还是使用MBG提供的context.getCommentGenerator的addComment(XmlElement xmlElement)方法来统一生成注释

        //创建mappers节点；
        XmlElement mappers = new XmlElement("mappers"); //$NON-NLS-1$
        root.addElement(mappers);

        //准备根据搜集到的本次生成的mapper.xml文件，为mappers生成mapper子元素
        XmlElement mapper;

        //为每一个mapper.xml文件生成一个对应的mapper子元素；从这里就可以明确的看出，在mapperFiles集合中保存的确实是mapper.xml文件的路径；
        for (String mapperFile : mapperFiles) {
            mapper = new XmlElement("mapper"); //$NON-NLS-1$
            mapper.addAttribute(new Attribute("resource", mapperFile)); //$NON-NLS-1$
            mappers.addElement(mapper);
        }

        //信息量非常大的一句代码，通过这句代码可以看出：
        //1，MBG使用GeneratedXmlFile对象来包装一个要生成的XML文件的所有相关内容；
        //2，该对象的构造方法包含了所有需要的信息
        //3，第一个参数，是该XML文件的内容，即Document；
        //4，第二个参数，是该XML文件的文件名，可以很清楚的看到，先得到fileName参数，否则使用默认的MapperConfig.xml命名（所以，后缀名是要自己传给MBG的）
        //5，第三个参数和第四个参数，分别是生成XML文件的targetPackage和targetProject；所以，可以看到MBG把文件的具体生成过程完全包装，只需要我们提供package和project即可；
        //6，第四个参数代表是否合并，
        //7，最后一个参数是提供一个XML文件格式化工具，直接使用上下文的xmlFormatter即可（这个是可以在<context>元素中配置的哦~~）
        GeneratedXmlFile gxf = new GeneratedXmlFile(document, properties
                .getProperty("fileName", "MapperConfig.xml"), //$NON-NLS-1$ //$NON-NLS-2$
                properties.getProperty("targetPackage"), //$NON-NLS-1$
                properties.getProperty("targetProject"), //$NON-NLS-1$
                false, context.getXmlFormatter());

        //最后返回要生成的这个文件，交给MBG去生成；
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>(1);
        answer.add(gxf);

        return answer;
    }

    /**
     * sqlMapGenerated方法，是在本次context中，生成每一个（注意是每一个）mapper.xml文件之后都会回调的方法；
     * 第一个参数GeneratedXmlFile即本次生成的mapper.xml文件对应的XML文件封装对象；
     * This method collects the name of every SqlMap file generated in
     * this context.
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                                   IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        //得到目标package；
        sb.append(sqlMap.getTargetPackage());

        //添加一个.然后把所有的.替换成/，就变成了mapper.xml文件的目录（原来并没有方法直接得到，还是要自己通过package去替换）
        sb.append('.');
        String temp = sb.toString();
        sb.setLength(0);
        sb.append(temp.replace('.', '/'));

        //接着拼上xml文件的文件名（还记得文件名是包含了后缀的吧），就创建好了这个mapper.xml文件的路径了
        sb.append(sqlMap.getFileName());

        //再添加到mapperFiles中
        mapperFiles.add(sb.toString());

        return true;
    }
}
