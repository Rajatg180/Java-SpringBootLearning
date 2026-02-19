package com.example.SpringBootUserManagement;

import com.example.SpringBootUserManagement.constants.Role;
import com.example.SpringBootUserManagement.entity.User;
import com.example.SpringBootUserManagement.repository.UserRepository;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminContoller {
    // admin apis work with user data, so we need repsoitry access
    private final UserRepository userRepository;

    public AdminContoller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/hello")
    public String helloAdmin(){
        return "Hello Admin";
    }

    // admin will have access to all users, using pagination
    // Example: GET /admin/users?page=0&size=10&sort=id,desc

    @PreAuthorize("hasRole('ADMIN')") // this annotation is used to restrict access to this endpoint to only users with ADMIN role
    @GetMapping("/users")

    // Page is a Spring Data interface that represents a page of data. It contains the content of the page (a list of users in this case) and metadata about the page (such as total number of pages, total number of elements, etc.).
    public Page<User> getAllUser(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size
    ){
        // this is spring data pagination. 
        // We create a Pageable object using PageRequest.of() method, which takes the page number, page size, and sorting information as parameters.
        //  In this case, we are sorting by id in descending order. Then we pass this Pageable object to the findAll() method of the userRepository, which returns a Page<User> object containing the users for the requested page along with pagination metadata. 
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        // findAll(pageable) automatically generates:
        // SELECT * FROM users LIMIT size OFFSET page*size
        return userRepository.findAll(pageable);
    }

    // promote  a user to admin ( for testing purpose )
    // WE WILL only prompate normal users to admin right ? but we have user which are only normal users ,how can they access this endpoint to promote themselves to admin ?
    // beacuse we have restricted this endpoint to only admin users, so only admin users can access this endpoint to promote other users to admin. Normal users cannot access this endpoint, so they cannot promote themselves to admin. This is a security measure to prevent normal users from promoting themselves to admin. Only existing admins can promote other users to admin.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{id}/make-admin")
    
    public User makeAdmin(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow( () -> new RuntimeException("User not found with id : " + id));

        Set<Role> roles = user.getRoles();
        roles.add(Role.ADMIN); // add admin role to the user
        user.setRoles(roles);

        return userRepository.save(user);
    }

}
