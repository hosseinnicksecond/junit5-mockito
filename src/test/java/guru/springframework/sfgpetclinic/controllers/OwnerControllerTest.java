package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.springdatajpa.OwnerSDJpaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Mock(lenient = true)
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult result;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(
                invocationOnMock -> {

                    List<Owner> owners= new ArrayList<>();
                    String result=invocationOnMock.getArgument(0);

                    if(result.equals("%Doe%")){
                        owners.add(new Owner(1L,"John","Doe"));
                        return owners;
                    }else if(result.equals("%not found%")){
                        return owners;
                    }else if(result.equals("%found more%")){
                        owners.add(new Owner(1L,"tim","Bus"));
                        owners.add(new Owner(2L,"Joe","Lost"));
                        return owners;
                    }

                    throw new RuntimeException("wrong Argument");
                }
        );
    }


    @Test
    void processFindFromTestWhenNotFound() {
        Owner owner= new Owner(1l,"Tim","not found");

        String view=controller.processFindForm(owner,result,null);

        assertThat("%not found%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualTo(view);
    }

    @Test
    void processFindFromTestWhenMoreThanOneResult() {
        Owner owner= new Owner(1L,"tim","found more");
        InOrder inOrder=Mockito.inOrder(service,model);

        String view= controller.processFindForm(owner,result,model);

        assertThat("%found more%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualTo(view);

        inOrder.verify(service).findAllByLastNameLike(anyString());
        inOrder.verify(model).addAttribute(anyString(),any());
    }

    @Test
    void processFindFromTestWithAnnotationCapture() {
        Owner owner= new Owner(1L,"John","Doe");
//        List<Owner> owners= new ArrayList<>();
//        given(service.findAllByLastNameLike(anyString())).willReturn(owners);

        String view=controller.processFindForm(owner,result,null);

        then(service).should().findAllByLastNameLike(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Doe%");
        assertThat("redirect:/owners/1").isEqualTo(view);
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