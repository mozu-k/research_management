package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Literature {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id = 0;
	private String title = "";
	private String author = "";
	private String publisher = "";
	private String release_date = "";
	private String isbn = "";

	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getId() {
		return this.id;
	}
	public String getTitle() {
		return this.title;
	}
	public String getAuthor() {
		return this.author;
	}
	public String getPublisher() {
		return this.publisher;
	}
	public String getRelease_date() {
		return this.release_date;
	}
	public String getIsbn() {
		return this.isbn;
	}
}
