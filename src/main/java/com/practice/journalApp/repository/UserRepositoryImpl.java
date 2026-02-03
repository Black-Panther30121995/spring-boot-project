package com.practice.journalApp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Component;

import com.practice.journalApp.entity.User;

@Component
public class UserRepositoryImpl {
	
	@Autowired
	private MongoTemplate mongoTemplate;


	
	public List<User> getUserForSA(){
		Query query=new Query();
		
		
		//query.addCriteria(Criteria.where("userName").is("alice"));
		//		query.addCriteria(Criteria.where("field").ne("value"));  --> ne is not equal
		//lt is less than, lte is less than equal to, gt is greater than, gte is greater than equal to
		//nin means not in list, in means in list 
		//.type -- means the type should be this
		
		query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
//		query.addCriteria(Criteria.where("email").ne(null).ne(""));
		query.addCriteria(Criteria.where("SentimentAnalysis").is(true));
		
		
		
//		Criteria criteria =new Criteria();
//		query.addCriteria(criteria.orOperator(   //using or Operator
//				Criteria.where("email").exists(true),
//				Criteria.where("SentimentAnalysis").is(true)));
		
		
		List<User> users=mongoTemplate.find(query, User.class);
		return users;
	}
	
}
