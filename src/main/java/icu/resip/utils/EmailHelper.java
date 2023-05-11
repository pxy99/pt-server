package icu.resip.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * 发送邮件工具类
 * @Author Peng
 * @Date 2021/11/18 23:58
 * @Version 1.0
 */
public final class EmailHelper {

    /**
     * 将ftl邮件模板静态化为字符串
     * @param ftlName ftl模板名称
     * @param map 需要替换的数据
     * @return 整个模板的字符串格式
     */
    public static String ftlToString(String ftlName, Map<String, Object> map) {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        URL url = Thread.currentThread().getContextClassLoader().getResource("templates");
        if (url == null) {
            return null;
        }
        String path = url.getPath();
        try {
            configuration.setDirectoryForTemplateLoading(new File(path));
            //设置字符集
            configuration.setDefaultEncoding("utf-8");
            //加载模板
            Template template = configuration.getTemplate(ftlName);
            //替换数据，静态化成字符串
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException | TemplateException e) {
            return null;
        }
    }

}
