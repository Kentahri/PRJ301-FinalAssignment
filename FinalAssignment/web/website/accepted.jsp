<%-- 
    Document   : accepted
    Created on : Jun 13, 2025, 9:31:41 PM
    Author     : anhqu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lí đơn nghỉ phép</title>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
            />
        <link rel="stylesheet" href="../css/accepted.css"/>
        <link href="../css/pagging.css" rel="stylesheet" type="text/css"/>
        <script src="../js/clock.js" defer></script>
        <script src="../js/accepted.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/pagging.js"></script>
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
                    <button class="logout-btn">Đăng xuất</button>
                </form>
            </div>
            <div class="request-list">
                <h2 style="margin-bottom: 20px; color: #2c3e50">
                    Danh sách đơn chờ duyệt
                </h2>

                <table id="myTable" class="request-table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Nhân viên</th>
                            <th>Phòng ban</th>
                            <th>Từ ngày</th>
                            <th>Đến ngày</th>
                            <th>Ghi chú</th>
                            <th>Trạng thái</th>
                            <th>người xử lí</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${requests}" varStatus="loop">
                            <tr>
                                <td><strong class="info-highlight">${loop.index + 1}</strong></td>
                                <td><strong class="info-highlight">${r.createdBy.username}</strong></td>
                                <td><strong class="info-highlight">${r.department.name}</strong></td>
                                <td><strong class="info-highlight">${r.startDate}</strong></td>
                                <td><strong class="info-highlight">${r.endDate}</strong></td>
                                <td><strong class="info-highlight">${r.note}</strong></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.status == 0}">
                                            <span class="status pending">Chờ duyệt</span>
                                        </c:when>
                                        <c:when test="${r.status == 1}">
                                            <span class="status approved">Đã duyệt</span>
                                        </c:when>
                                        <c:when test="${r.status == 2}">
                                            <span class="status rejected">Đã từ chối</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td><strong class="info-highlight">
                                        <c:if test="${r.processBy != null}">
                                            ${r.processBy.username}
                                        </c:if>
                                        <c:if test="${r.processBy == null}">
                                            <em>Chưa xử lý</em>
                                        </c:if>
                                    </strong>
                                </td>
                                <td>
                                    <c:if test="${r.status == 0}">
                                        <button
                                            class="view-btn"
                                            onclick="openModal(${r.id}, '${r.createdBy.username}', '${r.startDate}', '${r.endDate}', '${r.reason}')">
                                            Chi tiết
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div id="modal" class="modal-overlay">
                <div class="modal-content">
                    <form method="post" action="accepted">
                        <span class="close-x" onclick="closeModal()">×</span>
                        <h2>Duyệt đơn nghỉ phép</h2>
                        <p id="modal-user"></p>

                        <input type="hidden" name="id" id="modal-id" />

                        <label>Từ ngày:</label>
                        <input type="date" id="modal-from" disabled />

                        <label>Tới ngày:</label>
                        <input type="date" id="modal-to" disabled />

                        <label>Lý do:</label>
                        <textarea id="modal-reason" rows="4" disabled></textarea>

                        <label>Ghi chú xử lý:</label>
                        <textarea name="note" rows="3"></textarea>

                        <div class="button-group">
                            <button class="reject-btn" name="action" value="reject">
                                <i class="fas fa-times"></i> Từ chối
                            </button>
                            <button class="approve-btn" name="action" value="approve">
                                <i class="fas fa-check"></i> Duyệt
                            </button>
                            <button class="close-btn" type="button" onclick="closeModal()">
                                <i class="fas fa-times-circle"></i> Đóng
                            </button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </body>
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            paginateTable("myTable", 5);
        });
    </script>
</html>
