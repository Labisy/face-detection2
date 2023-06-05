package ru.stepchenkov.rest.controller.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepchenkov.rest.entity.Post;
import ru.stepchenkov.rest.exception.PostAlreadyExistsException;
import ru.stepchenkov.rest.exception.PostNotFoundException;
import ru.stepchenkov.rest.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/save")
    public ResponseEntity savePost(@RequestBody Post post) {
        try {
            return new ResponseEntity(postService.save(post), HttpStatus.OK);
        } catch (PostAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{value}")
    public ResponseEntity deletePost(@PathVariable String value) {
        try {
            postService.delete(value);
            return ResponseEntity.ok().build();
        } catch (PostNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllPost() {
        return new ResponseEntity(postService.getAll(), HttpStatus.OK);
    }
}
