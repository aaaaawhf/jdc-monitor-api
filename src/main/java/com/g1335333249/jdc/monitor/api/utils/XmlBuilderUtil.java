package com.g1335333249.jdc.monitor.api.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author guanpeng
 * @description XML转化
 * @date 2020/11/19 4:02 下午
 * @since
 */
public class XmlBuilderUtil {
    /**
     * 将XML转为指定的POJO对象
     *
     * @param clazz  需要转换的类
     * @param xmlStr xml数据
     * @return
     */
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws Exception {
        Object xmlObject;
        Reader reader;
        //利用JAXBContext将类转为一个实例
        JAXBContext context = JAXBContext.newInstance(clazz);
        //XMl 转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);
        reader.close();
        return xmlObject;

    }
}
