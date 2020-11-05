package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.springdatajpa.OwnerSDJpaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult result;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormTestWithCapture() {

        Owner owner= new Owner(1L,"John","Doe");
        List<Owner> owners= new ArrayList<>();
        final ArgumentCaptor<String> argumentCaptor= ArgumentCaptor.forClass(String.class);
        when(service.findAllByLastNameLike(anyString())).thenReturn(owners);

        String view=controller.processFindForm(owner,result,null);

        then(service).should().findAllByLastNameLike(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo("%Doe%");

    }

    @Test
    void processFindFromTestWithAnnotationCapture() {
        Owner owner= new Owner(1L,"John","Doe");
        List<Owner> owners= new ArrayList<>();
        given(service.findAllByLastNameLike(anyString())).willReturn(owners);

        String view=controller.processFindForm(owner,result,null);

        then(service).should().findAllByLastNameLike(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Doe%");
    }

    @Test
    void processCreationFormHasError() {

        Owner owner=new Owner(1L,"John","Doe");
        given(result.hasErrors()).willReturn(true);
//        assertFalse(result.hasErrors());

        String view=controller.processCreationForm(owner,result);

        assertThat(view).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreateFormNoError() {

        Owner owner= new Owner(1L,"John","Doe");
        given(result.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);

        String view=controller.processCreationForm(owner,result);

        assertThat(view).isEqualTo("redirect:/owners/1");
    }
}