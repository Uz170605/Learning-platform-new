package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.UUID;

@Controller
@RequestMapping("/download")
public class FileDownload {

    @Autowired
    FileService fileService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        return "index";
    }

    @RequestMapping(value = "/viewVideo/{id}")
    public String viewVideo( Model model,
            @PathVariable(required = false)String id
    ){
        model.addAttribute("lesson", fileService.lessonDto(UUID.fromString(id)));
        model.addAttribute("data", fileService.getAllData(UUID.fromString(id)));
        model.addAttribute("videoPath", fileService.viewVideo(UUID.fromString(id)));
        model.addAttribute("lesson_id", id);
        return "video";
    }


    @RequestMapping(value = "/pdfDownload/{id}")
    public void download(
            @PathVariable(required = false) String id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonManual/"+fileService.pdfDownload(UUID.fromString(id)));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/docDownload/{id}")
    public void downloadDoc(
            @PathVariable(required = false) String id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("D:/cdisk/JAVA BACKEND/5-modul/yangiii/Learning-platform-new/web/assets/fileUpload/lessonTask/"+fileService.pdfDownload(UUID.fromString(id)));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }


    @ExceptionHandler(IOException.class)
    public ModelAndView handleErrors(Exception ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exeption", ex);
        return model;
    }

}
