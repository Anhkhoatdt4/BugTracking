package com.devteria.bugtracking.controller;

import com.devteria.bugtracking.dto.Issue;
import com.devteria.bugtracking.dto.UpdateFieldRequest;
import com.devteria.bugtracking.entity.Bug;
import com.devteria.bugtracking.entity.Project;
import com.devteria.bugtracking.entity.User;
import com.devteria.bugtracking.repository.ProjectRepository;
import com.devteria.bugtracking.repository.UserRepository;
import com.devteria.bugtracking.service.BugService;
import com.devteria.bugtracking.service.ProjectService;
import com.devteria.bugtracking.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class HomeController {
    UserService userService;
    BugService bugService;
    ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("bugs", bugService.getAllBugs());
        model.addAttribute("projects", projectService.getAllProjects());
        return "list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Project> projects = projectRepository.findAll();
        List<User> users = userRepository.findAll(); // Nạp tất cả các người dùng

        model.addAttribute("projects", projects);
        model.addAttribute("users", users);
        return "create";
    }

    @PostMapping("/create-issue")
    public ModelAndView add(@RequestParam String project,
                            @RequestParam String summary,
                            @RequestParam String status,
                            @RequestParam String description,
                            @RequestParam String labels,
                            @RequestParam String priority,
                            @RequestParam String assignee,
                            @RequestParam String reporter) {

        // Tạo đối tượng Issue từ thông tin nhận được
        Issue issue = new Issue();
        issue.setProject(project);
        issue.setSummary(summary);
        issue.setStatus(status);
        issue.setDescription(description);
        issue.setLabels(labels);
        issue.setPriority(priority);
        issue.setAssignee(assignee);
        issue.setReporter(reporter);
        try {
            bugService.createIssue(issue);
        } catch (IllegalArgumentException e) {

            return new ModelAndView("error")
                    .addObject("message", e.getMessage());
        }
        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UpdateFieldRequest updateFieldRequest) {
        try {
            bugService.updateField(updateFieldRequest);
            List<Bug> bugs = bugService.getAllBugs();
            List<Project> projects = projectService.getAllProjects();
            System.out.println("Bugs: " + bugs);
            System.out.println("Projects: " + projects);
            return ResponseEntity.ok().body("Update successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
        }
    }




}



