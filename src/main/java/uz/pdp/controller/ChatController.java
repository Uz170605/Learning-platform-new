package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dao.ChatDao;
import uz.pdp.dto.ChatMessageSumDto;
import uz.pdp.dto.CourseDto;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatDao chatDao;
    @Autowired
    LoginService loginService;

    @GetMapping("/mentor")
    public String mentorMessages(
            @RequestParam(required = false, name = "taskId") UUID taskId,
            @RequestParam(required = false, name = "student_id") UUID student_id
    ) {
        taskId = UUID.fromString("76838e44-29f5-4e8e-a481-05551fc948c9"); // TODO: 3/7/2022  uchirip quyish kk
        return "chat";
    }


    // TODO: 3/8/2022 xsdjjdfsguvhchcv
    @GetMapping("/allStudentMessage")
    public String studentAllMessage(

            HttpServletRequest request, Model model,
            @ModelAttribute("message") ChatMessageSumDto message
    ){
        message.setTaskId(UUID.fromString("76838e44-29f5-4e8e-a481-05551fc948c9")); // TODO: 3/7/2022  uchirip quyish kk
        message.setMentorId(UUID.fromString("5378035c-bd44-4564-a42e-86b27d7f52e4")); // TODO: 3/7/2022  uchirip quyish kk

        UUID userId = loginService.checked(model, request, "USER");
        if (userId == null)
            return "login";
        message.setUserId(userId);
        message.setRole("d23d64e3-0658-4c99-a22d-2ad9a999585d");

        model.addAttribute("role", "user");
        model.addAttribute("chat", chatDao.chat(message));
        return "chat";
    }


    @PostMapping("/student")
    public String studentMessages(
            MultipartFile file,
            HttpServletRequest request, Model model,
            @ModelAttribute("message") ChatMessageSumDto message
    ) {
        message.setTaskId(UUID.fromString("76838e44-29f5-4e8e-a481-05551fc948c9")); // TODO: 3/7/2022  uchirip quyish kk
        message.setMentorId(UUID.fromString("5378035c-bd44-4564-a42e-86b27d7f52e4")); // TODO: 3/7/2022  uchirip quyish kk

        UUID userId = loginService.checked(model, request, "USER");
        if (userId == null)
            return "login";
        message.setUserId(userId);
        message.setRole("d23d64e3-0658-4c99-a22d-2ad9a999585d");

        if (!file.isEmpty()) {

            try {
                byte[] fileArr = file.getBytes();
                String fileName= file.getOriginalFilename();
                message.setMessage(fileName);
                chatDao.studentSendTask(message, fileArr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            chatDao.studentMentorSendMessage(message);
        }
        model.addAttribute("role", "user");
        model.addAttribute("chat", chatDao.chat(message));
        return "chat";
    }



}
