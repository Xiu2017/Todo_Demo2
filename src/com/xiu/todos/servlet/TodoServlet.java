package com.xiu.todos.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiu
 * @date 2019/1/8 16:55
 */
@WebServlet("/TodoServlet")
public class TodoServlet extends HttpServlet {

    private final static String ENCODING = "UTF-8";

    /**
     * 自定义分发请求
     * @param request 请求体
     * @param response 响应体
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
        //设置编码
        request.setCharacterEncoding(ENCODING);
        //自定义分发请求
        String method = request.getParameter("method");
        switch (method){
            case "list":
                listTodo(request, response);
                break;
            case "add":
                addTodo(request, response);
                break;
            case "update":
                updateTodo(request, response);
                break;
            case "delete":
                doDelete(request, response);
                break;
            default:
                listTodo(request, response);
                break;
        }
    }

    /**
     * 获取所有代办事项
     * @param request 请求体
     * @param response 相应体
     */
    private void listTodo(HttpServletRequest request, HttpServletResponse response){

    }

    /**
     * 添加代办事项
     * @param request 请求体
     * @param response 响应体
     */
    private void addTodo(HttpServletRequest request, HttpServletResponse response){

    }

    /**
     * 更新代办事项
     * @param request 请求体
     * @param response 响应体
     */
    private void updateTodo(HttpServletRequest request, HttpServletResponse response){

    }
}
