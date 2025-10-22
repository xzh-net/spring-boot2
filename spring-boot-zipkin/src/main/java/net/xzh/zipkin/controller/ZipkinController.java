package net.xzh.zipkin.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class ZipkinController {
    private static Logger log = LoggerFactory.getLogger(ZipkinController.class);


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("ping")
    public Object ping() {
        log.info("进入ping");
        return "pong ok";
    }

    @RequestMapping("log")
    public Object log() {
        log.info("this is info log");
        log.error("this is error log");
        log.debug("this is debug log");
        log.warn("this is warn log");
        log.trace("this is trace log");
        return "log ok";
    }

    @RequestMapping("http")
    public Object httpQuery() {

        String studyUrl = "http://localhost:8080/ping";
        URI studyUri = URI.create(studyUrl);
        String study = restTemplate.getForObject(studyUri, String.class);
        log.info("study:{}", study);

        String floorUrl = "http://localhost:8080/log";
        URI floorUri = URI.create(floorUrl);
        String floor = restTemplate.getForObject(floorUri, String.class);
        log.info("floor:{}", floor);

        String roomUrl = "http://localhost:8080/ping";
        URI roomUri = URI.create(roomUrl);
        String room = restTemplate.getForObject(roomUri, String.class);
        log.info("room:{}", room);
        return study + "-------" + floor + "-------" + room + "-------";
    }


}