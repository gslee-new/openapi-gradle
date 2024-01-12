package gslee.oas.controller;

import glsee.oas.openapi.domain.ModelApiResponse;
import glsee.oas.openapi.domain.Pet;
import glsee.oas.openapi.rest.PetApi;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
public class PetControllerImpl implements PetApi {
  @Override
  public ResponseEntity<Pet> getPetById(Long petId) {
    return PetApi.super.getPetById(petId);
  }

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return PetApi.super.getRequest();
  }

  @Override
  public ResponseEntity<Pet> addPet(Pet pet) {
    return PetApi.super.addPet(pet);
  }

  @Override
  public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
    return PetApi.super.deletePet(petId, apiKey);
  }

  @Override
  public ResponseEntity<List<Pet>> findPetsByStatus(String status) {
    return PetApi.super.findPetsByStatus(status);
  }

  @Override
  public ResponseEntity<List<Pet>> findPetsByTags(List<String> tags) {
    return PetApi.super.findPetsByTags(tags);
  }

  @Override
  public ResponseEntity<Pet> updatePet(Pet pet) {
    return PetApi.super.updatePet(pet);
  }

  @Override
  public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
    return PetApi.super.updatePetWithForm(petId, name, status);
  }

  @Override
  public ResponseEntity<ModelApiResponse> uploadFile(Long petId, String additionalMetadata,
      Resource body) {
    return PetApi.super.uploadFile(petId, additionalMetadata, body);
  }
}
