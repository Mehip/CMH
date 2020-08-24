package pl.companymanagementhelper.definition.address;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Address controller")
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

  @Operation(summary = "Get list of all addresses")
  @GetMapping("/address")
  public List<Address> getAllAddresses() {
    log.debug("REST request to get all Addresses");
    return addressRepository.findAll();
  }

  @Operation(summary = "Get address by id")
  @GetMapping("/address/{id}")
  public ResponseEntity<Address> getAddressById(
      @Parameter(required = true, description = "Address id - It's a value by which an address is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to get Address : {}", id);
    Optional<Address> address = addressRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(address);
  }

  @Operation(summary = "Create new address")
  @PostMapping("/address")
  public ResponseEntity<Address> createAddress(
      @Parameter(required = true, description = "Address id - It's a value by which an address is identified in a computer system") @RequestBody Address address) throws URISyntaxException {
    log.debug("REST request to save Address : {}", address);
    Address result = addressRepository.save(address);
    return ResponseEntity.created(new URI("/api/address/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Update address")
  @PutMapping("/address")
  public ResponseEntity<Address> updateAddress(
      @Parameter(required = true, description = "Address - It's an object which represent real address in a computer system") @RequestBody Address address) {
    log.debug("REST request to save Address : {}", address);
    Address result = addressRepository.save(address);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Delete address by id")
  @DeleteMapping("/address/{id}")
  public ResponseEntity<Void> deleteAddressById(
      @Parameter(required = true, description = "Address id - It's a value by which an address is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to delete Address : {}", id);
    addressRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
