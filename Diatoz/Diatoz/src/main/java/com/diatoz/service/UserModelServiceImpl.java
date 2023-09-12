package com.diatoz.service;

import com.diatoz.Model.Marks;
import com.diatoz.Model.MyRole;
import com.diatoz.Model.Role;
import com.diatoz.Model.UserModel;
import com.diatoz.dtos.MarksDto;
import com.diatoz.dtos.MarksResponse;
import com.diatoz.dtos.SignupDto;
import com.diatoz.dtos.StudentResponse;
import com.diatoz.repository.MarksRepository;
import com.diatoz.repository.RoleRepository;
import com.diatoz.repository.UserModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserModelServiceImpl  implements UserModelService  , UserDetailsService , MarksService{

    private final RoleRepository roleRepository;
    private final UserModelRepository userModelRepository;

    private  final  MarksRepository marksRepository;

    private final MongoTemplate mongoTemplate;





    @Override
    public String signup(SignupDto signupDto,  boolean isAdmin) {

        Role role = new Role();
         log.info("Admin");
        if(isAdmin){
            role = roleRepository.findByRole(MyRole.valueOf("ADMIN"));
        }
        boolean result = addUser(signupDto, role, isAdmin);

        return result ? "User added successfully" : "User not added";
    }

    @Override
    public UserModel addTeacher(SignupDto signupDto) {
        Role role = new Role();
        log.info("Teacher");

            role = roleRepository.findByRole(MyRole.valueOf("TEACHER"));

        boolean result = addUser(signupDto, role, false);

        if (result) {
            log.info("Teacher added successfully");
        } else {
            log.info("Teacher not added");
        }

        return userModelRepository.findByEmail(signupDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found")) ;

    }

    @Override
    public List<UserModel> getAllUsers() {
       List<UserModel> allUser = (List<UserModel>) userModelRepository.findAll();
       if(allUser.isEmpty()){
           throw new RuntimeException("No user found");
       }
         return allUser;
    }

    @Override
    public String deleteUser(String id) {
        UserModel userModel = userModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userModelRepository.delete(userModel);
        log.info("User deleted successfully");
        return "User deleted successfully";
    }

    @Override
    public UserModel addStudent(SignupDto signupDto) {
        Role role = new Role();
        log.info("Student");
        role = roleRepository.findByRole(MyRole.valueOf("STUDENT"));

        boolean result = addUser(signupDto, role, false);
        if (result) {
            log.info("Student added successfully");
        } else {
            log.info("Student not added");
        }
        return userModelRepository.findByEmail(signupDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found")) ;
    }

    @Override
    public List<UserModel> getAllStudent() {

        List<UserModel> allStudent = (List<UserModel>) userModelRepository.findAll();

        if(allStudent.isEmpty()){
            throw new RuntimeException("No student found");
        }
        List<UserModel> allStudentList = new ArrayList<>();
        for(UserModel userModel : allStudent){
            if(userModel.getRole().getRole().equals(MyRole.STUDENT)){
                allStudentList.add(userModel);
            }
        }

        return allStudentList;

    }
    @Override
    public List<UserModel> getAllTeacher() {

        List<UserModel> allStudent = (List<UserModel>) userModelRepository.findAll();

        if(allStudent.isEmpty()){
            throw new RuntimeException("No student found");
        }
        List<UserModel> allStudentList = new ArrayList<>();
        for(UserModel userModel : allStudent){
            if(userModel.getRole().getRole().equals(MyRole.TEACHER)){
                allStudentList.add(userModel);
            }
        }

        return allStudentList;

    }
    @Override
    public String addMultiStudent(List<SignupDto> signupDtoList) {
         for(SignupDto signupDto : signupDtoList){
             Role role = new Role();
             role = roleRepository.findByRole(MyRole.valueOf("STUDENT"));
             boolean result = addUser(signupDto, role, false);
         }
         return "All student added successfully";
    }

    @Override
    public String modifyUser(String id, SignupDto signupDto) {

      UserModel findUser  =   userModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        findUser.setName(signupDto.getName());
        findUser.setEmail(signupDto.getEmail());
        findUser.setPassword(signupDto.getPassword());
         UserModel modifySavedUser  =    userModelRepository.save(findUser);
        return "User modified successfully";
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userModelRepository.findByEmail(username).
                orElseThrow(() -> new RuntimeException("User not found"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        Role role = userModel.getRole();
        authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
        return new UserDetailsImpl(userModel.getEmail(), userModel.getPassword(), authorities);
    }

    private   boolean addUser(SignupDto signupDto, Role role, boolean isAdmin){
        log.info("Inside addUser method of UserModelServiceImpl");
        UserModel userModel = UserModel.builder()
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())
                .isAdmin(isAdmin)
                .role(role)
                .build();

        UserModel savedUserModel = userModelRepository.save(userModel);
         log.info("User saved successfully"+ savedUserModel);

        if(savedUserModel!= null) {
            return true;
        }

        else{
            return false;
        }

    }


    @Override
    public String addMarksToStudent(String id, MarksDto marksDto) {
        UserModel userModel = userModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Marks marks = Marks.builder()
                        .subjectOneMarks(marksDto.getSubjectOneMarks())
                        .subjectTwoMarks(marksDto.getSubjectTwoMarks())
                        .subjectThreeMarks(marksDto.getSubjectThreeMarks())
                        .subjectFourMarks(marksDto.getSubjectFourMarks())
                        .subjectFiveMarks(marksDto.getSubjectFiveMarks())
                                .student(userModel)
                                        .semester(marksDto.getSemester())
                                                .build();

        marks.setStudent(userModel);

        Marks savedMarks = marksRepository.save(marks);

        log.info("Marks saved successfully"+ savedMarks);

        if(savedMarks != null){
            return "Marks added successfully";
        }
        else{
            return "Marks not added";
        }



    }

    @Override
    public StudentResponse getStudentMarks(String id) {

       UserModel userModel =  userModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

          List<Marks> allMarksList = (List<Marks>) marksRepository.findAll();
        if(allMarksList.isEmpty()){
            throw new RuntimeException("No marks found");
        }
        List<Marks> marksList = new ArrayList<>();
        for(Marks marks : allMarksList){
            if(marks.getStudent().getId().equals(id)){
                marksList.add(marks);
            }
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setName(userModel.getName());
        studentResponse.setEmail(userModel.getEmail());
        studentResponse.setId(userModel.getId());

       List<MarksResponse> marksResponseList =    marksList.stream().map(e->
                    MarksResponse.builder()
                            .id(e.getId())
                            .subjectOneMarks(e.getSubjectOneMarks())
                            .subjectTwoMarks(e.getSubjectTwoMarks())
                            .subjectThreeMarks(e.getSubjectThreeMarks())
                            .subjectFourMarks(e.getSubjectFourMarks())
                            .subjectFiveMarks(e.getSubjectFiveMarks())
                            .semester(e.getSemester())
                            .build()
                  )
                    .collect(Collectors.toList());

        studentResponse.setMarksList(marksResponseList);

        return studentResponse;
    }
}

