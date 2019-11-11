package com.test.controller.api;

import com.test.db.service.UserService;
import com.test.model.User;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("{page}")
    public String main(@PathVariable String page) {
        return page;
    }

    @RequestMapping("login")
    public String login(HttpSession session, String username, String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        if (username.equals("1") && password.equals("1")) {
            session.setAttribute("user", username);
            return "redirect:/main";
        } else {
            return "redirect:/login.jsp";
        }
    }

    @RequestMapping("demo")
    public String demo(HttpServletRequest request, HttpSession session) {
        request.setAttribute("req", "req value");
        session.setAttribute("session", "session value");
        request.getSession().setAttribute("getSession", "session value2");
        request.getServletContext().setAttribute("sc", "sc value");
        return "demo";
    }

    @RequestMapping("demo2")
    public String demo2(@SessionAttribute String session) {
        System.out.println(session);
        return "main";
    }

    @RequestMapping("demo3")
    public String demo3(Map<String, Object> map) {
        System.out.println(map.getClass());
        map.put("map", "map value");
        return "main";
    }

    @RequestMapping("demo4")
    public String demo3(Model map) {
        System.out.println(map.getClass());
        map.addAttribute("map", "map value");
        return "main";
    }

    @RequestMapping("demo5")
    public ModelAndView demo5() {
        ModelAndView mv = new ModelAndView("main");
        mv.addObject("mv", "mv value");
        return mv;
    }

    @RequestMapping("download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(fileName);
        ServletContext servletContext = request.getServletContext();
        String files = servletContext.getRealPath("files");
        System.out.println(files);
        File file = new File(files, fileName);
        byte[] bytes = FileUtils.readFileToByteArray(file);
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
    }

    @RequestMapping("upload")
    public String upload(MultipartFile file, String name) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        System.out.println("name:" + originalFilename);
        originalFilename = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        FileUtils.copyInputStreamToFile(inputStream, new File("E:/" + uuid + originalFilename));
        return "redirect:/index.jsp";
    }

    @RequestMapping("register")
    public String register(User user, MultipartFile file, HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String path = request.getServletContext().getRealPath("files")+"/"+filename;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setPhoto(filename);
        userService.addUser(user);
        return "redirect:/index.jsp";
    }
}
