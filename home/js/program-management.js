// Mở và đóng sidebar
function openNav() {
    document.getElementById("sidebar").style.left = "0";
    document.getElementById("main").style.marginLeft = "250px";
    document.body.style.overflow = "hidden";
}

function closeNav() {
    document.getElementById("sidebar").style.left = "-250px";
    document.getElementById("main").style.marginLeft = "0";
    document.body.style.overflow = "auto";
}

// Đóng sidebar khi click bên ngoài
document.addEventListener('click', function(event) {
    const sidebar = document.getElementById('sidebar');
    const openBtn = document.querySelector('.openbtn');
    
    if (event.target !== sidebar && !sidebar.contains(event.target) && 
        event.target !== openBtn && !openBtn.contains(event.target)) {
        closeNav();
    }
});

// Xử lý chức năng chỉnh sửa chương trình
function editProgram() {
    const row = event.target.closest('tr');
    const programName = row.cells[0].textContent;
    const startDate = row.cells[1].textContent;
    const endDate = row.cells[2].textContent;
    
    // Hiển thị form chỉnh sửa (trong thực tế có thể dùng modal)
    alert(`Chỉnh sửa chương trình: ${programName}\nNgày bắt đầu: ${startDate}\nNgày kết thúc: ${endDate}`);
}

// Xử lý chức năng xóa chương trình
function deleteProgram() {
    if (confirm('Bạn có chắc chắn muốn xóa chương trình này?')) {
        const row = event.target.closest('tr');
        row.remove();
        showToast('Chương trình đã được xóa thành công!');
    }
}

// Xử lý form thêm chương trình mới
document.querySelector('.add-program-form form').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const programName = document.getElementById('program-name').value;
    const startDate = document.getElementById('start-date').value;
    const endDate = document.getElementById('end-date').value;
    
    // Kiểm tra hợp lệ
    if (!programName || !startDate || !endDate) {
        showToast('Vui lòng điền đầy đủ thông tin!', 'error');
        return;
    }
    
    if (new Date(startDate) > new Date(endDate)) {
        showToast('Ngày kết thúc phải sau ngày bắt đầu!', 'error');
        return;
    }
    
    // Thêm vào bảng (tạm thời)
    const table = document.querySelector('.program-list table tbody');
    const newRow = table.insertRow();
    
    newRow.innerHTML = `
        <td>${programName}</td>
        <td>${formatDate(startDate)}</td>
        <td>${formatDate(endDate)}</td>
        <td>
            <button onclick="editProgram()">Chỉnh sửa</button>
            <button onclick="deleteProgram()">Xóa</button>
        </td>
    `;
    
    // Reset form
    this.reset();
    showToast('Thêm chương trình mới thành công!');
});

// Hàm định dạng ngày từ input date sang dd/mm/yyyy
function formatDate(dateString) {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}

// Hàm hiển thị thông báo
function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    
    setTimeout(() => {
        toast.remove();
    }, 3000);
}

// Tắt sidebar khi load trang trên mobile
window.addEventListener('load', function() {
    if (window.innerWidth <= 768) {
        closeNav();
    }
});

// Xử lý responsive khi thay đổi kích thước màn hình
window.addEventListener('resize', function() {
    if (window.innerWidth > 768) {
        document.getElementById("sidebar").style.left = "-250px";
        document.getElementById("main").style.marginLeft = "0";
    }
});