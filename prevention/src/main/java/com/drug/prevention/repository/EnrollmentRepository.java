// 📄 repository/EnrollmentRepository.java

package com.drug.prevention.repository;

import com.drug.prevention.entity.Enrollment;
import com.drug.prevention.entity.User;
import com.drug.prevention.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserAndCourse(User user, Course course);
    List<Enrollment> findByCourse(Course course);
    List<Enrollment> findByUser(User user);
}
