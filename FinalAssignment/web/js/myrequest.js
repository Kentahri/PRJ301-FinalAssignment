
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




