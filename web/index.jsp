<%@ page import="java.util.List" %>
<%@ page import="com.xiu.todos.entity.Todo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Vue.js • TodoMVC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
<%
    //当前请求的状态
    String method = request.getParameter("method");
%>
<section class="todoapp">
    <header class="header">
        <h1>todos</h1>
        <form id="todo-form" action="${pageContext.request.contextPath}/TodoServlet?method=insert&last=<%=method%>" method="post" onsubmit="return addTodo()">
            <input name="content" class="new-todo" autofocus autocomplete="off" placeholder="What needs to be done?">
        </form>
    </header>
    <section class="main">
        <input id="toggle-all" class="toggle-all" type="checkbox">
        <label onclick="window.location.href='${pageContext.request.contextPath}/TodoServlet?method=toggleAll&last=<%=method%>'">Mark all as complete</label>
        <ul class="todo-list">
            <%
                //获取请求数据
                @SuppressWarnings("unchecked")
                List<Todo> todos = (List<Todo>) request.getAttribute("todos");
                //记录已完成的代办事项数量
                int completed_count = 0;
                //如果有数据，则遍历数据
                if (todos != null && todos.size() > 0) {
                    for (Todo todo : todos) {
                        //如果代办事项状态为completed，则已完成数量加1
                        if (Todo.STATUS_COMPLETED.equals(todo.getStatus())) {
                            completed_count++;
                            if("active".equals(method)){
                                continue;
                            }
                        }else if("completed".equals(method)){
                            continue;
                        }
            %>
            <li class="todo <%
                //如果状态为完成，则添加完成样式
                if(Todo.STATUS_COMPLETED.equals(todo.getStatus())){
                    %>
                    completed
                  <%
                }
            %>">
                <div class="view">
                    <input class="toggle" type="checkbox"
                           onclick="window.location.href='<%=request.getContextPath()%>/TodoServlet?method=update&id=<%=todo.getId()%>&status='+(this.checked?'<%=Todo.STATUS_COMPLETED%>':'<%=Todo.STATUS_ACTIVE%>') + '&last=<%=method%>'"
                        <%
                            //如果状态为完成，则让checkbox默认选中
                            if(Todo.STATUS_COMPLETED.equals(todo.getStatus())){
                                %>
                                    checked="checked"
                                <%
                            }
                        %>>
                    <label><%=todo.getContent()%>
                    </label>
                    <button class="destroy" onclick="window.location.href='<%=request.getContextPath()%>/TodoServlet?method=delete&id=<%=todo.getId()%>&last=<%=method%>'"></button>
                </div>
                <input class="edit" type="text">
            </li>
            <%
                    }
                }
            %>
        </ul>
    </section>
    <%
        //如果代办事项不为空，则显示工具栏
        if (todos != null && todos.size() > 0) {
    %>
    <footer class="footer">
    <span class="todo-count">
        <strong><%=todos.size() - completed_count%> items left</strong>
    </span>
        <ul class="filters">
            <li><a href="<%=request.getContextPath()%>/TodoServlet?method=all" <%
                //判断选项卡状态
                String queryString = request.getQueryString() + "";
                if(queryString.contains("method=all")){
                    %>
                        class="selected"
                    <%
                }
            %>>All</a></li>
            <li><a href="<%=request.getContextPath()%>/TodoServlet?method=active" <%
                //判断选项卡状态
                if(queryString.contains("method=active")){
                    %>
                        class="selected"
                    <%
                }
            %>>Active</a></li>
            <li><a href="<%=request.getContextPath()%>/TodoServlet?method=completed" <%
                //判断选项卡状态
                if(queryString.contains("method=completed")){
                    %>
                        class="selected"
                    <%
                }
            %>>Completed</a></li>
        </ul>
        <%
            //判断清除按钮的状态
            if (completed_count > 0) {
        %>
        <button class="clear-completed" onclick="window.location.href='<%=request.getContextPath()%>/TodoServlet?method=deleteAllCompleted&last=<%=method%>'">
            Clear completed
        </button>
        <%
            }
        %>
    </footer>
    <%
        }
    %>
</section>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>