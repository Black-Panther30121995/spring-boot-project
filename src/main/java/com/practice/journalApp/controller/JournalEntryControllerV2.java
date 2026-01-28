package com.practice.journalApp.controller;

import java.util.Optional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.service.JournalEntryService;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

	@Autowired
	private JournalEntryService journalEntryService;
	
	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAll()
	{
		List<JournalEntry> all=journalEntryService.getAll();
		if(all!=null && !all.isEmpty())
		{
			return new ResponseEntity(all,HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT); 
	}
	
	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry)
	{
		try {
			myEntry.setDate(LocalDateTime.now());
			journalEntryService.saveEntry(myEntry);
			return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId)
	{
		Optional<JournalEntry> journalEntry= journalEntryService.findById(myId);
		if(journalEntry.isPresent())
		{
			return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId)
	{
		journalEntryService.deleteById(myId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@PutMapping("id/{myId}")
	public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry entry)
	{
		JournalEntry old=journalEntryService.findById(myId).orElse(null);
		if(old!=null)
		{
			old.setTitle(entry.getTitle()!=null && !entry.getTitle().equals("")?entry.getTitle():old.getTitle());
			old.setContent(entry.getContent()!=null && !entry.getContent().equals("")?entry.getContent():old.getContent());
			journalEntryService.saveEntry(old);
			return new ResponseEntity(old,HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
