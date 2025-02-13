package study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SetTest {
    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    @DisplayName("Set 사이즈 확인")
    @Test
    void size() {
        assertThat(numbers).hasSize(3);
    }

    @DisplayName("Set 원소 확인-성공 케이스")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void contains(int input) {
        assertThat(numbers).contains(input);
    }

    @DisplayName("Set 원소 확인-성공/실패 케이스")
    @ParameterizedTest
    @CsvSource(value = {"1,true", "2,true", "3,true", "4,false", "5,false"})
    void contains(int input, boolean expected) {
        assertThat(numbers.contains(input)).isEqualTo(expected);
    }
}
