function paginateTable(tableId, rowsPerPage = 10) {
    const table = document.getElementById(tableId);
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);

    let currentPage = 1;

    const paginationContainer = document.createElement("div");
    paginationContainer.className = "pagination";

    function renderTable() {
        tbody.innerHTML = "";
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        rows.slice(start, end).forEach(row => tbody.appendChild(row));
    }

    function renderPaginationButtons() {
        paginationContainer.innerHTML = "";

        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = (i === currentPage) ? "active" : "";
            btn.addEventListener("click", () => {
                currentPage = i;
                renderTable();
                renderPaginationButtons();
            });
            paginationContainer.appendChild(btn);
        }
    }

    renderTable();
    renderPaginationButtons();

    // Thêm nút phân trang vào sau bảng
    table.parentNode.appendChild(paginationContainer);
}
