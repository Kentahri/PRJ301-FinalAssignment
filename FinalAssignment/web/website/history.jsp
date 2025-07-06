<%-- 
    Document   : history
    Created on : Jun 14, 2025, 10:18:01 AM
    Author     : anhqu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lịch sử tạo đơn</title>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
            />
        <link rel="stylesheet" href="../css/history.css"/>
        <script src="../js/clock.js"></script>
        <script src="../js/history.js"></script>
    </head>
    <body>
        <div class="sidebar">
            <h2>Menu</h2>
            <c:set var="permissions" value="${sessionScope.allowedEntrypoints}" />
            <c:if test="${permissions != null && permissions.contains('/website/create')}">
                <a href="create"><i class="fas fa-file-alt"></i> Tạo đơn nghỉ phép</a>
            </c:if>
            <c:if test="${permissions != null && permissions.contains('/website/accepted')}">
                <a href="accepted"><i class="fas fa-check-circle"></i> Duyệt đơn nghỉ phép</a>
            </c:if>
            <c:if test="${permissions != null && permissions.contains('/website/history')}">
                <a href="history"><i class="fas fa-folder-open"></i> Lịch sử duyệt đơn</a>
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
                <h2 style="margin-bottom: 20px; color: #2c3e50">Lịch sử tạo đơn</h2>
                <table class="request-table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Từ ngày</th>
                            <th>Đến ngày</th>
                            <th>Lý do</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><strong style="color: #1e3a8a">1</strong></td>
                            <td><strong style="color: #1e3a8a">2025-06-17</strong></td>
                            <td><strong style="color: #1e3a8a">2025-06-18</strong></td>
                            <td><strong style="color: #1e3a8a">Về quê có việc</strong></td>
                            <td><span class="status pending">Chờ duyệt</span></td>
                            <td>
                                <button
                                    class="view-btn"
                                    onclick="openDetailModal('2025-06-17', '2025-06-18', 'Về quê có việc')"
                                    >
                                    Chi tiết
                                </button>
                                <button
                                    class="edit-btn"
                                    onclick="openEditModal('2025-06-17', '2025-06-18', 'Về quê có việc')"
                                    >
                                    Sửa đơn
                                </button>
                            </td>
                        </tr>
                        <!-- Thêm các dòng khác -->
                    </tbody>
                </table>
            </div>
            <!-- Modal Chi tiết -->
            <div id="detail-modal" class="modal" style="display: none">
                <div class="modal-content">
                    <h3 style="margin-bottom: 10px; color: #2c3e50">
                        Chi tiết đơn nghỉ phép
                    </h3>
                    <p><strong>Từ ngày:</strong> <span id="detail-from"></span></p>
                    <p><strong>Đến ngày:</strong> <span id="detail-to"></span></p>
                    <p><strong>Lý do:</strong> <span id="detail-reason"></span></p>
                    <div class="button-group">
                        <button class="close-btn" onclick="closeDetailModal()">Đóng</button>
                    </div>
                </div>
            </div>

            <!-- Modal Sửa đơn -->
            <div id="edit-modal" class="modal" style="display: none">
                <div class="modal-content">
                    <h3 style="margin-bottom: 10px; color: #2c3e50">Sửa đơn nghỉ phép</h3>
                    <form id="edit-form">
                        <label for="edit-from">Từ ngày:</label>
                        <input type="date" id="edit-from" required />

                        <label for="edit-to">Đến ngày:</label>
                        <input type="date" id="edit-to" required />

                        <label for="edit-reason">Lý do:</label>
                        <textarea id="edit-reason" rows="3" required></textarea>

                        <div class="button-group">
                            <button type="button" class="approve-btn" onclick="submitEdit()">
                                Cập nhật
                            </button>
                            <button
                                type="button"
                                class="close-btn"
                                onclick="closeEditModal()"
                                >
                                Đóng
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
