package com.example.webuploader.sevice;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author booty
 * @date 2021/5/14 14:03
 */
public interface FileUploadService {

    JSONObject verifyFileExists(String fileMD5, String fileExt) throws Exception;

    JSONObject getChunkUploadCount(String fileMD5) throws Exception;

    JSONObject getChunkUploadIndex(String fileMD5, String chunkSize) throws Exception;

    JSONObject verifyChunk(String fileMD5, String chunk, String chunkSize) throws Exception;

    JSONObject upload(MultipartFile file, String fileMD5, String chunk, String chunkSize) throws Exception;

    JSONObject marginFile(String fileMD5, String fileExt, String chunkCount) throws Exception;

    JSONObject cancel(String fileMD5) throws Exception;


}
