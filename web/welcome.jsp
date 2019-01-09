<%--
  Created by IntelliJ IDEA.
  User: xiu
  Date: 2019/1/9
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TodoMVC</title>
</head>
<body>
正在跳转...
<script language="javascript">
    location.href = "<%=request.getContextPath()%>/TodoServlet?method=all";
</script>
</body>
</html>
