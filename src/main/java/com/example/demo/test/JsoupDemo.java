package com.example.demo.test;/**
 * @Auther: He
 * @Date: 2019/4/12 16:14
 * @Description:
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *@ClassName JsoupDemo
 *@Author JsoupDemo
 *@Date 2019/4/12 16:14
 *@Version 1.0
 **/
public class JsoupDemo {

    /**
     * Jsoup解析HTML
     * @author He
     * @date 2019/4/12 16:18
     * @param args
     * @return void
     *
     **/
    public static void main(String[] args) {
        String str = "<html>\n" +
                "\t<body>\n" +
                "\t\t<h>已有关联头寸，请及时匹配！</h>\n" +
                "\t\t<div id=\"businessId\" style=\"display:none;\">${businessId}</div>\n" +
                "\t\t<div id=\"businessType\" style=\"display:none;\">${businessType}</div>\n" +
                "\t</body>\n" +
                "</html>\n";
        Document document = Jsoup.parse(str);
        System.out.println("businessId:" + document.getElementById("businessId").text());
        System.out.println("businessType:" + document.getElementById("businessType").text());
    }
}
