package com.drug.drug.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.drug.drug.entity.Answer;
import com.drug.drug.entity.Course;
import com.drug.drug.entity.Question;
import com.drug.drug.entity.Test;
import com.drug.drug.entity.User;
import com.drug.drug.repository.AnswerRepository;
import com.drug.drug.repository.CourseRepository;
import com.drug.drug.repository.QuestionRepository;
import com.drug.drug.repository.TestRepository;
import com.drug.drug.repository.UserRepository;

@Configuration
public class DataInitializer {

    // Bean này giữ nguyên để khởi tạo User
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin123")) {
                User admin = new User();
                admin.setUsername("admin123");
                // Quan trọng: Sử dụng NoOpPasswordEncoder nên không cần encode
                // Nếu bạn đổi lại BCrypt thì bỏ comment dòng dưới và xóa dòng trên
                admin.setPassword("admin123");
                // admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("admin");
                admin.setEmail("admin@example.com");
                admin.setSdt("0123456789");
                admin.setAddress("Admin Address");
                admin.setSex("Other");
                admin.setBirthday(java.time.LocalDate.of(2000, 1, 1));
                userRepository.save(admin);
                System.out.println("✅ Admin user initialized: username=admin123, password=admin123");
            } else {
                System.out.println("✅ Admin user already exists.");
            }

            if (!userRepository.existsByUsername("member123")) {
                User member = new User();
                member.setUsername("member123");
                member.setPassword("member123");
                // member.setPassword(passwordEncoder.encode("member123"));
                member.setRole("member");
                member.setEmail("member123@example.com");
                member.setSdt("0987654321");
                member.setAddress("Member Address");
                member.setSex("Nam");
                member.setBirthday(java.time.LocalDate.of(2002, 5, 20));
                userRepository.save(member);
                System.out.println("✅ Member user initialized: username=member123, password=member123");
            } else {
                System.out.println("✅ Member user already exists.");
            }

            if (!userRepository.existsByUsername("bs.vananh")) {
                System.out.println("🌱 Initializing consultants (doctors)...");
                createDoctor(userRepository, "bs.vananh", "Vân Anh", "vananh.bs@drug.com", "/images/doctor1.jpg");
                createDoctor(userRepository, "bs.minhquan", "Minh Quân", "minhquan.bs@drug.com", "/images/doctor2.jpg");
                createDoctor(userRepository, "ts.thuhang", "Thu Hằng", "thuhang.ts@drug.com", "/images/doctor3.jpg");
                createDoctor(userRepository, "bs.hoangnam", "Hoàng Nam", "hoangnam.bs@drug.com", "/images/doctor4.jpg");
                createDoctor(userRepository, "ths.maiphuong", "Mai Phương", "maiphuong.ths@drug.com", "/images/doctor5.jpg");
                createDoctor(userRepository, "bs.quocbao", "Quốc Bảo", "quocbao.bs@drug.com", "/images/doctor6.jpg");
                createDoctor(userRepository, "bs.ngoclan", "Ngọc Lan", "ngoclan.bs@drug.com", "/images/doctor7.jpg");
                createDoctor(userRepository, "ts.thanhphong", "Thanh Phong", "thanhphong.ts@drug.com", "/images/doctor8.jpg");
                createDoctor(userRepository, "bs.diemquynh", "Diễm Quỳnh", "diemquynh.bs@drug.com", "/images/doctor9.jpg");
                createDoctor(userRepository, "ths.tuananh", "Tuấn Anh", "tuananh.ths@drug.com", "/images/doctor10.jpg");
                System.out.println("✅ 10 consultants initialized.");
            } else {
                System.out.println("✅ Consultants already exist.");
            }
        };
    }


    // Bean mới để khởi tạo dữ liệu Test, Question, Answer
    @Bean
    @Transactional // Đảm bảo tất cả hoạt động trong một giao dịch
    CommandLineRunner initTests(TestRepository testRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        return args -> {
            // Chỉ chạy nếu chưa có bài test nào

            if (testRepository.count() > 0) {
                System.out.println("✅ Tests/Surveys already initialized.");
                return;
            }

            // --- TẠO BÀI KHẢO SÁT ASSIST ---
            Test assistTest = createTest(
                    "Khảo sát ASSIST",
                    "Sàng lọc các vấn đề liên quan đến việc sử dụng chất gây nghiện.",
                    "Đánh giá mức độ",
                    "Nhận thức về ma túy",
                    "https://i.ytimg.com/vi/a_frdvO7f44/maxresdefault.jpg", // Đường dẫn ảnh (bạn cần có ảnh này trong static/images)
                    8,
                    testRepository
            );

            createQuestion(assistTest, "Trong 3 tháng qua, bạn có thường xuyên sử dụng thuốc lá không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có sử dụng đồ uống có cồn (bia, rượu) không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có sử dụng cần sa không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có sử dụng cocain, ma túy đá (hàng đá, chấm đá) không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có sử dụng các chất kích thích dạng amphetamine (thuốc lắc, ectasy) không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có sử dụng thuốc ngủ hoặc thuốc an thần mà không có chỉ định của bác sĩ không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có hít các chất gây ảo giác (keo, xăng, ete...) không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(assistTest, "Trong 3 tháng qua, bạn có sử dụng thuốc phiện, heroin, morphin... không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);

            System.out.println("✅ ASSIST Survey initialized.");

            // --- TẠO BÀI TEST CRAFFT ---
            Test crafftTest = createTest(
                    "Bài test CRAFFT",
                    "Công cụ sàng lọc ngắn gọn cho thanh thiếu niên về nguy cơ lạm dụng chất.",
                    "Trắc nghiệm",
                    "Tâm lý - hành vi",
                    "https://cdn.thuvienphapluat.vn/phap-luat/2022-2/NHBT/14032025/tu-1-7-2025-su-dung-ma-tuy-di-tu-bao-nhieu-nam.jpg", // Đường dẫn ảnh
                    8,
                    testRepository
            );

            createQuestion(crafftTest, "Bạn đã bao giờ đi trên một chiếc XE (Car) do người đã sử dụng rượu bia hoặc chất kích thích (kể cả bạn) lái chưa?", List.of(
                    createAnswer("Chưa bao giờ", false),
                    createAnswer("Đã từng", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "Bạn có bao giờ dùng rượu bia hay chất kích thích để THƯ GIÃN (Relax), để cảm thấy tự tin hơn, hoặc để hòa nhập với bạn bè không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "Bạn có bao giờ sử dụng rượu bia/chất kích thích khi ở MỘT MÌNH (Alone) không?", List.of(
                    createAnswer("Không", false),
                    createAnswer("Có", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "Bạn có bao giờ QUÊN (Forget) những gì đã làm khi đang sử dụng rượu bia/chất kích thích không?", List.of(
                    createAnswer("Chưa từng quên", false),
                    createAnswer("Đã từng quên", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "GIA ĐÌNH (Family) hoặc BẠN BÈ (Friends) có bao giờ bảo bạn nên cắt giảm việc sử dụng rượu bia/chất kích thích không?", List.of(
                    createAnswer("Không ai nói gì", false),
                    createAnswer("Có, họ đã nói", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "Bạn có bao giờ gặp RẮC RỐI (Trouble) khi đang sử dụng rượu bia/chất kích thích không?", List.of(
                    createAnswer("Chưa từng gặp rắc rối", false),
                    createAnswer("Đã từng gặp rắc rối", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "Bạn có cảm thấy việc sử dụng các chất này ảnh hưởng đến việc học tập hoặc các mối quan hệ không?", List.of(
                    createAnswer("Không ảnh hưởng", false),
                    createAnswer("Có ảnh hưởng", true)
            ), questionRepository, answerRepository);
            createQuestion(crafftTest, "Bạn có bao giờ nói dối về mức độ hoặc tần suất bạn sử dụng các chất này không?", List.of(
                    createAnswer("Không bao giờ nói dối", false),
                    createAnswer("Đã từng nói dối", true)
            ), questionRepository, answerRepository);

            System.out.println("✅ CRAFFT Test initialized.");
        };
    }

    // Helper method để tạo Test
    private Test createTest(String name, String desc, String type, String topic, String image, int qCount, TestRepository repo) {
        Test test = new Test();
        test.setName(name);
        test.setDescription(desc);
        test.setType(type);
        test.setTopic(topic);
        test.setImage(image);
        test.setQuestionCount(qCount);
        test.setStatus("active");
        test.setCreatedAt(LocalDateTime.now());
        return repo.save(test);
    }

    // Helper method để tạo Question
    private void createQuestion(Test test, String content, List<Answer> answers, QuestionRepository qRepo, AnswerRepository aRepo) {
        Question question = new Question();
        question.setTestId(test.getId());
        question.setContent(content);
        Question savedQuestion = qRepo.save(question);

        for (Answer answer : answers) {
            answer.setQuestionId(savedQuestion.getId());
            aRepo.save(answer);
        }
    }

    // Helper method để tạo Answer
    private Answer createAnswer(String content, boolean isCorrect) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setIsCorrect(isCorrect); // isCorrect dùng để tính điểm
        return answer;
    }

    private void createDoctor(UserRepository userRepository, String username, String fullName, String email, String avatarUrl) {
        User doctor = new User();
        doctor.setUsername(username);
        doctor.setPassword("doctor123"); // Mật khẩu chung cho tất cả doctor (để demo)
        doctor.setRole("doctor");
        doctor.setEmail(email);
        // Để đơn giản, các thông tin khác có thể để null hoặc giá trị mặc định
        doctor.setSdt("0900112233");
        doctor.setAddress("Phòng tư vấn trung tâm");
        doctor.setSex("Khác");
        doctor.setAvatar(avatarUrl); // Thêm avatar
        userRepository.save(doctor);
    }

    private void createCourse(CourseRepository repo, String name, String desc, String target, String topic, String image, int participants, String v1, String v2, String v3) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(desc);
        course.setTargetObject(target);
        course.setTopic(topic);
        course.setImage(image);
        course.setParticipants(participants);
        course.setCreatedAt(LocalDateTime.now());
        course.setVideo1(v1);
        course.setVideo2(v2);
        course.setVideo3(v3);
        repo.save(course);
    }


    @Bean
    @Transactional
    CommandLineRunner initCourses(CourseRepository courseRepository) {
        return args -> {
            // Chỉ chạy nếu chưa có khóa học nào
            if (courseRepository.count() > 0) {
                System.out.println("✅ Courses already initialized.");
                return;
            }

            System.out.println("🌱 Initializing courses...");

            createCourse(courseRepository,
                    "Nhận biết các loại ma túy phổ biến",
                    "Cung cấp kiến thức cơ bản về các loại ma túy, tác hại và cách nhận diện các dấu hiệu ban đầu.",
                    "Học sinh",
                    "Nhận thức về ma túy",
                    "https://conganthanhhoa.gov.vn/upload/81582/fck/pc04thang/2023_06_25_12_57_323.jpg", 150,
                    "https://www.youtube.com/embed/dQw4w9WgXcQ", // Link video mẫu
                    "https://www.youtube.com/embed/L_jWHffIx5E",
                    ""); // video3 để trống

            createCourse(courseRepository,
                    "Kỹ năng từ chối ma túy hiệu quả",
                    "Trang bị các kỹ năng giao tiếp và tâm lý vững vàng để nói 'KHÔNG' với các lời mời rủ rê, cám dỗ.",
                    "Sinh viên",
                    "Kỹ năng phòng tránh",
                    "https://static.tuoitre.vn/tto/i/s626/2016/01/11/hinh-4-1452510165.jpg", 220,
                    "https://www.youtube.com/embed/jNQXAC9IVRw",
                    "", "");

            createCourse(courseRepository,
                    "Phụ huynh đồng hành cùng con",
                    "Dành cho các bậc cha mẹ: Cách nhận biết thay đổi tâm sinh lý của con và tạo môi trường gia đình an toàn.",
                    "Phụ huynh",
                    "Tâm lý - hành vi",
                    "https://img.lsvn.vn/resize/th/upload/2025/01/14/ma-tuy-va-y-chi-21203173.jpg", 95,
                    "https://www.youtube.com/embed/3tmd-ClpJxA",
                    "https://www.youtube.com/embed/a3Z7zEc7AXQ",
                    "");

            createCourse(courseRepository,
                    "Xây dựng môi trường học đường không ma túy",
                    "Dành cho giáo viên, cán bộ nhà trường. Cung cấp các công cụ và chiến lược để xây dựng môi trường giáo dục lành mạnh.",
                    "Giáo viên",
                    "Chính sách & Cộng đồng",
                    "https://campustechnology.com/-/media/EDU/CampusTechnology/2019-Images/20191209online.jpg", 180,
                    "https://www.youtube.com/embed/C0DPdy98e4c",
                    "", "");

            System.out.println("✅ 4 sample courses initialized.");
        };
    }

}