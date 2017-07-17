package br.com.waes.jb64diff.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JB64Data {

	@Id
	private Long id;
	private String left;
	private String right;
	
	public JB64Data(){
		super();
	}
	
	public JB64Data(Long id, String left, String right) {
		super();
		this.id = id;
		this.left = left;
		this.right = right;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	
	
	
}
