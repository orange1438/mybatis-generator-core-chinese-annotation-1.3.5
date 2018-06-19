package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * @author orange1438
 * github: github.com/orange1438
 */
public class UpdateBatchByPrimaryKeySelectiveElementGenerator extends
        AbstractXmlElementGenerator {

    public UpdateBatchByPrimaryKeySelectiveElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");

        answer.addAttribute(new Attribute("id", introspectedTable.getUpdateBatchSelectiveStatementId()));

        answer.addAttribute(new Attribute("parameterType", "java.util.List"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        sb.append("update ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement trimSetElement = new XmlElement("trim");
        trimSetElement.addAttribute(new Attribute("prefix", "set"));
        trimSetElement.addAttribute(new Attribute("suffixOverrides", ","));


        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns())) {
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getActualColumnName(introspectedColumn));
            sb.append(" =case ");
            sb.append(MyBatis3FormattingUtilities.getActualColumnName(introspectedTable.getPrimaryKeyColumns().get(0)));


            XmlElement trimElement = new XmlElement("trim");
            trimElement.addAttribute(new Attribute("prefix", sb.toString()));
            trimElement.addAttribute(new Attribute("suffix", "end,"));
            trimSetElement.addElement(trimElement);

            XmlElement forEachElement = new XmlElement("foreach");
            forEachElement.addAttribute(new Attribute("collection", "list"));
            forEachElement.addAttribute(new Attribute("item", "item"));
            forEachElement.addAttribute(new Attribute("index", "index"));
            forEachElement.addAttribute(new Attribute("separator", " "));
            trimElement.addElement(forEachElement);


            XmlElement ifElement1 = new XmlElement("if");
            sb.setLength(0);
            sb.append("item.");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null ");
            ifElement1.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append("when ");

            sb.append(" #{item.");
            sb.append(MyBatis3FormattingUtilities.getParameterField(introspectedTable.getPrimaryKeyColumns().get(0)));
            sb.append("} then #{item.");
            sb.append(MyBatis3FormattingUtilities.getParameterField(introspectedColumn));
            sb.append("}");
            ifElement1.addElement(new TextElement(sb.toString()));
            forEachElement.addElement(ifElement1);

            XmlElement ifElement2 = new XmlElement("if");
            sb.setLength(0);
            sb.append("item.");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" == null ");
            ifElement2.addAttribute(new Attribute("test", sb.toString()));
            sb.setLength(0);
            sb.append("when ");
            sb.append(" #{item.");
            sb.append(MyBatis3FormattingUtilities.getParameterField(introspectedTable.getPrimaryKeyColumns().get(0)));
            sb.append("} then ");
            sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
            sb.append(".");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            ifElement2.addElement(new TextElement(sb.toString()));
            forEachElement.addElement(ifElement2);

        }
        answer.addElement(trimSetElement);

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {

                and = true;
            }
            sb.append("where ");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" in ");
            answer.addElement(new TextElement(sb.toString()));
            XmlElement forEachIdElement = new XmlElement("foreach");
            forEachIdElement.addAttribute(new Attribute("collection", "list"));
            forEachIdElement.addAttribute(new Attribute("item", "item"));
            forEachIdElement.addAttribute(new Attribute("index", "index"));
            forEachIdElement.addAttribute(new Attribute("separator", ","));
            forEachIdElement.addAttribute(new Attribute("open", "("));
            forEachIdElement.addAttribute(new Attribute("close", ")"));
            answer.addElement(forEachIdElement);
            sb.setLength(0);
            sb.append(" #{item.");
            sb.append(MyBatis3FormattingUtilities.getParameterField(introspectedColumn));
            sb.append("} ");
            forEachIdElement.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
