
function openEditModal(button) {
    const modal = document.getElementById('modal');
    modal.style.display = 'flex';

    document.getElementById('request-id').value = button.dataset.id;
    document.getElementById('modal-from').value = button.dataset.from;
    document.getElementById('modal-to').value = button.dataset.to;
    document.getElementById('modal-reason').value = button.dataset.reason;
}



function closeModal() {
    document.getElementById('modal').style.display = 'none';
}

function validateForm() {
    const fromDateStr = document.getElementById("modal-from").value;
    const toDateStr = document.getElementById("modal-to").value;

    if (!fromDateStr || !toDateStr) {
        alert("Ngày bắt đầu và kết thúc không được để trống!");
        return false;
    }

    const fromDate = new Date(fromDateStr);
    const toDate = new Date(toDateStr);
    const now = new Date();

    // Tính thời gian hiện tại + 24 giờ
    const minDate = new Date(now.getTime() + 24 * 60 * 60 * 1000);

    if (fromDate < minDate) {
        alert("Ngày bắt đầu phải ít nhất sau 24 giờ kể từ bây giờ!");
        return false;
    }

    if (fromDate >= toDate) {
        alert("Ngày kết thúc phải sau ngày bắt đầu!");
        return false;
    }

    return true;
}



