package pl.companymanagementhelper.definition.address;

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
public class AddressController {
  private final AddressRepository addressRepository;
  private final AddressService addressService;

  @Value("${pl.cmh.app-name}")
  private String applicationName;
  private static final String ENTITY_NAME = "address";

  @GetMapping("/address")
  public List<Address> getAllAddresses() {
    log.debug("REST request to get all Addresses");
    return addressRepository.findAll();
  }

  @GetMapping("/address/{id}")
  public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
    log.debug("REST request to get Address : {}", id);
    Optional<Address> address = addressRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(address);
  }

  @PostMapping("/address")
  public ResponseEntity<Address> createAddress(@RequestBody Address address) throws URISyntaxException {
    log.debug("REST request to save Address : {}", address);
    Address result = addressRepository.save(address);
    return ResponseEntity.created(new URI("/api/address/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/address")
  public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
    log.debug("REST request to save Address : {}", address);
    Address result = addressRepository.save(address);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @DeleteMapping("/address/{id}")
  public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
    log.debug("REST request to delete Address : {}", id);
    addressRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
