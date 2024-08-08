package com.activity.Activity.Services;

import com.activity.Activity.GlobalExceptionHandler.DeleteException;
import com.activity.Activity.GlobalExceptionHandler.StudentNotFoundException;
import com.activity.Activity.Model.Activity;
import com.activity.Activity.Model.User;
import com.activity.Activity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements BaseService<User> {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("User not found with id: " + id));
    }

    @Override
    public User create(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(Long id, User entity) {
        User user=getById(id);
        if (user!=null){
            user.setName(entity.getName());
            user.setSurname(entity.getSurname());
            userRepository.save(user);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        User user = getById(id);
        if(user!=null){
            userRepository.delete(user);
            return true;
        }
        else{
            throw new DeleteException("Böyle bir kullanıcı yok");
        }
    }

    public List<Activity> getAssignedActivitiesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new StudentNotFoundException("Böyle bir User Yok");
        }if (user.getAssignedActivities().isEmpty()) {
            throw new StudentNotFoundException("Kullanıcı etkinliğe kayıtlı değil.");
        }
        return user.getAssignedActivities();
    }

}
