// Mở sidebar
function openNav() {
    document.getElementById("sidebar").style.left = "0";
    document.getElementById("main").style.marginLeft = "250px";
}

// Đóng sidebar
function closeNav() {
    document.getElementById("sidebar").style.left = "-250px";
    document.getElementById("main").style.marginLeft = "0";
}

// Đóng sidebar khi click bên ngoài
document.addEventListener('click', function(event) {
    const sidebar = document.getElementById('sidebar');
    const openBtn = document.querySelector('.openbtn');
    
    // Nếu click bên ngoài sidebar và không phải là nút mở sidebar
    if (!sidebar.contains(event.target) && event.target !== openBtn) {
        closeNav();
    }
});

// Hiệu ứng số đếm cho thống kê
function animateValue(id, start, end, duration) {
    const obj = document.getElementById(id);
    let startTimestamp = null;
    const step = (timestamp) => {
        if (!startTimestamp) startTimestamp = timestamp;
        const progress = Math.min((timestamp - startTimestamp) / duration, 1);
        obj.innerHTML = Math.floor(progress * (end - start) + start);
        if (progress < 1) {
            window.requestAnimationFrame(step);
        }
    };
    window.requestAnimationFrame(step);
}

// Khi trang tải xong
document.addEventListener('DOMContentLoaded', function() {
    // Animation cho các số thống kê
    animateValue("course-count", 0, 150, 1000);
    animateValue("member-count", 0, 1200, 1000);
    animateValue("survey-count", 0, 30, 1000);
    
    // Thêm sự kiện cho các nút chi tiết
    const detailButtons = document.querySelectorAll('.task-status button:not(:disabled)');
    detailButtons.forEach(button => {
        button.addEventListener('click', function() {
            const taskName = this.parentElement.querySelector('span').textContent;
            alert(`Bạn đã chọn xem chi tiết công việc: ${taskName}`);
        });
    });
});