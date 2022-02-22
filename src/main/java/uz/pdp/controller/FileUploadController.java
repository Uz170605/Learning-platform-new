package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dao.FileUploadDao;
import uz.pdp.dao.LessonDao;
import uz.pdp.dto.FileDto;
import uz.pdp.dto.LessonDto;
import uz.pdp.service.FileUploadService;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    LessonDao lessonDao;

    @Autowired
    FileUploadDao fileUploadDao;

    @GetMapping("/video")
    public String getVideoUpload(Model model){
        List<LessonDto> allLessons = lessonDao.getAllLessons();
        model.addAttribute("lesson",allLessons);
        return "video-upload";
    }

    @GetMapping("/manual")
    public String getManualUpload(Model model){
        List<LessonDto> allLessons = lessonDao.getAllLessons();
        model.addAttribute("lesson",allLessons);
        return "manual-upload";
    }

    @GetMapping("/task")
    public String getTaskUpload(Model model){
        List<LessonDto> allLessons = lessonDao.getAllLessons();
        model.addAttribute("lesson",allLessons);
        return "task-upload";
    }

    @GetMapping("/videoData")
    public String getAllVideo(Model model){
        List<FileDto> fileVideo = fileUploadDao.getFileVideo();
        model.addAttribute("fileList",fileVideo);
        return "view-video";
    }

    @GetMapping("/manualData")
    public String getAllManual(Model model){
        List<FileDto> fileManual = fileUploadDao.getFileManual();
        model.addAttribute("fileList",fileManual);
        return "view-manual";
    }

    @GetMapping("/taskData")
    public String getAllTask(Model model){
        List<FileDto> fileTask = fileUploadDao.getFileTask();
        model.addAttribute("fileList",fileTask);
        return "view-task";
    }

    @PostMapping
    public String saveFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("lessonId") String id, Model model) {
        UUID lessonId = UUID.fromString(id);
        String msg = fileUploadService.saveFile(file, lessonId);
        model.addAttribute("message", msg);
        if (!file.isEmpty()) {
            String contentType = file.getContentType();
            if (Objects.equals(contentType, "video/mp4")){
                return "redirect:/upload/videoData";
            } else if (Objects.equals(contentType, "application/pdf")){
                return "redirect:/upload/manualData";
            } else if (Objects.requireNonNull(contentType).equals("application/msword")){
                return "redirect:/upload/taskData";
            }
            return "view-lessons";
        } else {
            return "view-lessons";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable String id){
        UUID fileId = UUID.fromString(id);
        FileDto fileById = fileUploadDao.getFileById(fileId);
        if (fileById.getFileType().equals("video/mp4")) {
            fileUploadDao.deleteFile(fileId);
            File file = new File("D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonVideo/"+fileById.getFileName());
            file.delete();
            return "redirect:/upload/videoData";
        } else if (fileById.getFileType().equals("application/pdf")){
            fileUploadDao.deleteFile(fileId);
            File file = new File("D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonManual/"+fileById.getFileName());
            file.delete();
            return "redirect:/upload/manualData";
        } else {
            fileUploadDao.deleteFile(fileId);
            File file = new File("D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonTask/"+fileById.getFileName());
            file.delete();
            return "redirect:/upload/taskData";
        }
    }

}
