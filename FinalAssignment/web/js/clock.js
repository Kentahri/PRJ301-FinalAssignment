
function updateClock() {
    const now = new Date();
    const days = [
        "Chủ Nhật",
        "Thứ Hai",
        "Thứ Ba",
        "Thứ Tư",
        "Thứ Năm",
        "Thứ Sáu",
        "Thứ Bảy",
    ];
    const dayName = days[now.getDay()];

    const day = String(now.getDate()).padStart(2, "0");
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const year = now.getFullYear();

    const hours = String(now.getHours()).padStart(2, "0");
    const minutes = String(now.getMinutes()).padStart(2, "0");
    const seconds = String(now.getSeconds()).padStart(2, "0");

    const formatted = `${dayName}, ${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
    document.getElementById("clock-text").textContent = formatted;
}

setInterval(updateClock, 1000);
updateClock();


