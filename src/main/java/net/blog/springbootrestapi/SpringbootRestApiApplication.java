package net.blog.springbootrestapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import net.blog.springbootrestapi.entity.Roles;
import net.blog.springbootrestapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import javax.management.relation.Role;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest APIs",
				description = "Spring Boot Blog App Rest APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Mohammed Ziyad",
						email = "mohdziyadk@gmail",
						url = "https://webwic.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://webwic.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/ziyad188/blog-webserver.git"
		)
)
public class SpringbootRestApiApplication implements CommandLineRunner {
	//to write meta data into the database
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public void run(String... args) throws Exception {
		Roles adminRole = new Roles();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Roles userRole = new Roles();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);


	}

	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}




	public static void main(String[] args) {

		SpringApplication.run(SpringbootRestApiApplication.class, args);

	}

}
