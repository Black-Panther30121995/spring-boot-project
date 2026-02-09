package com.practice.journalApp.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.service.JournalEntryService;
import com.practice.journalApp.service.UserService;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

	@Autowired
	private JournalEntryService journalEntryService;
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser()
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String userName=auth.getName();
		User user=userService.findByUserName(userName);
		
		List<JournalEntry> all=user.getJournalEntries();
		if(all!=null && !all.isEmpty())
		{
			return new ResponseEntity(all,HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT); 
	}
	
	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry)
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String userName=auth.getName();
		try {
			journalEntryService.saveEntry(myEntry,userName);
			return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId)
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String userName=auth.getName();
		
		User user=userService.findByUserName(userName);
		List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
		if(collect!=null && !collect.isEmpty())
		{
			Optional<JournalEntry> journalEntry= journalEntryService.findById(myId);
			if(journalEntry.isPresent())
			{
				return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId)
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String userName=auth.getName();
		
		User user=userService.findByUserName(userName);
		boolean deleted=journalEntryService.deleteById(myId,userName);
		if(deleted)
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("id/{myId}")
	public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry entry)
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String userName=auth.getName();
		
		User user=userService.findByUserName(userName);
		List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
		if(collect!=null && !collect.isEmpty())
		{
			JournalEntry journalEntry= journalEntryService.findById(myId).orElse(null);
			if(journalEntry!=null)
			{
				journalEntry.setTitle(entry.getTitle()!=null && !entry.getTitle().equals("")?entry.getTitle():journalEntry.getTitle());
				journalEntry.setContent(entry.getContent()!=null && !entry.getContent().equals("")?entry.getContent():journalEntry.getContent());
				journalEntryService.saveEntry(journalEntry);
				return new ResponseEntity(journalEntry,HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
