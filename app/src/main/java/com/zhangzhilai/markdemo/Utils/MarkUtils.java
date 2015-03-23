package com.zhangzhilai.markdemo.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by zhangzhilai on 3/18/15.
 *
 */
public class MarkUtils {

    public static final String TAG = "MarkUtils";

    public static final String APPLICATION_NAME = "myApp";
    //单次最多发送图片数
    public static final int MAX_IMAGE_SIZE = 8;
    //首选项:临时图片
    public static final String PREF_TEMP_IMAGES = "pref_temp_images";

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory() + File.separator;
    public static final String FLIE_PATH = SDCARD_PATH + "MarkDemo" + File.separator;
    public static final String FILE_NAME = "MarkCircleText";

    public static final String SHAREPREFER_CONFIG = "shareprefer_config";
    public static final String IS_FIRST_INSTALL = "is_first_install";

    public static final int RESULT_OK = 0x000088;                    //表示跳转成功
    public static final int TAKE_PICTURE_FROM_CAMERA = 0x000000;     //从相机获取图片
    public static final int TAKE_PICTURE_FROM_ALBUM = 0X000001;      //从相册获取图片
    public static final int BUCKETCHOOSE_TO_IMAGECHOOSE = 0x000002;  //由相册跳转到图片库
    public static final int JUMP_FROM_MARK_EDIT = 0x000003;          //从编辑页跳转到图片查看页面
    public static final int JUMP_FROM_MARK_CIRCLE = 0x000004;        //从标记圈页跳转到图片查看页面
    public static final int JUMP_MARK_CIRCLE_TO_MARK_EDIT = 0x000005;//从标记圈页跳转到标记编辑页面

    public static final String EXTRA_IMAGE_LIST = "image_list";      //相册中图片对象集合
    public static final String EXTRA_BUCKET_NAME = "buck_name";      //相册名称
    public static final String EXTRA_CAN_ADD_IMAGE_SIZE = "can_add_image_size"; //可添加的图片数量
    public static final String EXTRA_CURRENT_IMG_POSITION = "current_img_position";  //当前选择的照片位置
    public static final String EXTRA_JUMP_FROM_PAGE = "jump_from_page";  //跳转到图片扩展界面的原始界面
    public static final String EXTRA_MARK_CIRCLE_TO_MARK_EDIT = "jump_markcircle_to_markedit";  //跳转到图片扩展界面的原始界面

    public static boolean textIsEmpty(String stringContent){
        if(stringContent.length() == 0 || stringContent.equals("") || stringContent == null){
            return true;
        }
        return false;
    }

    /**
     *
     * @param filePath
     *            文件名称路径
     * @return
     */
    public static String readFile(String filePath) {
        String str = "";
        File readFile = new File(filePath);
        if (!isSDCardEnabled()) {
            // SD卡不可用，操作取消
            return null;
        }
        if (!readFile.exists()) {
            return null;
        }
        try {
            FileInputStream inStream = new FileInputStream(readFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            str = stream.toString();
            stream.close();
            inStream.close();
        } catch (Exception e) {
            return null;
        }
        return str;
    }

    public static boolean isSDCardEnabled() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    /**
     *
     * @param s
     *            字符串
     * @param name
     *            文件名称
     */
    public static void saveString2File(String s, String name) {
        if (!isSDCardEnabled()) {
            // SD卡不可用，操作取消
            return;
        }
        File folder = new File(MarkUtils.FLIE_PATH);
        if(!isExistFile(folder)){
            folder.mkdirs();
        }
        File fileTemp = new File(MarkUtils.FLIE_PATH + name + ".txt");
        if (isExistFile(fileTemp)) {
            fileTemp.delete();
        }
        try {
            FileOutputStream outStream = new FileOutputStream(MarkUtils.FLIE_PATH + name + ".txt", true);
            OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
            writer.write(s);
            // writer.write("/n");
            writer.flush();
            writer.close();// 记得关闭
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isExistFile(File file) {
        try {
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


}
