<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đơn nghỉ phép của tôi</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myrequest.css"/>
        <link href="${pageContext.request.contextPath}/css/pagging.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
        <script src="${pageContext.request.contextPath}/js/clock.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/myrequest.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/pagging.js" type="text/javascript"></script>
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
                <h2>Danh sách đơn nghỉ phép đã tạo</h2>
                <table id="myTable" class="request-table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Từ ngày</th>
                            <th>Đến ngày</th>
                            <th>Lý do</th>
                            <th>Ghi chú</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${requestScope.myRequests}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>
                                    <fmt:formatDate value="${r.startDate}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${r.endDate}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td>${r.reason}</td>
                                <td>${r.note}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.status == 0}">
                                            <span class="status pending">Chờ duyệt</span>
                                        </c:when>
                                        <c:when test="${r.status == 1}">
                                            <span class="status approved">Đã duyệt</span>
                                        </c:when>
                                        <c:when test="${r.status == 2}">
                                            <span class="status rejected">Từ chối</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${r.status == 0}">
                                        <button 
                                            class="view-btn"
                                            data-id="${r.id}"
                                            data-from="<fmt:formatDate value="${r.startDate}" pattern="yyyy-MM-dd"/>"
                                            data-to="<fmt:formatDate value="${r.endDate}" pattern="yyyy-MM-dd"/>"
                                            data-reason="${fn:escapeXml(r.reason)}"
                                            onclick="openEditModal(this)">
                                            Chi tiết
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="modal" class="modal-overlay" style="display:none;">
                <div class="modal-content">
                    <span class="close-x" onclick="closeModal()">×</span>
                    <h2>Chi tiết đơn nghỉ phép</h2>

                    <form id="editForm" method="post" action="myrequest">
                        <input type="hidden" name="id" id="request-id" />

                        <label>Từ ngày:</label>
                        <input type="date" name="fromDate" id="modal-from" required />

                        <label>Tới ngày:</label>
                        <input type="date" name="toDate" id="modal-to" required />

                        <label>Lý do:</label>
                        <textarea name="reason" id="modal-reason" rows="4" required></textarea>

                        <div class="button-group">
                            <button class="approve-btn" name="action" value="update" type="submit">
                                <i class="fas fa-pen"></i> Chỉnh sửa
                            </button>
                            <button class="reject-btn" name="action" value="delete" type="submit" onclick="return confirm('Bạn có chắc muốn xóa đơn này?')">
                                <i class="fas fa-trash"></i> Xóa
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
