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
    document.addEventListener('click', function(e) {
        // Start consultation buttons
        if (e.target.closest('.appointment-actions .btn-primary')) {
            e.preventDefault();
            const appointmentItem = e.target.closest('.appointment-item');
            const clientName = appointmentItem.querySelector('.appointment-client strong').textContent;
            alert(`Bắt đầu phiên tư vấn với ${clientName}`);
        }
        
        // Confirm appointment button
        if (e.target.closest('.appointment-actions .btn-success')) {
            e.preventDefault();
            const appointmentItem = e.target.closest('.appointment-item');
            
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
        }
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
    
    // Modal appointment form
    const appointmentModal = new bootstrap.Modal(document.getElementById('appointmentModal'));
    const appointmentForm = document.getElementById('appointmentForm');
    const saveBtn = document.getElementById('saveBtn');
    const deleteBtn = document.getElementById('deleteBtn');
    const modalTitle = document.getElementById('appointmentModalLabel');
    
    // Mở modal thêm lịch hẹn
    document.querySelector('.btn-primary[data-bs-target="#appointmentModal"]')?.addEventListener('click', function() {
        resetAppointmentForm();
        modalTitle.textContent = 'Thêm lịch hẹn mới';
        deleteBtn.style.display = 'none';
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
            e.preventDefault();
            const appointmentItem = e.target.closest('.appointment-item');
            const appointmentId = appointmentItem.dataset.id || Date.now().toString();
            
            // Lấy thông tin từ lịch hẹn hiện có
            const clientName = appointmentItem.querySelector('.appointment-client strong').textContent;
            const clientAge = appointmentItem.querySelector('.appointment-client').textContent.match(/\d+/)[0];
            const timeRange = appointmentItem.querySelector('.appointment-time').textContent.split(' - ');
            const riskLevel = appointmentItem.querySelector('.appointment-tag').className.includes('low') ? 'low' : 
                             appointmentItem.querySelector('.appointment-tag').className.includes('medium') ? 'medium' : 'high';
            
            const notesElement = appointmentItem.querySelector('.appointment-note');
            const notes = notesElement ? notesElement.textContent.replace('Tái khám', '').trim() : '';
            
            // Điền dữ liệu vào form
            fillAppointmentForm({
                id: appointmentId,
                clientId: '1', // Giả định ID khách hàng
                clientName: `${clientName} (${clientAge} tuổi)`,
                date: '2023-06-15', // Ngày mặc định
                startTime: timeRange[0],
                endTime: timeRange[1],
                type: 'online', // Loại mặc định
                riskLevel: riskLevel,
                notes: notes,
                status: appointmentItem.querySelector('.badge').textContent === 'Đã xác nhận' ? 'confirmed' : 'pending'
            });
            
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
            clientName: document.querySelector(`#clientSelect option[value="${document.getElementById('clientSelect').value}"]`).text,
            date: document.getElementById('appointmentDate').value,
            startTime: document.getElementById('startTime').value,
            endTime: document.getElementById('endTime').value,
            type: document.getElementById('appointmentType').value,
            riskLevel: document.getElementById('riskLevel').value,
            notes: document.getElementById('appointmentNotes').value,
            status: 'pending'
        };
    }
    
    // Hàm thêm lịch hẹn mới
    function addNewAppointment(data) {
        // Tạo HTML cho lịch hẹn mới và thêm vào danh sách
        const newAppointment = createAppointmentItem({
            id: Date.now().toString(),
            clientName: data.clientName,
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
    
    // Hàm cập nhật lịch hẹn
    function updateAppointment(data) {
        // Tìm và cập nhật lịch hẹn trong danh sách
        const appointmentItem = document.querySelector(`.appointment-item[data-id="${data.id}"]`);
        if (appointmentItem) {
            appointmentItem.querySelector('.appointment-client strong').textContent = 
                data.clientName.split(' (')[0];
            
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
    
    // Hàm xóa lịch hẹn
    function deleteAppointment(id) {
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
        
        const ageMatch = data.clientName.match(/\((\d+) tuổi\)/);
        const ageText = ageMatch ? ageMatch[0] : '';
        
        item.innerHTML = `
            <div class="appointment-details">
                <div class="appointment-time">${data.startTime} - ${data.endTime}</div>
                <div class="appointment-client">
                    <strong>${data.clientName.split(' (')[0]}</strong> ${ageText}
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