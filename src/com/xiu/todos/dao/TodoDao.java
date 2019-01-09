package com.xiu.todos.dao;

import com.xiu.todos.entity.Todo;

import java.util.List;

/**
 * 用于操作Todo数据的Dao
 * @author xiu
 * @date 2019/1/8 15:23
 */
public interface TodoDao {

    /**
     * 查询所有Todo
     * @return Todo集合
     */
    List<Todo> listTodo();

    /**
     * 根据sql查询Todo
     * @param sql 查询语句
     * @return Todo集合
     */
    List<Todo> listTodoByQuery(String sql);

    /**
     * 添加单个Toto
     * @param todo Todo对象
     * @return 添加结果
     */
    boolean insertTodo(Todo todo);

    /**
     * 更新单个Toto
     * @param todo Todo对象
     * @return 更新结果
     */
    boolean updateTodo(Todo todo);

    /**
     * 根据Id删除Todo
     * @param id Todo的id
     * @return 删除结果
     */
    boolean deleteTodoById(String id);
}
