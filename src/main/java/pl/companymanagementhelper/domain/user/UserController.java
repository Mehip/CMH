package pl.companymanagementhelper.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.companymanagementhelper.utils.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "User controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserRepository userRepository;

  @Value("${pl.cmh.app-name}")
  private String applicationName;
  private static final String ENTITY_NAME = "user";

  @Operation(summary = "Get list of all users")
  @GetMapping("/users")
  public List<User> getAllUsers() {
    log.debug("REST request to get all Users");
    return userRepository.findAll();
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/users/{id}")
  public ResponseEntity getUserById(
      @Parameter(required = true, description = "User id - It's a value by which a user is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to get User : {}", id);
    return userRepository.findById(id).map((response) -> ResponseEntity.ok()
        .headers((HttpHeaders) null)
        .body(response))
        .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
  }


  @Operation(summary = "Create new user")
  @PostMapping("/users")
  public ResponseEntity<User> createUser(
      @Parameter(required = true, description = "User - It's an object which represent information about user in a computer system") @RequestBody User user) throws URISyntaxException {
    log.debug("REST request to save User : {}", user);
    User result = userRepository.save(user);
    return ResponseEntity.created(new URI("/api/users/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Update user")
  @PutMapping("/users")
  public ResponseEntity<User> updateUser(
      @Parameter(required = true, description = "User - It's an object which represent information about user in a computer system") @RequestBody User user) {
    log.debug("REST request to update User : {}", user);
    User result = userRepository.save(user);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Delete user by id")
  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUserById(
      @Parameter(required = true, description = "User id - It's a value by which a user is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to delete User : {}", id);
    userRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
