package com.practice.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.JournalEntryRepository;



@Service 
public class JournalEntryService {
	
	@Autowired
	private	JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;
	

	
	
	@Transactional
	public void saveEntry(JournalEntry journalEntry, String userName)
	{
		try {
		User user=userService.findByUserName(userName);
		journalEntry.setDate(LocalDateTime.now());
		JournalEntry saved=journalEntryRepository.save(journalEntry);
		user.getJournalEntries().add(saved);
		userService.saveUser(user);
		}
		catch(Exception e)
		{

			throw new RuntimeException("An error occured while saving the entry.",e);
		}

	}
	
	public void saveEntry(JournalEntry journalEntry)
	{
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getAll()
	{
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> findById(ObjectId id)
	{
		return journalEntryRepository.findById(id);
	}
	
	@Transactional
	public boolean deleteById(ObjectId id, String userName)
	{
		User user=userService.findByUserName(userName);
		boolean removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
		if(removed)
		{
			userService.saveUser(user);
			journalEntryRepository.deleteById(id);
			return true;
		}
		return false;

	}
//	public List<JournalEntry> findByUserName(String userName)
//	{
//		
//	}
	
}
