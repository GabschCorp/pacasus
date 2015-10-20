package de.berufsschule_freising.pacasus.model.entity;

/**
 * Created by Jooly on 16.10.2015.
 */
public class User {

	protected Integer id;
	protected String name;
	protected String password;

	public void setID(Integer id){
		this.id = id;
	}

	public Integer getID(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}
}
