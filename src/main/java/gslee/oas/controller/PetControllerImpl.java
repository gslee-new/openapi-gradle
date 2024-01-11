package gslee.oas.controller;

import glsee.oas.openapi.domain.Pet;
import glsee.oas.openapi.rest.PetApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetControllerImpl implements PetApi {
  @Override
  public ResponseEntity<Pet> getPetById(Long petId) {
    return PetApi.super.getPetById(petId);
  }
}
