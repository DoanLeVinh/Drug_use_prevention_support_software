package com.drug.drug.service;

import com.drug.drug.entity.PastCourse;
import com.drug.drug.repository.PastCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PastCourseService {

    @Autowired
    private PastCourseRepository pastCourseRepository;

    public void logCourseAccess(Long userId, Long courseId, String name, String description, String targetObject,
                               String topic, String video1, String video2, String video3,
                               Integer participants, String image, String action) {
        PastCourse entry = new PastCourse();
        entry.setUserId(userId);
        entry.setCourseId(courseId);
        entry.setName(name);
        entry.setDescription(description);
        entry.setTargetObject(targetObject);
        entry.setTopic(topic);
        entry.setVideo1(video1);
        entry.setVideo2(video2);
        entry.setVideo3(video3);
        entry.setParticipants(participants);
        entry.setImage(image);
        entry.setAction(action);
        entry.setAccessedAt(LocalDateTime.now());
        pastCourseRepository.save(entry);
    }

    public List<PastCourse> getPastCoursesByUser(Long userId) {
        return pastCourseRepository.findByUserIdOrderByAccessedAtDesc(userId);
    }

    public List<PastCourse> findByUserId(Long userId) {
    return pastCourseRepository.findByUserId(userId);
}

}
