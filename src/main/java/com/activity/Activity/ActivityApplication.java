package com.activity.Activity;

import com.activity.Activity.Model.Role;
import com.activity.Activity.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(RoleRepository roleRepository){
		return  args -> {
			Role role=new Role();
			role.setName("TEACHER");
			roleRepository.save(role);

			Role role1=new Role();
			role1.setName("STUDENT");
			roleRepository.save(role1);
		};
	}

}
