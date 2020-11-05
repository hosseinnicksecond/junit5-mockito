package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InlineMockObject {

    @Test
    void inlineMock(){
        Map mockMap=mock(Map.class);

        assertEquals(mockMap.size(),0);
    }

    @Test
    void whenTest() {
        LinkedList<String> linkedList=mock(LinkedList.class);

        when(linkedList.get(0)).thenReturn("first");
        when(linkedList.get(1)).thenThrow(new RuntimeException());

        System.out.println(linkedList.get(0));

        assertThatThrownBy(()->linkedList.get(1)).isInstanceOf(RuntimeException.class);

    }
}
