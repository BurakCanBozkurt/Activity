package com.activity.Activity.Services;


import com.activity.Activity.GlobalExceptionHandler.DeleteException;
import com.activity.Activity.Model.Role;
import com.activity.Activity.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements BaseService<Role> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role create(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public Role update(Long id, Role entity) {
        Role role = getById(id);
        if(role!=null){
            role.setName(entity.getName());
            return roleRepository.save(role);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(getById(id)!=null){
            roleRepository.deleteById(id);
            return true;
        }
        else{
            throw new DeleteException("Böyle bir rol yok");
        }
    }
}

