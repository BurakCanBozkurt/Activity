package com.activity.Activity.RestController;

import com.activity.Activity.Model.Role;
import com.activity.Activity.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public List<Role> getAllRoles(){
        return roleService.getAll();}

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id){return roleService.getById(id);}

    @PostMapping("/add")
    public Role createRole(@RequestBody Role entity){
        return roleService.create(entity);}

    @PostMapping("/delete/{id}")
    public Boolean deleteRole(@PathVariable Long id){return roleService.delete(id);}

    @PutMapping("/update/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role entity) {
        return roleService.update(id, entity);
    }
}
