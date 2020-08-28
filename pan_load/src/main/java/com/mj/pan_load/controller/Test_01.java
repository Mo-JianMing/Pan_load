package com.mj.pan_load.controller;

import com.mj.pan_load.dao.FeDao;
import com.mj.pan_load.dao.UserDao;
import com.mj.pan_load.entity.Fe;
import com.mj.pan_load.entity.User;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Test_01 {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FeDao feDao;

    private String realPath;

    @PostMapping("login")
    String s1(String name, String password, HttpSession session) {
        User user = userDao.su(name, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:showAll";
        } else {
            System.out.println("用户信息错误.........");
            return "redirect:error";
        }
    }

    @PostMapping("upload")
    String s2(MultipartFile file, HttpSession session) throws IOException {
        realPath = new File("").getCanonicalPath() + "\\src\\main\\resources\\static\\files";
        User user = (User) session.getAttribute("user");
        File dir = new File(realPath, user.getName());
        if (!dir.exists()) dir.mkdirs();
        file.transferTo(new File(dir, file.getOriginalFilename()));

        long bt = file.getSize();
        String size = bt > 1024 * 1024 ? bt / 1024 / 1024 + "mb" : bt / 1024 + 1 + "kb";
//        String size = (file.getSize() / 1024 + 1) + "kb";
        Fe fe = new Fe(file.getOriginalFilename(), size, user.getName(), new Date());
        feDao.save(fe);
        return "redirect:showAll";
    }

    @GetMapping("downLoad")
    void s3(String tp, String fileName, String user, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");

        realPath = new File("").getCanonicalPath() + "\\src\\main\\resources\\static\\files\\" + user;
        File file = new File(realPath, fileName);
        FileInputStream is = new FileInputStream(file);

        tp = tp == null ? "attachment" : tp;
        response.setHeader("content-disposition", tp + ";fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream os = response.getOutputStream();

        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    @GetMapping("delete")
    String s4(String fileName, String user) throws IOException {
        realPath = new File("").getCanonicalPath() + "\\src\\main\\resources\\static\\files\\" + user;
        File file = new File(realPath, fileName);
        if (file.exists()) {
            file.delete();
            feDao.delete(fileName,user);
        }
        return "redirect:showAll";
    }

}
