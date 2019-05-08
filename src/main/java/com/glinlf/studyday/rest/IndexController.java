package com.glinlf.studyday.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author glinlf
 * @date 2019-05-08
 * @Description TODO
 **/
@RestController
public class IndexController {

    @GetMapping(value = "/index")
    public String index() {
        return "hello world!";
    }
}
