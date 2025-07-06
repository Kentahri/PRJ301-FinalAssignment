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
        <script src="../js/clock.js" defer></script>
        <script src="../js/accepted.js" defer></script>
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
                    <span>Nguyễn Văn A</span>
                </div>
                <button class="logout-btn">Đăng xuất</button>
            </div>
            <div class="request-list">
                <h2 style="margin-bottom: 20px; color: #2c3e50">
                    Danh sách đơn chờ duyệt
                </h2>

                <table class="request-table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Nhân viên</th>
                            <th>Phòng ban</th>
                            <th>Từ ngày</th>
                            <th>Đến ngày</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><strong class="info-highlight">1</strong></td>
                            <td><strong class="info-highlight">mr Tèo</strong></td>
                            <td><strong class="info-highlight">Phòng IT</strong></td>
                            <td><strong class="info-highlight">2025-06-12</strong></td>
                            <td><strong class="info-highlight">2025-06-14</strong></td>
                            <td>
                                <span class="status pending">Chờ duyệt</span>
                            </td>
                            <td>
                                <button
                                    class="view-btn"
                                    onclick="openModal('mr Tèo', 'Phòng IT', '2025-06-12', '2025-06-14', 'Về quê có việc')"
                                    >
                                    Chi tiết
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div id="modal" class="modal-overlay">
                <div class="modal-content">
                    <span class="close-x" onclick="closeModal()">×</span>
                    <h2>Duyệt đơn nghỉ phép</h2>
                    <p id="modal-user"></p>

                    <label>Từ ngày:</label>
                    <input type="date" id="modal-from" disabled />

                    <label>Tới ngày:</label>
                    <input type="date" id="modal-to" disabled />

                    <label>Lý do:</label>
                    <textarea id="modal-reason" rows="4" disabled></textarea>

                    <div class="button-group">
                        <button class="reject-btn" onclick="alert('Đã từ chối đơn')">
                            <i class="fas fa-times"></i> Từ chối
                        </button>
                        <button class="approve-btn" onclick="alert('Đã duyệt đơn')">
                            <i class="fas fa-check"></i> Duyệt
                        </button>
                        <button class="close-btn" onclick="closeModal()">
                            <i class="fas fa-times-circle"></i> Đóng
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
