package com.mj.pan_load.controller;

import com.mj.pan_load.dao.FeDao;
import com.mj.pan_load.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class Emp {
    @Autowired
    private FeDao feDao;

    @GetMapping("showAll")
    String all(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("files",feDao.sAll(user.getName()));
            return "show";
        }else return "redirect:error";
    }

    @GetMapping("error")
    String error() {
        return "error";
    }
}
