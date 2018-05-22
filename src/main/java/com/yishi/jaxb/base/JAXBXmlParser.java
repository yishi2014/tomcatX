package com.yishi.jaxb.base;

import org.dom4j.DocumentException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Lustin on 2017/9/15.
 */
public class JAXBXmlParser {
    /**
     * JavaBean转换成xml
     * 默认编码UTF-8
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    public static void main(String[] args) throws DocumentException {
//        SetBillSellInfo cobj=converyToJavaBean("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<ROOT>\n" +
//                "    <REQUEST>\n" +
//                "        <PARAM NAME=\"args\">\n" +
//                "            <ENTERNAME_G>浙江省财政厅（财政执收）</ENTERNAME_G>\n" +
//                "            <F_ENTEGUID_G>33000021356</F_ENTEGUID_G>\n" +
//                "            <ENTERNAME_S>浙江省财政厅（财政执收）</ENTERNAME_S>\n" +
//                "            <F_ENTEGUID_S>3300002017</F_ENTEGUID_S>\n" +
//                "            <USERNAME_S>91测试系统管理员</USERNAME_S>\n" +
//                "            <USERGUID_S>1</USERGUID_S>\n" +
//                "            <NOTICENO>pjcs_0000003859</NOTICENO>\n" +
//                "            <REGISTERDATE>20170915</REGISTERDATE>\n" +
//                "            <REMARK></REMARK>\n" +
//                "            <SUMMONEY>0.24</SUMMONEY>\n" +
//                "            <F_REGICODE>330000</F_REGICODE>\n" +
//                "            <OPERATIONDATE>20170915</OPERATIONDATE>\n" +
//                "            <REGISTERUSERGUID>1</REGISTERUSERGUID>\n" +
//                "            <REGISTERUSERNAME>91测试系统管理员</REGISTERUSERNAME>\n" +
//                "            <IFBALANCE>1</IFBALANCE>\n" +
//                "\n" +
//                "            <DETAIL>\n" +
//                "                <F_BUMAGUID>35209</F_BUMAGUID>\n" +
//                "                <F_REGICODE>330000</F_REGICODE>\n" +
//                "                <F_SUBJECTNO>wypjkc</F_SUBJECTNO>\n" +
//                "                <F_ENTEGUID>33000021356</F_ENTEGUID>\n" +
//                "                <USERGUID>1</USERGUID>\n" +
//                "                <USERNAME>91测试系统管理员</USERNAME>\n" +
//                "                <F_BITYCODE>102</F_BITYCODE>\n" +
//                "                <BILLTYPE>[102]浙江省政府非税收入统一票据</BILLTYPE>\n" +
//                "                <STARTNO>0910004768</STARTNO>\n" +
//                "                <ENDNO>0910004768</ENDNO>\n" +
//                "                <SHEETS>1</SHEETS>\n" +
//                "                <BOOKSUM>1</BOOKSUM>\n" +
//                "                <PRESSPRICE>0.0</PRESSPRICE>\n" +
//                "                <RETAILPRICE>0.24</RETAILPRICE>\n" +
//                "                <WHOLESALEPRICE>0.28</WHOLESALEPRICE>\n" +
//                "                <PRICE>0.24</PRICE>\n" +
//                "                <PERMONEY>0.24</PERMONEY>\n" +
//                "                <BITYNAME>[102]浙江省政府非税收入统一票据</BITYNAME>\n" +
//                "\n" +
//                "            </DETAIL>\n" +
//                "        </PARAM>\n" +
//                "    </REQUEST>\n" +
//                "</ROOT>",SetBillSellInfo.class);
//        System.out.println(convertToXml(cobj));
//        GetBillTypeInfo info=new GetBillTypeInfo();
//        Success success=new Success();
//        success.setCode("00");
//        success.setMsg("成功");
//        GetBillTypeInfo_BillType billTypeInfo_billType=new GetBillTypeInfo_BillType();
//        info.setResultCode(success);
//        info.addBillType(billTypeInfo_billType);
//        info.addBillType(billTypeInfo_billType);
//        info.addBillType(billTypeInfo_billType);
//        System.out.println(convertToXml(info));
//        SetBillApplyPass applys=new SetBillApplyPass().init();
//        applys.getApplys().add(new SetBillApplyPass_BillApply());
//        System.out.println(convertToXml(applys));
//          SetBillApplyPass applys=converyToJavaBean("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                  "<ROOT>\n" +
//                  "    <REQUEST>\n" +
//                  "        <DETAIL>\n" +
//                  "            <F_REGICODE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <APPLYDATE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <F_APPLYENTEGUID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <F_REPORTENTEGUID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <F_BITYCODE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <SHEETS xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <PREDATE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <BIAPSTATUS xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "        </DETAIL>\n" +
//                  "        <DETAIL>\n" +
//                  "            <F_REGICODE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <APPLYDATE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <F_APPLYENTEGUID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <F_REPORTENTEGUID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <F_BITYCODE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <SHEETS xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <PREDATE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "            <BIAPSTATUS xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
//                  "        </DETAIL>\n" +
//                  "    </REQUEST>\n" +
//                  "</ROOT>",SetBillApplyPass.class);
//        System.out.println(applys.getApplys().size());
    }


    /**
     * JavaBean转换成xml
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * xml转换成JavaBean
     * @param xml
     * @param c
     * @return
     */
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            if("".equals(xml)){
                return t;
            }
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

}
