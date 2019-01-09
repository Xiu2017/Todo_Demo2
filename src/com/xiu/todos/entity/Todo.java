package com.xiu.todos.entity;

/**
 * 代办事项实体类
 * @author xiu
 * @date 2019/1/8 14:12
 */
public class Todo {
    /**
     * 代办事项id
     */
    private String id;
    /**
     * 代办事项内容
     */
    private String content;
    /**
     * 代办事项状态，Active 或 Completed
     */
    private String status;
    /**
     * 代办事项日期
     */
    private long date;

    public final static String STATUS_ACTIVE = "Active";
    public final static String STATUS_COMPLETED = "Completed";

    public Todo() {
    }

    public Todo(String id, String content, String status, long date) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
