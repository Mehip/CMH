package pl.companymanagementhelper.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.companymanagementhelper.domain.address.Address;
import pl.companymanagementhelper.domain.address.AddressController;
import pl.companymanagementhelper.domain.address.AddressRepository;
import pl.companymanagementhelper.domain.user.UserController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AddressRepository addressRepository;

  private List<Address> addressList;

  @BeforeEach
  void setUp() {
    this.addressList = new ArrayList<>();
    this.addressList.add(new Address(123L, "Monte Cassino", "1", "01-121", "Warszawa"));
    this.addressList.add(new Address(1L, "Kazimierzowska", "12", "03-542", "Siedlce"));
    this.addressList.add(new Address(44L, "Warszawska", "16", "21-754", "Lublin"));

  }

  @Test
  void shouldFetchAllAddresses() throws Exception {
    given(addressRepository.findAll()).willReturn(addressList);

    this.mockMvc.perform(get("/api/address"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(addressList.size())));
  }

  @Test
  void shouldFetchOneAddressById() throws Exception {
    given(addressRepository.findById(this.addressList.get(0).getId())).willReturn(Optional.of(this.addressList.get(0)));

    this.mockMvc.perform(get("/api/address/{id}", this.addressList.get(0).getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.city", is(this.addressList.get(0).getCity())))
        .andExpect(jsonPath("$.houseNumber", is(this.addressList.get(0).getHouseNumber())))
        .andExpect(jsonPath("$.street", is(this.addressList.get(0).getStreet())))
        .andExpect(jsonPath("$.postalCode", is(this.addressList.get(0).getPostalCode())));
  }

  @Test
  void shouldCreateNewAddress() throws Exception {
    given(addressRepository.save(any(Address.class))).willAnswer((invocation) -> invocation.getArgument(0));

    this.mockMvc.perform(post("/api/address")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.addressList.get(0))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.city", is(this.addressList.get(0).getCity())))
        .andExpect(jsonPath("$.houseNumber", is(this.addressList.get(0).getHouseNumber())))
        .andExpect(jsonPath("$.street", is(this.addressList.get(0).getStreet())))
        .andExpect(jsonPath("$.postalCode", is(this.addressList.get(0).getPostalCode())));
  }

  @Test
  void shouldUpdateAddress() throws Exception {
    given(addressRepository.save(any(Address.class))).willAnswer((invocation) -> invocation.getArgument(0));

    this.mockMvc.perform(put("/api/address")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.addressList.get(0))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.city", is(this.addressList.get(0).getCity())))
        .andExpect(jsonPath("$.houseNumber", is(this.addressList.get(0).getHouseNumber())))
        .andExpect(jsonPath("$.street", is(this.addressList.get(0).getStreet())))
        .andExpect(jsonPath("$.postalCode", is(this.addressList.get(0).getPostalCode())));
  }

  @Test
  void shouldDeleteAddress() throws Exception {
    given(addressRepository.findById(this.addressList.get(0).getId())).willReturn(Optional.of(this.addressList.get(0)));
    doNothing().when(addressRepository).deleteById(this.addressList.get(0).getId());

    this.mockMvc.perform(delete("/api/address/{id}", this.addressList.get(0).getId()))
        .andExpect(status().isNoContent());
  }
}
