package com.example.utilsdemo.other;

import gov.nist.javax.sdp.fields.SDPField;
import gov.nist.javax.sdp.parser.ParserFactory;
import gov.nist.javax.sdp.parser.SDPParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class TestSetAccessible {

    public static void main(String[] args) throws Exception{
        ParserFactory parserFactory = new ParserFactory();
        Class<?> clazz = ParserFactory.class;

        // 获得指定类的属性
        Field field = clazz.getDeclaredField("parserTable");

        Class c = Class.forName("gov.nist.javax.sdp.parser.ParserFactory");
        Method m = c.getDeclaredMethod("getParser", String.class);
        m.setAccessible(true);

        field.setAccessible(true);
        // 更改私有属性的值
        Hashtable parserTable = new Hashtable();
        parserTable.put("a", m.invoke(c.newInstance(), "AttributeFieldParser"));
        parserTable.put("b", m.invoke(c.newInstance(), "BandwidthFieldParser"));
        parserTable.put("c", m.invoke(c.newInstance(), "ConnectionFieldParser"));
        parserTable.put("e", m.invoke(c.newInstance(), "EmailFieldParser"));
        parserTable.put("i", m.invoke(c.newInstance(), "InformationFieldParser"));
        parserTable.put("k", m.invoke(c.newInstance(), "KeyFieldParser"));
        parserTable.put("m", m.invoke(c.newInstance(), "MediaFieldParser"));
        parserTable.put("o", m.invoke(c.newInstance(), "OriginFieldParser"));
        parserTable.put("p", m.invoke(c.newInstance(), "PhoneFieldParser"));
        parserTable.put("v", m.invoke(c.newInstance(), "ProtoVersionFieldParser"));
        parserTable.put("r", m.invoke(c.newInstance(), "RepeatFieldParser"));
        parserTable.put("s", m.invoke(c.newInstance(), "SessionNameFieldParser"));
        parserTable.put("t", m.invoke(c.newInstance(), "TimeFieldParser"));
        parserTable.put("u", m.invoke(c.newInstance(), "URIFieldParser"));
        parserTable.put("z", m.invoke(c.newInstance(), "ZoneFieldParser"));
        parserTable.put("y", m.invoke(c.newInstance(), "y"));
        parserTable.put("f", m.invoke(c.newInstance(), "f"));

        field.set(parserFactory, parserTable);
//        System.out.println(field.get(stu).toString());
    }

}
