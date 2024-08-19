package org.example.day0819_board.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.day0819_board.model.MemberDTO;
import org.example.day0819_board.model.MemberRepository;
import org.example.day0819_board.model.MemberRepositoryMysql;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user.do")
public class UserServlet extends HttpServlet {
    private MemberRepository repo = MemberRepositoryMysql.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if("login".equals(action)){ // 로그인 하겠다고 ? 기존에 로그인한 상태 아닌지 체크한번 할게!
            HttpSession session = req.getSession();
            String loginId = (String)session.getAttribute("loginId");
            if(loginId==null) { // 로그인 안된 애네~ 로그인 html 화면 만들어 주지뭐~~
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            } else { // 엥? 너 이미 로그인 되어있어!
                req.setAttribute("msg", "이미 로그인 내역 있습니다.");
                req.setAttribute("path", req.getContextPath()); // 맨 첫페이지로 보냄
                req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
            }
        }else if("logout".equals(action)){
            HttpSession session = req.getSession();
            session.invalidate(); // removeAttribute("loginId") 해도 되지만 보통 지금 로그아웃하는 사용자의 정보 싹 날리느라 그냥 세션객체 자체를 없애버림.

            req.setAttribute("msg", "로그아웃 되었습니다.");
            req.setAttribute("path", req.getContextPath()); // 맨 첫페이지로 보냄
            req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
        }else if("register".equals(action)){
            HttpSession session = req.getSession();
            String loginId = (String)session.getAttribute("loginId");
            if(loginId==null) { // 로그인 안된 애네~ 로그인 html 화면 만들어 주지뭐~~
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            } else { // 엥? 너 이미 로그인 되어있어!
                req.setAttribute("msg", "이미 로그인 내역 있습니다.");
                req.setAttribute("path", req.getContextPath()); // 맨 첫페이지로 보냄
                req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = req.getParameter("userid");
        String userpw = req.getParameter("userpw");

        String action = req.getParameter("action");
        if("login".equals(action)){
            try {
                MemberDTO exists = repo.exists(userid);
                if (exists != null && exists.getUserPw().equals(userpw)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("loginId", userid);

                    if (req.getParameter("remember") == null) {
                        System.out.println("체크 해제됨");
                        Cookie cookie = new Cookie("rememberId", "");
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    } else if (req.getParameter("remember").equals("on")) {
                        Cookie cookie = new Cookie("rememberId", userid);
                        cookie.setMaxAge(60*60*24*7);
                        resp.addCookie(cookie);
                    }

                    req.setAttribute("msg", "로그인 완료되었습니다. " + session.getAttribute("loginId"));
                    req.setAttribute("path", req.getContextPath());
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
                }else{
                    req.setAttribute("msg", "로그인 실패입니다. 아이디나 패스워드를 확인해 주세요.");
                    req.setAttribute("path", req.getContextPath()+"/user.do?action=login");
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("register".equals(action)){
            MemberDTO memberDTO = new MemberDTO(userid, userpw);
            try {
                repo.insert(memberDTO);
                req.setAttribute("msg", "회원가입 완료되었습니다.");
                req.setAttribute("path", req.getContextPath());
                req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}