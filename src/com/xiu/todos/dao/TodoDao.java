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
     * 根据条件查询Todo
     * @param todo 包含查询条件的Todo实体类
     * @return Todo集合
     */
    List<Todo> listTodoByQuery(Todo todo);

    /**
     * 根据
     * @param todo
     * @return
     */
    int todoCountByQuery(Todo todo);

    /**
     * 添加单个Toto
     * @param todo Todo对象
     * @return 添加结果
     */
    int insertTodo(Todo todo);

    /**
     * 更新单个Toto
     * @param todo Todo对象
     * @return 更新结果
     */
    int updateTodo(Todo todo);

    /**
     * 更新所有Todo的状态
     * @param status 要更新的状态
     * @return 受影响行数
     */
    int updateAllTodoStatus(String status);

    /**
     * 根据Id删除Todo
     * @param id Todo的id
     * @return 受影响行数
     */
    int deleteTodoById(String id);

    /**
     * 根据状态删除Todo
     * @param status Todo状态
     * @return 受影响行数
     */
    int deleteTodoByStatus(String status);
}
