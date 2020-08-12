package pl.companymanagementhelper.definition.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.companymanagementhelper.utils.HeaderUtil;
import pl.companymanagementhelper.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  @Value("${pl.cmh.app-name}")
  private String applicationName;
  private static final String ENTITY_NAME = "user";

  @GetMapping("/users")
  public List<User> getAllUsers() {
    log.debug("REST request to get all Users");
    return userRepository.findAll();
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    log.debug("REST request to get User : {}", id);
    Optional<User> user = userRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(user);
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
    log.debug("REST request to save User : {}", user);
    User result = userRepository.save(user);
    return ResponseEntity.created(new URI("/api/users/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/users")
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    log.debug("REST request to update User : {}", user);
    User result = userRepository.save(user);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
    log.debug("REST request to delete User : {}", id);
    userRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
