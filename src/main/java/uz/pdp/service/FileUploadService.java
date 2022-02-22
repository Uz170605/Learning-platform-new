package uz.pdp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dao.FileUploadDao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUploadService {

    @Autowired
    FileUploadDao fileUploadDao;


    public String saveFile(MultipartFile file, UUID lessonId) {
        if (!file.isEmpty()) {
            BufferedOutputStream bos = null;
            String fileName="";
            try {
                byte[] fileBytes = file.getBytes();
                String originalFilename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + file.getOriginalFilename();
                String contentType = file.getContentType();
                if (Objects.requireNonNull(contentType).equals("video/mp4")){
                 fileName = "D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonVideo/" + originalFilename;
                } else if (Objects.equals(contentType, "application/pdf")){
                    fileName = "D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonManual/"+originalFilename;
                } else if (contentType.equals("application/msword")){
                    fileName = "D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonTask/"+originalFilename;
                }
                bos = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                bos.write(fileBytes);

               Integer save = fileUploadDao.saveFileDb(lessonId,originalFilename,contentType);

                if (save !=0) {
                    return  "Upload successful for " + file.getName();
                } else {
                    return  "Upload failed for " + file.getName() + " as file is invalid or empty";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        return "Error upload!";
                    }
                }
            }
        }
        else {
            return  "Upload failed for " + file.getName() + " as file is invalid or empty";
        }
        return "Error upload!";
    }
    }

