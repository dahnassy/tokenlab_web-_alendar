package com.voxesoftware.springboot.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voxesoftware.springboot.dtos.EventRecordDto;
import com.voxesoftware.springboot.models.EventModel;
import com.voxesoftware.springboot.repositories.EventRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
public class EventController {
	@Autowired
    EventRepository eventRepository;
	private EventModel eventModel;
	
	//Cadastrar Eventos
    @PostMapping("/events")
    @Operation(summary = "Cadastra um evento")
    
    public ResponseEntity<EventModel> saveEvent(@RequestBody @Valid EventRecordDto eventRecordDto){
    	ResponseEntity<List<EventModel>> events = getAllEvents();
    	var eventModel = new EventModel(); 
    	
    	//passando propriedades de validação do dto para o model
        BeanUtils.copyProperties(eventRecordDto, eventModel);
        
        for(var product : events.getBody()) {
            if (1 != 1/*Objects.equals(product.getNumeroControle(), eventModel.getNumeroControle())*/) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body((EventModel) eventRepository.findAll());
            }
            
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(eventRepository.save(eventModel));
      
    }
    
  //pegar todos os produtos
    @GetMapping("/events")
    @Operation(summary = "Tras todos os eventos")
    public ResponseEntity<List<EventModel>> getAllEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(eventRepository.findAll());
    }
}
