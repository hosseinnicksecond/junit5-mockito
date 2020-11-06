package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy
    PetMapService petMapService;

    @InjectMocks
    VisitController controller;

    @Test
    void loadPetWithVisitNoStubbing() {
        Pet pet=new Pet(1L);
        Map<String,Object> model=new HashMap<>();

        petMapService.save(pet);

        given(petMapService.findById(anyLong())).willCallRealMethod();

        Visit visit=controller.loadPetWithVisit(1L,model);

        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        verify(petMapService,times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        Pet pet= new Pet(1L);
        Pet pet1= new Pet(10L);
        Map<String,Object> model= new HashMap<>();

        petMapService.save(pet);
        petMapService.save(pet1);

        given(petMapService.findById(anyLong())).willReturn(pet1);

        Visit visit=controller.loadPetWithVisit(1L,model);

        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(10L);
        verify(petMapService,times(1)).findById(anyLong());


    }
}