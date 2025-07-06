<%-- 
    Document   : login.jsp
    Created on : Jun 11, 2025, 8:43:14 PM
    Author     : anhqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
    </head>
    <body>
        <form class="login-box" action="login" method="post">
            <%
    String message = request.getParameter("message");
    if (message == null) {
        message = (String) request.getAttribute("message");
    }
    if ("not_logged_in".equals(message)) {
            %>
            <div class="error-message">Bạn cần đăng nhập để tiếp tục!</div>
            <%
                } else if ("fail".equals(message)) {
            %>
            <div class="error-message">Đăng nhập thất bại. Vui lòng kiểm tra lại tài khoản và mật khẩu!</div>
            <%
                }
            %>
            <h2>Đăng nhập</h2>
            <input type="text" placeholder="Tên tài khoản" name="user"/><br>
            <input type="password" placeholder="Mật khẩu" name="pass"/><br>
            <input class="button" type="submit" value="Đăng nhập"><br>
        </form>

    </body>
</html>
    