package io.wedeploy.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@EnableAutoConfiguration
public class WeDeployController {

    private final Logger logger = LoggerFactory.getLogger(WeDeployController.class);

    private final Clock clock = Clock.systemUTC();
    public static final int CAPACITY_PER_DEVICE = 20;
    private static final long ACCEPTED_INTERVAL = 20;
    private static Map<String, SizedStack<DataEntries>> STORAGE = new HashMap<>();


    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(WeDeployController.class, args);
    }

    @RequestMapping("/")
    public String hello(Model model) {

        Map<String, List<DataEntries>> soreted = STORAGE.entrySet().stream()
                .limit(20)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> this.getList(e.getValue())
                ));

        model.addAttribute("data", soreted);
        return "layout";
    }

    @PutMapping(value = "/save")
    public String save(@RequestBody DataEntries dataEntries) {
        dataEntries.setTimestamp(LocalDateTime.now(clock));

        logger.info(dataEntries.toString());

        SizedStack<DataEntries> data = STORAGE.get(dataEntries.getDevice());
        if (data == null) {
            data = new SizedStack<>(CAPACITY_PER_DEVICE);
            STORAGE.put(dataEntries.getDevice(), data);
        }
        DataEntries peek = data.peek();
        if (peek != null) {
            LocalDateTime oldTimeStamp = peek.getTimestamp();
            LocalDateTime newTimeStamp = dataEntries.getTimestamp();
            if (Duration.between(oldTimeStamp, newTimeStamp).toMinutes() > ACCEPTED_INTERVAL) {
                data.push(dataEntries);
            }

        } else {
            data.push(dataEntries);
        }

        return "ok";
    }

    private List<DataEntries> getList(Collection<DataEntries> collection) {
        List<DataEntries> col = new ArrayList<>(collection);
        Collections.reverse(col);
        return col;
    }

}
