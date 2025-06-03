document.addEventListener('DOMContentLoaded', function() {
    // Toggle sidebar on mobile
    const sidebar = document.querySelector('.sidebar');
    const sidebarToggle = document.querySelector('.sidebar-toggle');
    
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', function() {
            sidebar.classList.toggle('show');
        });
    }
    
    // Mark notifications as read
    const notificationItems = document.querySelectorAll('.notification-item.unread');
    
    notificationItems.forEach(item => {
        item.addEventListener('click', function() {
            this.classList.remove('unread');
            
            // Update notification badge count
            const badge = document.querySelector('.notification-bell .badge');
            if (badge) {
                let count = parseInt(badge.textContent);
                if (count > 0) {
                    count--;
                    badge.textContent = count;
                    
                    if (count === 0) {
                        badge.style.display = 'none';
                    }
                }
            }
        });
    });
    
    // Appointment actions
    const startConsultationBtns = document.querySelectorAll('.appointment-actions .btn-primary');
    
    startConsultationBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const appointmentItem = this.closest('.appointment-item');
            const clientName = appointmentItem.querySelector('.appointment-client strong').textContent;
            
            // In a real app, this would redirect to the consultation page
            alert(`Bắt đầu phiên tư vấn với ${clientName}`);
        });
    });
    
    // Confirm appointment button
    const confirmAppointmentBtns = document.querySelectorAll('.appointment-actions .btn-success');
    
    confirmAppointmentBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const appointmentItem = this.closest('.appointment-item');
            
            // Change status to confirmed
            const statusBadge = appointmentItem.querySelector('.badge');
            statusBadge.classList.remove('bg-warning', 'text-dark');
            statusBadge.classList.add('bg-success');
            statusBadge.textContent = 'Đã xác nhận';
            
            // Change button to start consultation
            const actionsDiv = appointmentItem.querySelector('.appointment-actions');
            actionsDiv.innerHTML = `
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết">
                    <i class="fas fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-primary" title="Bắt đầu tư vấn">
                    <i class="fas fa-play"></i> Bắt đầu
                </button>
            `;
            
            // Add event listener to the new button
            const newStartBtn = actionsDiv.querySelector('.btn-primary');
            newStartBtn.addEventListener('click', function(e) {
                e.preventDefault();
                const clientName = appointmentItem.querySelector('.appointment-client strong').textContent;
                alert(`Bắt đầu phiên tư vấn với ${clientName}`);
            });
        });
    });
    
    // Quick action buttons
    const quickActionBtns = document.querySelectorAll('.quick-actions .btn');
    
    quickActionBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const actionText = this.textContent.trim();
            alert(`Thực hiện hành động: ${actionText}`);
        });
    });
});
// Thêm code mới vào file consultant.js
document.addEventListener('DOMContentLoaded', function() {
    // ... (giữ nguyên code cũ)
    
    // Modal appointment form
    const appointmentModal = new bootstrap.Modal(document.getElementById('appointmentModal'));
    const appointmentForm = document.getElementById('appointmentForm');
    const saveBtn = document.getElementById('saveBtn');
    const deleteBtn = document.getElementById('deleteBtn');
    const modalTitle = document.getElementById('appointmentModalLabel');
    
    // Mở modal thêm lịch hẹn
    document.querySelector('.btn-primary[data-target="#appointmentModal"]')?.addEventListener('click', function() {
        resetAppointmentForm();
        modalTitle.textContent = 'Thêm lịch hẹn mới';
        deleteBtn.style.display = 'none';
        appointmentModal.show();
    });
    
    // Xử lý nút Lưu
    saveBtn.addEventListener('click', function() {
        if (appointmentForm.checkValidity()) {
            const appointmentData = getAppointmentFormData();
            
            if (document.getElementById('appointmentId').value) {
                // Cập nhật lịch hẹn
                updateAppointment(appointmentData);
            } else {
                // Thêm lịch hẹn mới
                addNewAppointment(appointmentData);
            }
            
            appointmentModal.hide();
        } else {
            appointmentForm.classList.add('was-validated');
        }
    });
    
    // Xử lý nút Xóa
    deleteBtn.addEventListener('click', function() {
        if (confirm('Bạn có chắc chắn muốn xóa lịch hẹn này?')) {
            const appointmentId = document.getElementById('appointmentId').value;
            deleteAppointment(appointmentId);
            appointmentModal.hide();
        }
    });
    
    // Xử lý khi click nút "Xem chi tiết" (sửa lịch hẹn)
    document.addEventListener('click', function(e) {
        if (e.target.closest('.appointment-actions .btn-outline-primary')) {
            const appointmentItem = e.target.closest('.appointment-item');
            const appointmentId = appointmentItem.dataset.id || '1'; // Lấy ID từ data attribute
            
            // Giả lập dữ liệu - trong thực tế sẽ gọi API hoặc lấy từ store
            const appointmentData = {
                id: appointmentId,
                clientId: '1',
                clientName: appointmentItem.querySelector('.appointment-client strong').textContent,
                date: '2023-06-15',
                startTime: '09:00',
                endTime: '10:00',
                type: 'online',
                riskLevel: 'low',
                notes: 'Tái khám'
            };
            
            fillAppointmentForm(appointmentData);
            modalTitle.textContent = 'Chỉnh sửa lịch hẹn';
            deleteBtn.style.display = 'block';
            appointmentModal.show();
        }
    });
    
    // Hàm reset form
    function resetAppointmentForm() {
        appointmentForm.reset();
        document.getElementById('appointmentId').value = '';
        appointmentForm.classList.remove('was-validated');
    }
    
    // Hàm điền dữ liệu vào form
    function fillAppointmentForm(data) {
        document.getElementById('appointmentId').value = data.id;
        document.getElementById('clientSelect').value = data.clientId;
        document.getElementById('appointmentDate').value = data.date;
        document.getElementById('startTime').value = data.startTime;
        document.getElementById('endTime').value = data.endTime;
        document.getElementById('appointmentType').value = data.type;
        document.getElementById('riskLevel').value = data.riskLevel;
        document.getElementById('appointmentNotes').value = data.notes || '';
    }
    
    // Hàm lấy dữ liệu từ form
    function getAppointmentFormData() {
        return {
            id: document.getElementById('appointmentId').value,
            clientId: document.getElementById('clientSelect').value,
            date: document.getElementById('appointmentDate').value,
            startTime: document.getElementById('startTime').value,
            endTime: document.getElementById('endTime').value,
            type: document.getElementById('appointmentType').value,
            riskLevel: document.getElementById('riskLevel').value,
            notes: document.getElementById('appointmentNotes').value
        };
    }
    
    // Hàm thêm lịch hẹn mới (giả lập)
    function addNewAppointment(data) {
        // Trong thực tế sẽ gọi API ở đây
        console.log('Thêm lịch hẹn mới:', data);
        
        // Tạo HTML cho lịch hẹn mới và thêm vào danh sách
        const newAppointment = createAppointmentItem({
            id: Date.now().toString(),
            clientName: document.querySelector(`#clientSelect option[value="${data.clientId}"]`).text,
            date: data.date,
            startTime: data.startTime,
            endTime: data.endTime,
            type: data.type,
            riskLevel: data.riskLevel,
            notes: data.notes,
            status: 'pending'
        });
        
        document.querySelector('.appointment-list').prepend(newAppointment);
        alert('Đã thêm lịch hẹn mới thành công!');
    }
    
    // Hàm cập nhật lịch hẹn (giả lập)
    function updateAppointment(data) {
        // Trong thực tế sẽ gọi API ở đây
        console.log('Cập nhật lịch hẹn:', data);
        
        // Tìm và cập nhật lịch hẹn trong danh sách
        const appointmentItem = document.querySelector(`.appointment-item[data-id="${data.id}"]`);
        if (appointmentItem) {
            appointmentItem.querySelector('.appointment-client strong').textContent = 
                document.querySelector(`#clientSelect option[value="${data.clientId}"]`).text.split(' (')[0];
            
            appointmentItem.querySelector('.appointment-time').textContent = 
                `${data.startTime} - ${data.endTime}`;
            
            // Cập nhật tag nguy cơ
            const riskTag = appointmentItem.querySelector('.appointment-tag');
            riskTag.className = 'appointment-tag risk-' + data.riskLevel;
            riskTag.textContent = 
                data.riskLevel === 'low' ? 'Nguy cơ thấp' : 
                data.riskLevel === 'medium' ? 'Nguy cơ trung bình' : 'Nguy cơ cao';
            
            // Cập nhật ghi chú nếu có
            if (data.notes) {
                let noteElement = appointmentItem.querySelector('.appointment-note');
                if (!noteElement) {
                    noteElement = document.createElement('span');
                    noteElement.className = 'appointment-note';
                    noteElement.innerHTML = '<i class="fas fa-info-circle"></i> ';
                    appointmentItem.querySelector('.appointment-info').appendChild(noteElement);
                }
                noteElement.textContent = data.notes;
            }
        }
        
        alert('Đã cập nhật lịch hẹn thành công!');
    }
    
    // Hàm xóa lịch hẹn (giả lập)
    function deleteAppointment(id) {
        // Trong thực tế sẽ gọi API ở đây
        console.log('Xóa lịch hẹn:', id);
        
        const appointmentItem = document.querySelector(`.appointment-item[data-id="${id}"]`);
        if (appointmentItem) {
            appointmentItem.remove();
            alert('Đã xóa lịch hẹn thành công!');
        }
    }
    
    // Hàm tạo HTML cho 1 lịch hẹn
    function createAppointmentItem(data) {
        const item = document.createElement('div');
        item.className = 'appointment-item';
        item.dataset.id = data.id;
        
        const riskText = 
            data.riskLevel === 'low' ? 'Nguy cơ thấp' : 
            data.riskLevel === 'medium' ? 'Nguy cơ trung bình' : 'Nguy cơ cao';
        
        const statusBadge = data.status === 'confirmed' ? 
            '<span class="badge bg-success">Đã xác nhận</span>' : 
            '<span class="badge bg-warning text-dark">Chờ xác nhận</span>';
        
        item.innerHTML = `
            <div class="appointment-details">
                <div class="appointment-time">${data.startTime} - ${data.endTime}</div>
                <div class="appointment-client">
                    <strong>${data.clientName.split(' (')[0]}</strong> ${data.clientName.match(/\((\d+) tuổi\)/)[0]}
                    ${statusBadge}
                </div>
                <div class="appointment-info">
                    <span class="appointment-tag risk-${data.riskLevel}">${riskText}</span>
                    ${data.notes ? `<span class="appointment-note"><i class="fas fa-info-circle"></i> ${data.notes}</span>` : ''}
                </div>
            </div>
            <div class="appointment-actions">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết">
                    <i class="fas fa-eye"></i>
                </button>
                ${data.status === 'confirmed' ? 
                    '<button class="btn btn-sm btn-primary" title="Bắt đầu tư vấn"><i class="fas fa-play"></i> Bắt đầu</button>' : 
                    '<button class="btn btn-sm btn-success" title="Xác nhận"><i class="fas fa-check"></i></button>'}
            </div>
        `;
        
        return item;
    }
});