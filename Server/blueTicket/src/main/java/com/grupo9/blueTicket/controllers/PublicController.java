package com.grupo9.blueTicket.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo9.blueTicket.models.entities.Event;
import com.grupo9.blueTicket.models.entities.User;
import com.grupo9.blueTicket.services.EventService;
import com.grupo9.blueTicket.services.UserService;
import com.grupo9.blueTicket.models.dtos.EmailDTO;
import com.grupo9.blueTicket.models.dtos.PageDTO;

@RestController
@RequestMapping("/public")
@CrossOrigin("*")
public class PublicController {
	
	@Autowired
	private EventService eventService;
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/home")
	public ResponseEntity<?> getAllEvents(@RequestParam(defaultValue = "1") int page,
			                              @RequestParam(defaultValue = "6") int size,
			                              @RequestParam(defaultValue = "") String title){
		
		long totalElements = eventService.count();
	    int totalPages = (int) Math.ceil((double) totalElements / size);

	    List<Event> eventsMatch = new ArrayList<>();

	    for (int i = 1; i <= totalPages; i++) {
	        Page<Event> events = eventService.findAll(PageRequest.of(i - 1, size));

	        for (Event event : events.getContent()) {
	            if (event.getTitle().toUpperCase().contains(title.toUpperCase())) {
	                eventsMatch.add(event);
	            }
	        }
	    }

	    List<Event> songsOnCurrentPage = eventsMatch.subList((page - 1) * size, Math.min(page * size, eventsMatch.size()));
	    PageDTO<Event> songPageDTO = new PageDTO<>(songsOnCurrentPage, page, size, eventsMatch.size(), totalPages);

	    return new ResponseEntity<>(songPageDTO, HttpStatus.OK);
	}
	@GetMapping("/get-user")
	public ResponseEntity<?> getUserId(@RequestBody EmailDTO email){
		List<User> user = userService.findAll();
		UUID id = null;
		for(User u : user) {
			if (u.getEmail().equals(email.getEmail())) {
				id = u.getId();
			}
		}
		if(id != null) {
			return new ResponseEntity<>(id, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
