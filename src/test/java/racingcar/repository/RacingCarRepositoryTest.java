package racingcar.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.domain.Car;
import racingcar.domain.Cars;
import racingcar.domain.MoveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class RacingCarRepositoryTest {

    RacingCarRepository carTable;

    @BeforeEach
    void setUp() {
        carTable = new RacingCarRepository();
    }

    @DisplayName("Car 저장")
    @Test
    void save() {
        Car carA = new Car("testA");
        Car carB = new Car("testB");

        carTable.save(carA);
        carTable.save(carB);

        int size = carTable.findAll()
                            .size();
        assertThat(size).isEqualTo(2);
    }

    @DisplayName("Cars 저장")
    @Test
    void saveAll() {
        carTable.saveAll(Cars.of("noose,pobi,honux"));

        int size = carTable.findAll()
                            .size();
        assertThat(size).isEqualTo(3);
    }

    @DisplayName("불러온 데이터에 수정을 해도 테이블에 저장된 데이터는 변경되지 않는다.")
    @Test
    void findAll() {
        carTable.saveAll(Cars.of("noose,pobi,honux"));

        Car carA = carTable.findAll().getElements().get(0);
        MoveStrategy forward = () -> true;
        carA.move(forward);
        carA.move(forward);

        Car carB = carTable.findAll().getElements().get(0);

        assertThat(carB.positionValue()).isEqualTo(1);
    }

    @DisplayName("동시에 데이터를 저장하는 경우에도 잘 처리한다.")
    @Test
    void sync() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(save(100)).start();
        }

        Thread.sleep(1000);
        Cars cars = carTable.findAll();
        assertThat(cars.size()).isEqualTo(10000);
    }

    private Runnable save(int loopSize) {
        return () -> {
            for (int i = 0; i < loopSize; i++) {
                carTable.save(new Car("test"));
            }
        };
    }
}