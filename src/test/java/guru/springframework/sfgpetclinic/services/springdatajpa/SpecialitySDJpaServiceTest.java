package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void findByIdTest() {
        //given
        Speciality speciality= new Speciality();
      given(repository.findById(anyLong())).willReturn(Optional.of(speciality));
        //when
        Speciality foundSpeciality=service.findById(1L);
        //then
        assertThat(foundSpeciality).isNotNull();
        then(repository).should(times(1)).findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();
    }
    @Test
    void delete() {
        //given
        Speciality speciality= new Speciality();
        //when
        service.delete(speciality);

        then(repository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteByIdTimes() {
        //given non

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        then(repository).should(times(2)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeastOnce() {
        //given non

        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //than
        then(repository).should(atLeast(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtMost() {
        //given non

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        //then
        then(repository).should(atMost(5)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNever() {
        //given non

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        then(repository).should(never()).deleteById(5L);
    }


}