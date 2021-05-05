package io.wedeploy.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class WeDeployControllerTest {

    private WeDeployController weDeployController = new WeDeployController();


    @Test
    void save() {
        DataEntries data1 = new DataEntries();
        data1.setDevice("dev1");
        data1.setTemp(BigDecimal.TEN);
        data1.setHumidity(BigDecimal.TEN);
        data1.setTimestamp(LocalDateTime.of(2018,1,1,0,0,0));

        weDeployController.save(data1);

        DataEntries data2 = new DataEntries();
        data2.setDevice("dev1");
        data2.setTemp(BigDecimal.TEN);
        data2.setHumidity(BigDecimal.TEN);
        data2.setTimestamp(LocalDateTime.of(2018,1,1,0,1,0));

        weDeployController.save(data2);

        Model model = new ExtendedModelMap();
        weDeployController.hello(model);

        Map<String, List<DataEntries>> result = (Map<String, List<DataEntries>>) model.asMap().get("data");

        Assertions.assertThat(result)
                .containsKeys("dev1")
                .hasSize(1);
        List<DataEntries> dev1 = result.get("dev1");
        Assertions.assertThat(dev1)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

    }
}