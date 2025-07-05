package com.drug.drug.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(name = "target_object")
    private String targetObject;

    private String topic;
    private String video1;
    private String video2;
    private String video3;
    private Integer participants;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String image; // Thêm dòng này

    // Getter và Setter đầy đủ
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTargetObject() { return targetObject; }
    public void setTargetObject(String targetObject) { this.targetObject = targetObject; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getVideo1() { return video1; }
    public void setVideo1(String video1) { this.video1 = video1; }

    public String getVideo2() { return video2; }
    public void setVideo2(String video2) { this.video2 = video2; }

    public String getVideo3() { return video3; }
    public void setVideo3(String video3) { this.video3 = video3; }

    public Integer getParticipants() { return participants; }
    public void setParticipants(Integer participants) { this.participants = participants; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getImage() { return image; }   // Thêm getter
    public void setImage(String image) { this.image = image; } // Thêm setter

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", targetObject='" + targetObject + '\'' +
                ", topic='" + topic + '\'' +
                ", video1='" + video1 + '\'' +
                ", video2='" + video2 + '\'' +
                ", video3='" + video3 + '\'' +
                ", participants=" + participants +
                ", createdAt=" + createdAt +
                ", image='" + image + '\'' +    // Thêm dòng này
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id != null && id.equals(course.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public Course() {
        // Constructor mặc định
    }

    // Có thể bổ sung image vào constructor nếu cần:
    public Course(String name, String description, String targetObject, String topic,
                  String video1, String video2, String video3, Integer participants,
                  LocalDateTime createdAt, String image) {
        this.name = name;
        this.description = description;
        this.targetObject = targetObject;
        this.topic = topic;
        this.video1 = video1;
        this.video2 = video2;
        this.video3 = video3;
        this.participants = participants;
        this.createdAt = createdAt;
        this.image = image;
    }
}
