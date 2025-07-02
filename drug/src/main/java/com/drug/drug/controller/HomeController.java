package com.drug.drug.controller;

import com.drug.drug.entity.BlogPost;
import com.drug.drug.entity.Booking;
import com.drug.drug.service.BlogPostService;
import com.drug.drug.service.BookingService;
import com.drug.drug.service.CourseService;
import com.drug.drug.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "member/dashboard";
    }

    @GetMapping("/assessment")
    public String assessment(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return "member/assessment";
    }

    @GetMapping("/consultation")
    public String consultationForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "member/consultation";
    }

    @PostMapping("/consultation")
    public String submitConsultation(@ModelAttribute("booking") Booking booking, Model model) {
        try {
            bookingService.save(booking);
            model.addAttribute("success", "Đặt lịch thành công! Chúng tôi sẽ liên hệ với bạn sớm.");
            model.addAttribute("booking", new Booking());
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra, vui lòng thử lại.");
            model.addAttribute("booking", booking);
        }
        return "member/consultation";
    }

    @GetMapping("/blog")
    public String blog(Model model,
                      @RequestParam(required = false) String category,
                      @RequestParam(required = false) String tag,
                      @RequestParam(required = false) String search) {
        
        List<BlogPost> allPosts = blogPostService.getAllPosts();
        
        // Lọc theo category nếu có
        if (category != null && !category.isEmpty()) {
            allPosts = allPosts.stream()
                .filter(post -> category.equals(post.getCategory()))
                .collect(Collectors.toList());
        }
        
        // Lọc theo tag (ở đây coi audience như tag)
        if (tag != null && !tag.isEmpty()) {
            allPosts = allPosts.stream()
                .filter(post -> tag.equals(post.getAudience()))
                .collect(Collectors.toList());
        }
        
        // Lọc theo search keyword
        if (search != null && !search.isEmpty()) {
            String keyword = search.toLowerCase();
            allPosts = allPosts.stream()
                .filter(post -> 
                    post.getTitle().toLowerCase().contains(keyword) ||
                    post.getSummary().toLowerCase().contains(keyword) ||
                    post.getContent().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        }
        
        // Lấy danh sách categories và tags (audience) duy nhất
        Set<String> categories = allPosts.stream()
            .map(BlogPost::getCategory)
            .collect(Collectors.toSet());
        
        Set<String> tags = allPosts.stream()
            .map(BlogPost::getAudience)
            .collect(Collectors.toSet());
        
        model.addAttribute("posts", allPosts);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tags);
        model.addAttribute("currentCat", category);
        model.addAttribute("currentTag", tag);
        model.addAttribute("currentSearch", search);
        
        return "member/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {
        BlogPost post = blogPostService.getPostById(id);
        if (post == null) {
            return "error/404";
        }
        model.addAttribute("post", post);
        return "member/blog-detail";
    }

    @GetMapping("/about")
    public String about() {
        return "member/about";
    }
}