package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Thesis {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String titleJa = "";
	private String titleEn = "";
	private String author1 = "";
	private String author2 = "";
	private String author3 = "";
	private String author4 = "";
	private String author5 = "";
	private String author6 = "";
	private String abs = "";
	private String NAID = "";
	private String NCID = "";
	private String ISSN = "";
	private String NDL_ID = "";
	private String NDL_code = "";
	private String pdf_path = "";
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitleJa() {
		return this.titleJa;
	}
	public void setTitleJa(String titleJa) {
		this.titleJa = titleJa;
	}
	public String getTitleEn() {
		return this.titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	public String getAuthor1() {
		return this.author1;
	}
	public void setAuthor1(String author1) {
		this.author1 = author1;
	}
	public String getAuthor2() {
		return this.author2;
	}
	public void setAuthor2(String author2) {
		this.author2 = author2;
	}
	public String getAuthor3() {
		return this.author3;
	}
	public void setAuthor3(String author3) {
		this.author3 = author3;
	}
	public String getAuthor4() {
		return this.author4;
	}
	public void setAuthor4(String author4) {
		this.author4 = author4;
	}
	public String getAuthor5() {
		return this.author5;
	}
	public void setAuthor5(String author5) {
		this.author5 = author5;
	}
	public String getAuthor6() {
		return this.author6;
	}
	public void setAuthor6(String author6) {
		this.author6 = author6;
	}
	public String getAbs() {
		return this.abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public String getNAID() {
		return this.NAID;
	}
	public void setNAID(String nAID) {
		this.NAID = nAID;
	}
	public String getNCID() {
		return this.NCID;
	}
	public void setNCID(String nCID) {
		this.NCID = nCID;
	}
	public String getISSN() {
		return this.ISSN;
	}
	public void setISSN(String iSSN) {
		this.ISSN = iSSN;
	}
	public String getNDL_ID() {
		return this.NDL_ID;
	}
	public void setNDL_ID(String nDL_ID) {
		this.NDL_ID = nDL_ID;
	}
	public String getNDL_code() {
		return this.NDL_code;
	}
	public void setNDL_code(String nDL_code) {
		this.NDL_code = nDL_code;
	}
	public void setPdf_path(String pdf_path) {
		this.pdf_path = pdf_path;
	}
	public String getPdf_path() {
		return this.pdf_path;
	}
}
