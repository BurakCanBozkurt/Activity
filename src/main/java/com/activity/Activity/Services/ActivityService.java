package com.activity.Activity.Services;

import com.activity.Activity.GlobalExceptionHandler.DeleteException;
import com.activity.Activity.GlobalExceptionHandler.StudentAlreadyExistsException;
import com.activity.Activity.GlobalExceptionHandler.StudentNotFoundException;
import com.activity.Activity.Model.Activity;
import com.activity.Activity.Model.User;
import com.activity.Activity.Repository.ActivityRepository;
import com.activity.Activity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService implements BaseService<Activity>{
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity create(Activity entity) {
        return activityRepository.save(entity);
    }

    @Override
    public Activity update(Long id, Activity entity) {
        Activity activity=getById(id);
        if(activity!=null){
            activity.setName(entity.getName());
            activity.setDescription(entity.getDescription());
            activity.setAssignedStudents(entity.getAssignedStudents());
            activityRepository.save(activity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Activity activity=getById(id);
        if(activity!=null){
            activityRepository.delete(activity);
            return true;
        }
        else{
            throw new DeleteException("Böyle bir aktivite yok");
        }
    }

    public Activity addStudentAct(Long studentid, Long activityid) {
        List<User> userList = new ArrayList<>();

        Optional<User> userOptional = userRepository.findById(studentid);
        Optional<Activity> activityOptional = activityRepository.findById(activityid);

        if (userOptional.isPresent() && activityOptional.isPresent()) {
            User user = userOptional.get();
            Activity activity = activityOptional.get();

            userList = activity.getAssignedStudents();
            if (!userList.contains(user)) {
                userList.add(user);
                activity.setAssignedStudents(userList);
                activityRepository.save(activity);
            } else {
                throw new StudentAlreadyExistsException("User already in the activity");
            }
            return activity;
        } else {
            throw new StudentNotFoundException("Böyle bir kullanıcı veya aktivite yok");
        }
    }


    public String printStudents(Long id) {
        Activity activity = activityRepository.findById(id).orElse(null);

        if (activity == null) {
            return "Aktivite bulunamadı.";
        }

        List<User> userList = activity.getAssignedStudents();

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Aktivite adı: ").append(activity.getName()).append("\n");
        responseBuilder.append("Aktivite tanımı: ").append(activity.getDescription()).append("\n");

        for (User user : userList) {
            responseBuilder.append("---------------------------\n");
            responseBuilder.append("Öğrenci ID: ").append(user.getUserId()).append("\n");
            responseBuilder.append("Öğrenci Adı - Soyadı: ").append(user.getName()).append(" ").append(user.getSurname()).append("\n");
        }

        return responseBuilder.toString();
    }

    public String removeStudentFromActivity(Long activityId, Long studentId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null) {
            return "Aktivite bulunamadı.";
        }

        User studentToRemove = null;
        for (User user : activity.getAssignedStudents()) {
            if (user.getUserId().equals(studentId)) {
                studentToRemove = user;
                break;
            }
        }

        if (studentToRemove == null) {
            return "Öğrenci aktiviteye atanmamış.";
        }

        activity.getAssignedStudents().remove(studentToRemove);
        activityRepository.save(activity);

        return "Öğrenci aktiviteden başarıyla silindi.";
    }
}
