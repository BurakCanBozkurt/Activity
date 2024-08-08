package com.activity.Activity.RestController;

import com.activity.Activity.Model.Activity;
import com.activity.Activity.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.activity.Activity.Model.User;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllStudents(){return userService.getAll();}

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){return userService.getById(id);}

    @PostMapping("/add")
    public User createUser(@RequestBody User entity){return userService.create(entity);}

    @PostMapping("/delete/{id}")
    public Boolean deleteUser(@PathVariable Long id){return userService.delete(id);}

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User entity) {
        return userService.update(id, entity);
    }

    @GetMapping("/{userId}/assignedActivities")
    public ResponseEntity<List<Activity>> getAssignedActivities(@PathVariable Long userId) {
        List<Activity> assignedActivities = userService.getAssignedActivitiesByUserId(userId);
        if (assignedActivities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(assignedActivities);
    }
}
