package com.webjournal.controller;


import com.webjournal.dto.*;
import com.webjournal.entities.User;
import com.webjournal.mappers.UserMapper;
import com.webjournal.repositories.UserRepository;
import com.webjournal.services.impls.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
@author Микола
@project High_school_library
@class UserController
@version 1.0.0
@since 17.08.2022 - 22.29
*/
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl service;
    private  final UserRepository repository;
    private final UserMapper mapper;

    public UserController(UserServiceImpl service, UserRepository repository, UserMapper mapper) {
        this.service = service;
        this.repository = repository;
        this.mapper = mapper;
    }

    @ApiOperation(value = "View a page of users", response = PageDTO.class,
            notes = "Get search object where describe what page need, page size, sort field, sort type and name or surname user. Return PageDTO object.",
            tags={ "get-page"})
    @PostMapping("/search")
    public PageDTO<AuthorDTO> search(@RequestBody SearchDTO search) {
        LOGGER.info("search(search={})", search);
        return service.getPage(search);
    }
    @ApiOperation(value = "Get user", response = User.class,
            notes = "Get user id an return UserDto object.",
            tags={ "get-one-element"})
    @GetMapping("/id={id}")
    public Optional<AuthorDTO> getOneUser(@PathVariable Integer id) {
        return service.getOneUser(id);
    }

    @ApiOperation(value = "Created student", response = Integer.class,
            notes = "Gets StudentDTO, and set it into database.",
            tags={ "created-user"})
    @PostMapping("/create-student")
    public Integer create(@RequestBody AuthorDTO dto){
//        LOGGER.info("userDTO(dto={})", dto);
         return service.create(dto);
    }

    @ApiOperation(value = "Update teacher", httpMethod = "PUT",
            notes = "Gets TeacherDTO, and update it by id if it is where in data base.",
            tags={ "update-user"})
    @PutMapping("/update-teacher")
    public void update(@RequestBody AuthorDTO dto) throws IOException {
//        LOGGER.info("userDTO(dto={})", dto);
        service.update(dto);
    }

    @ApiOperation(value = "Deleted teacher", httpMethod = "DELETE",
            notes = "Gets user id, and deleted, it by id if it is where in data base.",
            tags={ "delete-user"})
    @DeleteMapping("/delete/{id}")
    public void deleteOne(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

    @GetMapping
    public List<AuthorDTO> show() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
