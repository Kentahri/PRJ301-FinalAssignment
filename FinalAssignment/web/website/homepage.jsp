<%-- 
    Document   : homepage
    Created on : Jun 12, 2025, 9:41:38 AM
    Author     : anhqu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
            />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homepage.css"/>
        <script src="${pageContext.request.contextPath}/js/clock.js" defer></script>
    </head>
    <body>
        <div class="sidebar">
            <h2>Menu</h2>
            <c:set var="permissions" value="${sessionScope.allowedEntrypoints}" />

            <a href="homepage"><i class="fas fa-home"></i> Trang chủ</a>

            <c:if test="${permissions != null && permissions.contains('/website/create')}">
                <a href="create"><i class="fas fa-file-alt"></i> Tạo đơn nghỉ phép</a>
            </c:if>

            <c:if test="${permissions != null && permissions.contains('/website/accepted')}">
                <a href="accepted"><i class="fas fa-check-circle"></i> Duyệt đơn nghỉ phép</a>
            </c:if>

            <c:if test="${permissions != null && permissions.contains('/website/myrequest')}">
                <a href="myrequest"><i class="fas fa-list"></i> Lịch sử tạo đơn</a>
            </c:if>

            <c:if test="${permissions != null && permissions.contains('/website/agenda')}">
                <a href="agenda"><i class="fas fa-calendar-alt"></i> Lịch làm việc</a>
            </c:if>
        </div>

        <div class="main">
            <div class="topbar">
                <div class="clock" id="clock">
                    <i class="fas fa-calendar-days"></i>
                    <span id="clock-text"></span>
                </div>
                <div class="user-info">
                    <i class="fas fa-user"></i>
                    <span>${sessionScope.account.displayname}</span>
                </div>
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="logout-btn">Đăng xuất</button>
                </form>
            </div>

            <div class="content">
                <c:if test="${not empty requestScope.welcomeMessage}">
                    <div class="notification">
                        <i class="fas fa-bell"></i>
                        <span>${requestScope.welcomeMessage}</span>
                        <button class="close-btn" onclick="this.parentElement.style.display = 'none'">×</button>
                    </div>
                </c:if>
                <c:forEach var="ms" items="${requestScope.message}">
                    <div class="notification">
                        <i class="fas fa-bell"></i>
                        <span>${ms}</span>
                    <button
                        class="close-btn"
                        onclick="this.parentElement.style.display = 'none'"
                        >
                        ×
                    </button>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
