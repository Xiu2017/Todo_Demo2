package com.xiu.todos.dao.impl;

import com.xiu.todos.dao.BaseDao;
import com.xiu.todos.dao.TodoDao;
import com.xiu.todos.entity.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 直接操作Todo数据的Dao
 * @author xiu
 * @date 2019/1/8 14:12
 */
public class TodoDaoImpl implements TodoDao {

    @Override
    public List<Todo> listTodo() {
        String sql = "select * from todo order by date asc";
        ResultSet resultSet = BaseDao.executeQuery(sql, null);
        List<Todo> todos = parseResultSet(resultSet);
        BaseDao.close();  //正确关闭连接
        return todos;
    }

    @Override
    public List<Todo> listTodoByQuery(Todo todo) {
        String sql = "select * from todo where 1=1";
        //以下判断顺序不可改变
        String content = todo.getContent();
        if(content != null && content.length() > 0){
            sql += " and content like '%?%'";
        }
        String status = todo.getStatus();
        if(status != null && status.length() > 0){
            sql += " and status=?";
        }
        int id = todo.getId();
        if(id > 0){
            sql += " and id=?";
        }
        long date = todo.getDate();
        if(date > 0){
            sql += " and date=?";
        }
        sql += " order by date asc";
        List<Object> params = todoToParam(todo);
        ResultSet resultSet = BaseDao.executeQuery(sql, params);
        List<Todo> todos = parseResultSet(resultSet);
        BaseDao.close();  //正确关闭连接
        return todos;
    }

    @Override
    public int todoCountByQuery(Todo todo) {
        String sql = "select count(*) as count from todo where 1=1";
        //以下判断顺序不可改变
        String content = todo.getContent();
        if(content != null && content.length() > 0){
            sql += " and content like '%?%'";
        }
        String status = todo.getStatus();
        if(status != null && status.length() > 0){
            sql += " and status=?";
        }
        int id = todo.getId();
        if(id > 0){
            sql += " and id=?";
        }
        long date = todo.getDate();
        if(date > 0){
            sql += " and date=?";
        }
        List<Object> params = todoToParam(todo);
        ResultSet resultSet = BaseDao.executeQuery(sql, params);
        int count = 0;
        try {
            resultSet.next();
            count = resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.close();  //正确关闭连接
        }
        return count;
    }

    @Override
    public int insertTodo(Todo todo) {
        String sql = "insert into todo (content, status, date) values (?,?,?)";
        List<Object> params = todoToParam(todo);
        return BaseDao.executeUpdate(sql, params);
    }

    @Override
    public int updateTodo(Todo todo) {
        String sql = "";
        String content = todo.getContent();
        if(content != null && content.length() > 0){
            sql = "update todo set content=? where id=?";
        }
        String status = todo.getStatus();
        if(status != null && status.length() > 0){
            sql = "update todo set status=? where id=?";
        }
        List<Object> params = todoToParam(todo);
        return BaseDao.executeUpdate(sql, params);
    }

    @Override
    public int updateAllTodoStatus(String status) {
        String sql = "update todo set status=?";
        List<Object> params = new ArrayList<>();
        params.add(status);
        return BaseDao.executeUpdate(sql, params);
    }

    @Override
    public int deleteTodoById(String id) {
        String sql = "delete from todo where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        return BaseDao.executeUpdate(sql, params);
    }

    @Override
    public int deleteTodoByStatus(String status) {
        String sql = "delete from todo where status=?";
        List<Object> params = new ArrayList<>();
        params.add(status);
        return BaseDao.executeUpdate(sql, params);
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
                    todo.setId(resultSet.getInt("id"));
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
            //以下判断顺序不可改变
            String content = todo.getContent();
            if(content != null && content.length() > 0){
                params.add(content);
            }
            String status = todo.getStatus();
            if(status != null && status.length() > 0){
                params.add(status);
            }
            int id = todo.getId();
            if(id > 0){
                params.add(id);
            }
            long date = todo.getDate();
            if(date > 0){
                params.add(date);
            }
        }
        return params;
    }
}
