package com.example.demo.controllers;

import java.io.*;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class FileController {

    @Autowired
    FileService fileService;

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/index")
    ModelAndView
    index()
    {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("version", "0.1");
        return mav;
    }

    @RequestMapping(value = "/template/log", method = RequestMethod.GET)
    public String getLog() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:9090/log";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    @RequestMapping(value="/log", method = RequestMethod.GET)
    public String log()
    {
        // Logging various log level messages
        logger.trace("Log level: TRACE");
        logger.info("Log level: INFO");
        logger.debug("Log level: DEBUG");
        logger.error("Log level: ERROR");
        logger.warn("Log level: WARN");

        log.trace("Log level: TRACE from lombok");
        log.info("Log level: INFO from lombok");
        log.debug("Log level: DEBUG from lombok");
        log.error("Log level: ERROR from lombok");
        log.warn("Log level: WARN from lombok");

        return "Hey! You can check the output in the logs";

    }
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile(@PathVariable String filename) throws IOException  {
       return fileService.downloadFile(filename);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.fileUpload(file);
    }
}