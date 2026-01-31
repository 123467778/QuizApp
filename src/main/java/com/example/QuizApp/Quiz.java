package com.example.QuizApp;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Quiz {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	
	@ManyToMany
	private List<Question> question;

	@ManyToOne
	private User user;

	private int score;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
	this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Question> getQuestion() {
		return question;
	}

	public void setQuestion(List<Question> question) {
		this.question = question;
	}

public int getScore(){
		return score;
}

public void setScore(int score){
		this.score=score;
}

     public User getUser(){
		return user;
      }
	public void setUser(User user){
		this.user=user;
	}

}
