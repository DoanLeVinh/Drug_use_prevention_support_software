// Mở và đóng sidebar
function openNav() {
    document.getElementById("sidebar").style.left = "0";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("sidebar").style.left = "-250px";
    document.getElementById("main").style.marginLeft = "0";
}

// Xử lý khảo sát
function editSurvey() {
    alert("Chức năng chỉnh sửa khảo sát sẽ được mở.");
    // Có thể thêm logic chuyển hướng hoặc hiển thị form chỉnh sửa ở đây
}

function deleteSurvey() {
    if (confirm("Bạn có chắc chắn muốn xóa khảo sát này không?")) {
        alert("Khảo sát đã được xóa.");
        // Có thể thêm logic xóa khảo sát từ cơ sở dữ liệu ở đây
    }
}

// Thêm câu hỏi động (nếu cần)
document.addEventListener('DOMContentLoaded', function() {
    // Có thể thêm logic để thêm câu hỏi động vào form
    const addQuestionBtn = document.createElement('button');
    addQuestionBtn.textContent = 'Thêm Câu Hỏi';
    addQuestionBtn.type = 'button';
    addQuestionBtn.className = 'add-question-btn';
    addQuestionBtn.onclick = function() {
        const questionCount = document.querySelectorAll('.survey-questions input[type="text"]').length + 1;
        const newQuestion = document.createElement('div');
        
        const label = document.createElement('label');
        label.setAttribute('for', `question${questionCount}`);
        label.textContent = `Câu hỏi ${questionCount}:`;
        
        const input = document.createElement('input');
        input.type = 'text';
        input.id = `question${questionCount}`;
        input.name = `question${questionCount}`;
        
        newQuestion.appendChild(label);
        newQuestion.appendChild(input);
        
        document.querySelector('.survey-questions').appendChild(newQuestion);
    };
    
    const surveyQuestions = document.querySelector('.survey-questions');
    if (surveyQuestions) {
        surveyQuestions.parentNode.insertBefore(addQuestionBtn, surveyQuestions.nextSibling);
    }
    
    // Xử lý submit form
    const surveyForm = document.querySelector('.add-survey-form form');
    if (surveyForm) {
        surveyForm.addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Khảo sát đã được tạo thành công!');
            // Có thể thêm logic gửi dữ liệu form đến server ở đây
        });
    }
});