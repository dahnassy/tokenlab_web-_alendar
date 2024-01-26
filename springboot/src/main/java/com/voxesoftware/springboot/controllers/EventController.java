package com.voxesoftware.springboot.controllers;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.voxesoftware.springboot.dtos.EventRecordDto;
import com.voxesoftware.springboot.models.EventModel;
import com.voxesoftware.springboot.repositories.EventRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController("/Events")
public class EventController {
	@Autowired
    EventRepository eventRepository;
	//private EventModel eventModel;
	
	//Cadastrar Eventos
    @PostMapping("/events")
    @Operation(summary = "Cadastra um evento")
    
    public ResponseEntity<EventModel> saveEvent(@RequestBody @Valid EventRecordDto eventRecordDto){
    	ResponseEntity<List<EventModel>> events = getAllEvents();
    	var eventModel = new EventModel(); 
    	
    	//passando propriedades de validação do dto para o model
        BeanUtils.copyProperties(eventRecordDto, eventModel);
        
        for(var event : events.getBody()) {
            if (Objects.equals(event.getName(), eventModel.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body((EventModel) eventRepository.findAll());
            }
            
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(eventRepository.save(eventModel));
      
    }
    
    //pegar todos os eventos
    @GetMapping("/events")
    @Operation(summary = "Tras todos os eventos")
    public ResponseEntity<List<EventModel>> getAllEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(eventRepository.findAll());
    }
    
    //pegar produto por id
    @GetMapping("/events/{id}")
    @Operation(summary = "Tras os evento pelo id")
    public ResponseEntity<Object> getOneProductEvent(@PathVariable(value="id") UUID id){
        Optional<EventModel> event0 = eventRepository.findById(id);
        if(event0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(event0.get());
    }
    

    //atualizar evento
    @PutMapping("/events/{id}")
    @Operation(summary = "Edita evento pelo id")
    public ResponseEntity<Object> updateEvent(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid EventRecordDto eventRecordDto){
        Optional<EventModel> event0 = eventRepository.findById(id);
        if(event0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        var eventModel = event0.get();
        BeanUtils.copyProperties(eventRecordDto, eventModel);
        return ResponseEntity.status(HttpStatus.OK).body(eventRepository.save(eventModel));
    }
    
    //deletar evento
    @DeleteMapping("/events/{id}")
    @Operation(summary = "Deleta evento pelo id")
    public ResponseEntity<Object> deleteEvent(@PathVariable(value="id") UUID id){
        Optional<EventModel> event0 = eventRepository.findById(id);

        if(event0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found!");
        }

        eventRepository.delete(event0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Event deleted successfully");
    }
}
