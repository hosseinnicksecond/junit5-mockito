package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    Visit visit;
    Visit visit1;

    @Mock
    VisitRepository repository;

    @InjectMocks
    VisitSDJpaService service;

    @BeforeEach
    public void setup(){
        visit= new Visit(1L, LocalDate.now());
    }


    @Test
    void findAllBDD() {
        //given
        Set<Visit> visits= new HashSet<>();
        visits.add(visit);
        given(repository.findAll()).willReturn(visits);
        //when
        Set<Visit> found=service.findAll();

        //then
        then(repository).should().findAll();
        assertThat(found).hasSize(1);
    }

    @Test
    void findByIdBDD() {

        //given
        given(repository.findById(anyLong())).willReturn(Optional.of(visit));
        //when
        visit1=service.findById(1L);
        //then
        then(repository).should(times(1)).findById(anyLong());
        assertThat(visit1).isNotNull();
    }

    @Test
    void saveBDD() {
        //given
        given(repository.save(visit)).willReturn(visit);
        //when
        visit1=service.save(visit);
        //then
        then(repository).should().save(any(Visit.class));
        assertThat(visit1).isEqualTo(visit);
    }

    @Test
    void deleteBDD() {
        //given-- none

        //when
        service.delete(visit);
        //then
        then(repository).should().delete(any(Visit.class));

    }

    @Test
    void deleteByIdBDD() {
        //given - none

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        then(repository).should(times(2)).deleteById(anyLong());
    }
}