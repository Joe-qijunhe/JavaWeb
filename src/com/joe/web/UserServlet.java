package com.joe.web;

import com.google.gson.Gson;
import com.joe.pojo.User;
import com.joe.service.UserService;
import com.joe.service.impl.UserServiceImpl;
import com.joe.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
        //登录处理业务
        User loginUser = userService.login(user);
        if (loginUser == null) {
            //把错误信息和回显表单信息保存在request域当中
            req.setAttribute("msg", "用户或密码错误");
            req.setAttribute("username", username);
            //跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            //登录成功
            //保存用户登录的信息到Session域
            req.getSession().setAttribute("user", loginUser);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //检查验证码是否正确
        if (token != null && token.equalsIgnoreCase(code)) {
            //检查用户名是否可用
            if (userService.existUsername(username)) {
                //把错误信息和回显表单信息保存在request域当中
                req.setAttribute("msg", "用户名已存在");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                //不可用，跳回注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
                userService.registerUser(user);
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            //把错误信息和回显表单信息保存在request域当中
            req.setAttribute("msg", "验证码错误");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            //跳回注册页面
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //销毁session
        req.getSession().invalidate();
        //重定向到首页
        resp.sendRedirect(req.getContextPath());
    }

    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String username = req.getParameter("username");
        boolean existsUsername = userService.existUsername(username);
        //把返回的结果封装成map对象
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername", existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);

    }
}
