<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo tài khoản mới</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
        <style>
            .login-box select {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
                background-color: white;
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
            }

            .login-box label {
                display: block;
                margin: 5px 0;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <form class="login-box" method="post">
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success-message">${success}</div>
            </c:if>

            <h2>Tạo tài khoản</h2>

            <input type="text" placeholder="Tên đăng nhập" name="username" required />

            <input type="password" placeholder="Mật khẩu" name="password" required />

            <input type="password" placeholder="Xác nhận mật khẩu" name="confirmpassword" required />

            <input type="text" placeholder="Tên hiển thị" name="displayname" />

            <input type="text" placeholder="Tên nhân viên" name="employeename" required />

            <select name="department" required>
                <option value="">-- Chọn phòng ban --</option>
                <c:forEach var="d" items="${departments}">
                    <option value="${d.id}">${d.name}</option>
                </c:forEach>
            </select>

            <label>Vai trò:</label>
            <select name="roles" multiple size="4">
                <c:forEach var="r" items="${roles}">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>

            <input class="button" type="submit" value="Tạo tài khoản">
        </form>
    </body>
</html>
