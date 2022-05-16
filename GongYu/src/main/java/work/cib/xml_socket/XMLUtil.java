package work.cib.xml_socket;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import work.cib.xml_socket.JSONUtils;

public class XMLUtil {
    private static Logger log = LoggerFactory.getLogger(XMLUtil.class);

    /**
     * 对象转换为报文
     *
     * @param clazz 报文对应的VO类
     * @param obj   要转为报文的对象
     */
    public static String pack(Class<?> clazz, Object obj) {
        try {
            // jaxb提供与xml/java绑定信息相关的抽象方法
            // createMarshaller编组
            // createUnmarshaller解组
            // createValidator验证(已deprecated)
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            StringWriter sw = new StringWriter();
            marshaller.marshal(obj, sw);
            return sw.toString();
        } catch (JAXBException e) {
            log.error("对象转换为XML失败:", e);
        }
        return null;
    }

    /**
     * 报文解析为对象
     *
     * @param clazz  报文对应的VO类
     * @param xmlStr XML字符串报文
     * @return 解析后的报文对象
     */
    public static Object unpack(Class<?> clazz, String xmlStr) throws RuntimeException {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            XMLInputFactory xif = XMLInputFactory.newInstance();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            System.out.println("xmlStr===" + xmlStr);
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlStr));
            System.out.println("xsr====" + xsr);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object obj = unmarshaller.unmarshal(xsr);
            log.info("报文解析为对象成功【{}】", JSONUtils.beanToJson(obj));
            return obj;
        } catch (JAXBException e) {
            log.error("XML解析为对象失败:", e);
        } catch (XMLStreamException e) {
            log.error("XML解析为对象失败:", e);
        }
        return null;
    }

}
