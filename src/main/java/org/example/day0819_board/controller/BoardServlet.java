package org.example.day0819_board.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.day0819_board.model.BoardDTO;
import org.example.day0819_board.model.BoardRepository;
import org.example.day0819_board.model.BoardRepositoryMysql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@WebServlet("/board.do")
public class BoardServlet extends HttpServlet {
    private BoardRepository repo = BoardRepositoryMysql.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        try {
            if ("list".equals(action)) {
                List<BoardDTO> boardList = repo.selectAll();
                req.setAttribute("bList", boardList);
                req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req,resp);
            } else if ("writeForm".equals(action)) {
                String loginId = (String) session.getAttribute("loginId");
                System.out.println("loginId = " + loginId);
                if (loginId == null) {
                    resp.sendRedirect(req.getContextPath() + "/user.do?action=login");
                } else {
                    req.getRequestDispatcher("/WEB-INF/views/writeForm.jsp").forward(req, resp);
                }
            } else if("view".equals(action)){
                String noParam = req.getParameter("no");
                int no = Integer.parseInt(noParam);

                BoardDTO board = repo.selectOne(no);
                if (!Objects.equals(board.getWriter(), (String) session.getAttribute("loginId"))) {
                    repo.readInc(no);
                }
                req.setAttribute("bbb", board);
                req.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(req, resp);
            } else if ("update".equals(action)) {
                BoardDTO selectedBoard = repo.selectOne(Integer.parseInt(req.getParameter("no")));
                if (!Objects.equals(selectedBoard.getWriter(), (String) session.getAttribute("loginId"))) {
                    req.setAttribute("msg", "당신 글이 아닙니다");
                    req.setAttribute("path", req.getContextPath()+"/board.do?action=view&no="+selectedBoard.getNo());
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
                } else {
                    req.setAttribute("board", selectedBoard);
                    req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
                }
            } else if ("delete".equals(action)) {
                BoardDTO selectedBoard = repo.selectOne(Integer.parseInt(req.getParameter("no")));

                if (!Objects.equals(selectedBoard.getWriter(), (String) session.getAttribute("loginId"))) {
                    req.setAttribute("msg", "당신 글이 아닙니다");
                    req.setAttribute("path", req.getContextPath()+"/board.do?action=view&no="+selectedBoard.getNo());
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
                } else {
                    repo.delete(selectedBoard);
                    req.setAttribute("msg", "게시글 삭제 완료");
                    req.setAttribute("path", req.getContextPath() + "/board.do?action=list");
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/views/common/error.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String title = req.getParameter("tttt");
        String content = req.getParameter("cccc");
        String writer = (String) session.getAttribute("loginId");

        BoardDTO boardDTO = new BoardDTO(title,content,writer);
        try {
            if (req.getParameter("_method").equals("put")) {
                boardDTO.setNo(Integer.parseInt(req.getParameter("nnn")));
                int result = repo.update(boardDTO);
                if (result == 1) {
                    req.setAttribute("msg", "글 수정 완료");
                    req.setAttribute("path", req.getContextPath()+"/board.do?action=list");
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
                }
            }
            else {
                int result = repo.insert(boardDTO);
                if (result == 1) {
                    req.setAttribute("msg", "글 작성 완료");
                    req.setAttribute("path", req.getContextPath()+"/board.do?action=list");
                    req.getRequestDispatcher("/WEB-INF/views/common/alert.jsp").forward(req,resp);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}