function openDetailModal(from, to, reason) {
    document.getElementById("detail-from").innerText = from;
    document.getElementById("detail-to").innerText = to;
    document.getElementById("detail-reason").innerText = reason;
    document.getElementById("detail-modal").style.display = "flex";
}

function closeDetailModal() {
    document.getElementById("detail-modal").style.display = "none";
}

// Sửa đơn
function openEditModal(from, to, reason) {
    document.getElementById("edit-from").value = from;
    document.getElementById("edit-to").value = to;
    document.getElementById("edit-reason").value = reason;
    document.getElementById("edit-modal").style.display = "flex";
}

function closeEditModal() {
    document.getElementById("edit-modal").style.display = "none";
}

function submitEdit() {
    const from = document.getElementById("edit-from").value;
    const to = document.getElementById("edit-to").value;
    const reason = document.getElementById("edit-reason").value;
    alert(
            `Cập nhật đơn:\nTừ ngày: ${from}\nĐến ngày: ${to}\nLý do: ${reason}`
            );
    closeEditModal();
}


