package com.voxesoftware.springboot.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.voxesoftware.springboot.dtos.UserRecordDto;
import com.voxesoftware.springboot.models.UserModel;
import com.voxesoftware.springboot.repositories.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

//bcryptEncoder.encode
@RestController
public class UserController {
	
	@Autowired
    UserRepository userRepository;
	
	//Cadastrar usuario
    @PostMapping("/user")
    @Operation(summary = "Cadastra um usuario")
    
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDto userRecordDto){
    	ResponseEntity<List<UserModel>> users = getAllUsers();
    	var userModel = new UserModel(); 
    	
    	//passando propriedades de validação do dto para o model
        BeanUtils.copyProperties(userRecordDto, userModel);
        
        for(var user : users.getBody()) {
            if (Objects.equals(user.getUserName(), userModel.getUserName()) || Objects.equals(user.getEmail(), userModel.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body((UserModel) userRepository.findAll());
            }
            
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
      
    }
    
    //pegar todos os usuarios
    @GetMapping("/users")
    @Operation(summary = "Tras todos os usuarios")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }
}
