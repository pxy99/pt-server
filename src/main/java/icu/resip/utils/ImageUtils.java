package icu.resip.utils;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 图片上传工具类
 * @Author Peng
 * @Date 2022/3/28
 */
public class ImageUtils {

    private ImageUtils() {}

    /**
     * 文件上传
     * @param file 本地上传文件参数
     * @param path 文件存储的目标路径 不带/ eg:/home/kingyumu/pic
     * @param isRename 是否重命名 true:是 false:否
     * @return 文件名，带/ eg:/a.jpg
     * @throws IOException 上传失败
     */
    public static String uploadImage(MultipartFile file, String path, boolean isRename) throws IOException {
        //获取文件原本的名字
        String originalFilename = file.getOriginalFilename();
        //获取拓展名
        assert originalFilename != null;
        String fileType = ".jpg";
        int i = originalFilename.lastIndexOf(".");
        if (i != -1) {
            String formatName = originalFilename.substring(i);
            String[] formatNameList = new String[] {".jpg", ".png", ".jpeg", ".gif", ".bmp", ".webp", "ico"};

            for (String f : formatNameList) {
                if (f.equalsIgnoreCase(formatName)) {
                    fileType = f;
                    break;
                }
            }
        }
        //文件路径
        String fileName = "";
        //是否重命名文件
        if (isRename) {
            //获取随机值
            String uuid = IdUtil.simpleUUID();
            fileName = "/" + uuid + fileType;
        } else {
            fileName = "/" + originalFilename;
        }
        File targetFile = new File(path, fileName);
        //把输入流传进去, 会把内容写到目标文件
        FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        return fileName;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return true:删除成功
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            return file.delete();
        }
        return false;
    }

}
