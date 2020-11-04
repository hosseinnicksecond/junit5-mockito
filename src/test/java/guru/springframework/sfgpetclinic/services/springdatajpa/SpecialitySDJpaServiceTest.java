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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void findByIdTest() {
        Speciality speciality= new Speciality();

        when(repository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality=service.findById(1L);

        assertThat(foundSpeciality).isNotNull();

        verify(repository).findById(1L);
    }

    @Test
    void delete() {

        service.delete(new Speciality());
    }

    @Test
    void deleteByIdTimes() {
        service.deleteById(1L);
        service.deleteById(1L);

        verify(repository,times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeastOnce() {
        service.deleteById(1L);
        service.deleteById(1L);

        verify(repository,atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(1L);
        service.deleteById(1L);

        verify(repository,atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(1L);
        service.deleteById(1L);

        verify(repository,never()).deleteById(5L);
    }


}