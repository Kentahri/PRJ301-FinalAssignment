<%-- 
    Document   : homepage
    Created on : Jun 12, 2025, 9:41:38 AM
    Author     : anhqu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lịch làm việc nhân viên</title>
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

            <c:if test="${permissions != null && permissions.contains('/website/history')}">
                <a href="history"><i class="fas fa-folder-open"></i> Lịch sử duyệt đơn</a>
            </c:if>

            <c:if test="${permissions != null && permissions.contains('/website/myrequest')}">
                <a href="myrequest"><i class="fas fa-list"></i> Lịch sử tạo đơn</a>
            </c:if>

            <c:if test="${permissions != null && permissions.contains('/website/agenda')}">
                <a href="agenda"><i class="fas fa-calendar-alt"></i> Lịch làm việc</a>
            </c:if>

            <c:if test="${permissions != null && permissions.contains('/admin/updaterole')}">
                <a href="../admin/updaterole"><i class="fas fa-user-shield"></i> Cấp quyền tài khoản</a>
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
                <h2>Lịch làm việc nhân viên</h2>

                <form method="post" action="agenda">
                    Từ ngày:
                    <input type="date" name="fromDate" value="${fromDate}" required>
                    Đến ngày:
                    <input type="date" name="toDate" value="${toDate}" required>
                    <button type="submit">Xem lịch</button>
                </form>

                <c:if test="${not empty error}">
                    <div style="color: red; margin-top:10px;">${error}</div>
                </c:if>

                <c:if test="${not empty agendaData}">
                    <table class="agenda-table">
                        <thead>
                            <tr>
                                <th>Ngày</th>
                                    <c:forEach var="emp" items="${employees}">
                                    <th>${emp.value}</th>
                                    </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="entry" items="${agendaData}">
                                <tr>
                                    <td>
                                        <fmt:formatDate value="${entry.key}" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <c:forEach var="eid" items="${employees.keySet()}">
                                        <c:set var="status" value="${entry.value[eid]}" />
                                        <td
                                            class="
                                            <c:choose>
                                                <c:when test='${status=="present"}'>status-present</c:when>
                                                <c:when test='${status=="leave"}'>status-leave</c:when>
                                                <c:when test='${status=="future"}'>status-future</c:when>
                                            </c:choose>
                                            ">
                                            <c:choose>
                                                <c:when test="${status=='present'}">✅</c:when>
                                                <c:when test="${status=='leave'}">❌</c:when>
                                                <c:when test="${status=='future'}">🔵</c:when>
                                            </c:choose>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </body>
</html>
