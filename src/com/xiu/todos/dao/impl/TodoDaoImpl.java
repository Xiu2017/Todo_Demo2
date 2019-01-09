package com.xiu.todos.dao.impl;

import com.xiu.todos.dao.BaseDao;
import com.xiu.todos.dao.TodoDao;
import com.xiu.todos.entity.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiu
 * @date 2019/1/8 14:12
 */
public class TodoDaoImpl implements TodoDao {

    @Override
    public List<Todo> listTodo() {
        String sql = "select * from todo";
        ResultSet resultSet = BaseDao.executeQuery(sql, null);
        List<Todo> todos = parseResultSet(resultSet);
        return todos;
    }

    @Override
    public List<Todo> listTodoByQuery(String sql) {
        ResultSet resultSet = BaseDao.executeQuery(sql, null);
        List<Todo> todos = parseResultSet(resultSet);
        return todos;
    }

    @Override
    public boolean insertTodo(Todo todo) {
        String sql = "insert into todos (id, content, date, status) values (?,?,?,?)";
        List<Object> params = todoToParam(todo);
        int result = BaseDao.executeUpdate(sql, params);
        return result > 0;
    }

    @Override
    public boolean updateTodo(Todo todo) {
        String sql = "update todos set content=?,status=? where id=?";
        return false;
    }

    @Override
    public boolean deleteTodoById(String id) {
        String sql = "";
        return false;
    }

    /**
     * 从ResutlSet中解析Todo数据
     * @param resultSet 查询结果
     * @return 解析后的todos
     */
    private List<Todo> parseResultSet(ResultSet resultSet){
        List<Todo> todos = new ArrayList<>();
        if (resultSet != null) {
            try {
                while (resultSet.next()){
                    Todo todo = new Todo();
                    todo.setId(resultSet.getString("id"));
                    todo.setContent(resultSet.getString("content"));
                    todo.setDate(resultSet.getLong("date"));
                    todo.setStatus(resultSet.getString("status"));
                    todos.add(todo);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return todos;
    }

    /**
     * 将todo实体类转为param
     * @param todo todo实体类
     * @return 转换后的param
     */
    private List<Object> todoToParam(Todo todo){
        List<Object> params = new ArrayList<>();
        if(todo != null){
            String id = todo.getId();
            String content = todo.getContent();
            long date = todo.getDate();
            String status = todo.getStatus();
            if(id != null && id.length() > 0){
                params.add(id);
            }
            if(content != null && content.length() > 0){
                params.add(content);
            }
            if(date > 0){
                params.add(date);
            }
            if(status != null && status.length() > 0){
                params.add(status);
            }
        }
        return params;
    }
}
