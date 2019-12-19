package MakePlannieWork.Plannie.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReisItemControllerTest {

    @Autowired
    private ReisItemController reisItemController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(reisItemController).isNotNull();
    }
}