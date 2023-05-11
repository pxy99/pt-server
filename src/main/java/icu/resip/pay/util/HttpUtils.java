package icu.resip.pay.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 封装http get post
 */
public class HttpUtils {

    /**
     * 封装post请求
     * @return 响应字符串
     */
    public static String doPost(String url, String data) {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .postDataCharset("UTF-8")
                    .requestBody(data)
                    .execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponseStr(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
