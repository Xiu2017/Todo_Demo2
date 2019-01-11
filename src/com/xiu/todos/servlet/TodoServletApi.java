package com.xiu.todos.servlet;

import com.google.gson.Gson;
import com.xiu.todos.dao.TodoDao;
import com.xiu.todos.dao.impl.TodoDaoImpl;
import com.xiu.todos.entity.Todo;
import com.xiu.todos.util.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author xiu
 * @date 2019/1/10 10:27
 */
@WebServlet("/TodoServletApi")
public class TodoServletApi extends HttpServlet {

    private final static String ENCODING = "UTF-8";

    /**
     * 自定义分发请求
     *
     * @param request  请求体
     * @param response 响应体
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //允许跨域请求
        Utils.allowCORS(response);
        //设置编码
        request.setCharacterEncoding(ENCODING);
        //自定义分发请求
        String method = request.getParameter("method");
        switch (method) {
            case "all":
                listTodo(request, response);
                break;
            case "active":
                listTodoByStatus(request, response, Todo.STATUS_ACTIVE);
            case "completed":
                listTodoByStatus(request, response, Todo.STATUS_COMPLETED);
                break;
            case "count":
                todoCountByStatus(request, response);
                break;
            case "insert":
                insertTodo(request, response);
                break;
            case "update":
                updateTodo(request, response);
                break;
            case "delete":
                deleteTodo(request, response);
                break;
            case "deleteAllCompleted":
                deleteAllCompleted(request, response);
                break;
            case "toggleAll":
                toggleAll(request, response);
                break;
            default:
                break;
        }
    }

    /**
     * 获取所有代办事项
     *
     * @param request  请求体
     * @param response 相应体
     */
    private void listTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TodoDao todoDao = new TodoDaoImpl();
        List<Todo> todos = todoDao.listTodo();
        //发送数据
        sendResult(response, todos);
    }

    /**
     * 根据状态查询代办事项
     *
     * @param request  请求体
     * @param response 相应体
     */
    private void listTodoByStatus(HttpServletRequest request, HttpServletResponse response, String status) throws IOException {
        TodoDao todoDao = new TodoDaoImpl();
        Todo todo = new Todo();
        todo.setStatus(status);
        List<Todo> todos = todoDao.listTodoByQuery(todo);

        //发送数据
        sendResult(response, todos);
    }

    /**
     * 添加代办事项
     *
     * @param request  请求体
     * @param response 响应体
     */
    private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = request.getParameter("content");
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setStatus(Todo.STATUS_ACTIVE);
        todo.setDate(System.currentTimeMillis());

        TodoDao todoDao = new TodoDaoImpl();
        int result = todoDao.insertTodo(todo);
        System.out.println("受影响行数：" + result);
        sendResult(response, String.valueOf(result));
    }

    /**
     * 更新代办事项
     *
     * @param request  请求体
     * @param response 响应体
     */
    private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //处理请求参数
        int id = Integer.valueOf(request.getParameter("id"));
        String content = request.getParameter("content");
        String status = request.getParameter("status");
        Todo todo = new Todo();
        todo.setId(id);
        if (content != null && content.length() > 0) {
            todo.setContent(content);
        }
        if (status != null && status.length() > 0) {
            todo.setStatus(status);
        }

        //更新
        TodoDao todoDao = new TodoDaoImpl();
        int result = todoDao.updateTodo(todo);
        System.out.println("受影响行数：" + result);
        sendResult(response, String.valueOf(result));
    }

    /**
     * 删除代办事项
     *
     * @param request  请求体
     * @param response 相应体
     */
    private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        TodoDao todoDao = new TodoDaoImpl();
        int result = todoDao.deleteTodoById(id);
        System.out.println("受影响行数：" + result);
        sendResult(response, String.valueOf(result));
    }

    /**
     * 删除所有已完成待办事项
     * @param request 请求体
     * @param response 响应体
     */
    private void deleteAllCompleted(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TodoDao todoDao = new TodoDaoImpl();
        int result = todoDao.deleteTodoByStatus(Todo.STATUS_COMPLETED);
        System.out.println("受影响行数：" + result);
        sendResult(response, String.valueOf(result));
    }

    /**
     * 更改全部Todo状态为完成或未完成
     * @param request 请求体
     * @param response 响应体
     */
    private void toggleAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TodoDao todoDao = new TodoDaoImpl();
        Todo todo = new Todo();
        todo.setStatus(Todo.STATUS_ACTIVE);
        int activeTodoCount = todoDao.todoCountByQuery(todo);
        int result;
        if (activeTodoCount > 0) {
            result = todoDao.updateAllTodoStatus(Todo.STATUS_COMPLETED);
        } else {
            result = todoDao.updateAllTodoStatus(Todo.STATUS_ACTIVE);
        }
        System.out.println("受影响行数：" + result);
        sendResult(response, String.valueOf(result));
    }

    /**
     * 根据Todo状态获取数量
     * @param request 请求体
     * @param response 响应体
     */
    private  void todoCountByStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("status");
        TodoDao todoDao = new TodoDaoImpl();
        Todo todo = new Todo();
        todo.setStatus(status);
        int todoCount = todoDao.todoCountByQuery(todo);
        sendResult(response, String.valueOf(todoCount));
    }

    /**
     * 向客户端发送JSON对象
     * @param todos 要转换为JSON的Object对象
     */
    private void sendResult(HttpServletResponse response, List<Todo> todos) throws IOException {
        //直接输出结果
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        String jsonStr = new Gson().toJson(todos);
        out.write(jsonStr);
        out.close();
    }

    /**
     * 向客户端发送字符串
     * @param data 要发送的字符串
     */
    private void sendResult(HttpServletResponse response, String data) throws IOException {
        //直接输出结果
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.write(data);
        out.close();
    }
}
