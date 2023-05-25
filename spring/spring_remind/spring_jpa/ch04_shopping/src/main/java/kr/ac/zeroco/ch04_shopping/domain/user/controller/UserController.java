package kr.ac.zeroco.ch04_shopping.domain.user.controller;

import kr.ac.zeroco.ch04_shopping.domain.user.entity.User;
import kr.ac.zeroco.ch04_shopping.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* *
     * 회원 등록 기능
     */
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    /* *
     * 회원 조회 기능 - 회원 목록
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
