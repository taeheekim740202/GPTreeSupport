package com.gptree.login.controller;

import com.gptree.common.contants.PageUrls;
import com.gptree.common.contants.SessionNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * GPTree Support Login
 *
 * Created by Tae-Hee, Kim on 2016-12-29.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login/login.gptree")
    public String login(HttpSession session) {
        if (session.getAttribute(SessionNames.SESSION_USER_INFOMATION) != null) {
            return String.format("%s%s", "redirect:", PageUrls.PAGE_MAIN_REQUEST);
        } else {
            return "login/login";
        }
    }
}
