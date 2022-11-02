package com.example.SpringSecurityDemo.rest;

import com.example.SpringSecurityDemo.model.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {

    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1L, "John", "Weak"),
            new Developer(2L, "Danil", "Zinoveyev"),
            new Developer(3L, "Michael", "Sholkin")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Developer> getAll(){
        return DEVELOPERS;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Developer getById(@PathVariable Long id){
        return DEVELOPERS
                .stream()
                .filter(elem -> elem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('developers:write')")
    public Developer create(@RequestBody Developer developer){
        DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public void delete(@PathVariable Long id){
        DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
    }
}
