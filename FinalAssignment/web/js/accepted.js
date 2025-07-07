function openModal(id, user, from, to, reason) {
    const modal = document.getElementById('modal');
    modal.style.display = 'flex';

    document.getElementById('modal-id').value = id;
    document.getElementById('modal-user').innerText = "Người tạo: " + user;
    document.getElementById('modal-from').value = from;
    document.getElementById('modal-to').value = to;
    document.getElementById('modal-reason').value = reason;
}

function closeModal() {
    document.getElementById('modal').style.display = 'none';
}



