package com.mwj.personweb.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: 母哥 @Date: 2019-03-08 10:22 @Version 1.0
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String accessKey = "b-lHQMCnIt3TO4P7QL1hVM06sm2cxAqN-53uwWDe";
    public static String secretKey = "mSll8IcTHHotFymXpvrkPETXwPm9sfUrhn_VtWF_";
    public static String bucket = "munjie";
    private static String QINIU_IMAGE_DOMAIN = "http://cdn.biubiucat.com";

    /* 		    华东	Zone.zone0()
                华北	Zone.zone1()
                华南	Zone.zone2()
                北美	Zone.zoneNa0()
    */
    // 文件流上传
    public static String upload(MultipartFile file) throws IOException {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        DefaultPutRet putRet = null;
        try {
            byte[] uploadBytes = file.getBytes();
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            } catch (QiniuException ex) {
                Response r = ex.response;
                logger.error("上传文件到七牛云异常", r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    logger.error("上传文件到七牛云异常", ex2);
                }
            }
        } catch (UnsupportedEncodingException ex) {

            logger.error("上传文件到七牛云异常", ex);
        }
        return QINIU_IMAGE_DOMAIN + "/" + putRet.key;
    }
}
