package org.slaega.family_secret.controller.v1;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/users/")
public class UserController {
    @PostMapping
    public String create(@RequestBody String entity) {
        return entity;
    }

    @GetMapping
    public String find(@RequestParam String param) {
        return new String();
    }

    @GetMapping(":id")
    public String findOne(@RequestParam String param) {
        return new String();
    }

    @PutMapping("{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @DeleteMapping
    public String putMethodName(@PathVariable String id) {
        return id;
    }
}
