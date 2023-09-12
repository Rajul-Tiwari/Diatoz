package com.diatoz.controller;

import com.diatoz.Model.Marks;
import com.diatoz.Model.UserModel;
import com.diatoz.dtos.*;
import com.diatoz.service.LoginHandler;
import com.diatoz.service.UserModelServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
@Slf4j
public class TeacherController {

    private final UserModelServiceImpl userModelServiceImpl;


    private final PasswordEncoder passwordEncoder;

    private final LoginHandler loginHandler;


    @GetMapping("/login")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<JwtResponse> loginHandler(@RequestBody LoginDto loginDto) {

        return ResponseEntity.ok(loginHandler.login(loginDto));
    }



    @GetMapping("/getProfile")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<UserModel> getProfile() {
        log.info("getProfile () method called");
        return ResponseEntity.ok(loginHandler.getCurrentUser());
    }

    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<UserModel> addStudent(@RequestBody SignupDto signupDto) {
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        return new ResponseEntity<>(userModelServiceImpl.addStudent(signupDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllStudent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<UserModel>> getAllStudent() {
        return ResponseEntity.ok(userModelServiceImpl.getAllStudent());
    }

    @PostMapping("/addMultiStudent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> addMultipleStudent(@RequestBody List<SignupDto> signupDtoList) {

        for (SignupDto signupDto : signupDtoList) {
            signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        }

        return new ResponseEntity<>(userModelServiceImpl.addMultiStudent(signupDtoList), HttpStatus.CREATED);
    }


    @PostMapping("/addMarksToStudent/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> addMarksToStudent(@PathVariable("id") String id, @RequestBody  MarksDto marksDto) {
        return new ResponseEntity<>(userModelServiceImpl.addMarksToStudent(id, marksDto), HttpStatus.CREATED);
    }

    @GetMapping("/getMarksdetailsOfStudent/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<StudentResponse> getMarksdetailsOfStudent(@PathVariable("id") String id) {
        return ResponseEntity.ok(userModelServiceImpl.getStudentMarks(id));
    }

}
