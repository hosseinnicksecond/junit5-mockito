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
    void findAll() {
        Set<Visit> visits= new HashSet<>();
        visits.add(visit);

//       when(repository.findAll()).thenReturn(visits);
        doReturn(visits).when(repository).findAll();
        Set<Visit> found=service.findAll();

        verify(repository).findAll();

        assertThat(found).hasSize(1);
    }

    @Test
    void findById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(visit));

        visit1=service.findById(1L);
        verify(repository).findById(anyLong());
        assertThat(visit1).isNotNull();
    }

    @Test
    void save() {

        when(repository.save(any(Visit.class))).thenReturn(visit);

        visit1=service.save(visit);

        verify(repository).save(visit);

        assertThat(visit1).isEqualTo(visit);
    }

    @Test
    void delete() {

        service.delete(visit);

        verify(repository).delete(visit);

    }

    @Test
    void deleteById() {

        service.deleteById(1L);
        service.deleteById(1L);

        verify(repository,times(2)).deleteById(anyLong());
    }
}