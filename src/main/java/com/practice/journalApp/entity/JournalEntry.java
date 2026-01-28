package com.practice.journalApp.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection="journal_entries")
@Getter
@Setter
public class JournalEntry {
	
	@Id 
	private ObjectId id;
	private String title;
	private String content;
	private LocalDateTime date;
	
}
