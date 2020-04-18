package edu.stevens.cs555;

/**
 * Import files
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Class to set/get Individual entry.
 * 
 * @author Kunj Desai, Dhruval Thakkar, Nihir Patel,
 * 		   Vyom Shah, Yash Navadiya
 *
 */
public class IndividualEntry {
	
	private String id,name,gender,birthday,age,death,childOf;
	Set<String> child = new HashSet<String>(); 
	Set<String> spous= new HashSet<String>(); 
	
	//Constructor
	public IndividualEntry(String ind) {
		this.id=ind;
	}
	
	//Getter and setter methods
	public String getId() {
		return (String)this.id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAge() throws ParseException {
		this.setAge();
		return age;
	}
	/**
	 * ID: US27
	 * Name: Include Individual age
	 * Description: Include person's current age when listing individuals
	 * Date created: Feb 17, 202012:14:12 AM
	 */
	public void setAge() throws ParseException {
		if(birthday==null) {
			this.age="NA";
			return;
		}
		if(death==null) {
			SimpleDateFormat formatter3=new SimpleDateFormat("dd MMM yyyy");
			Date date3=formatter3.parse(this.birthday);
			Date cur=new Date();
		    long diffInMillies = Math.abs(cur.getTime() - date3.getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    int years=(int)diff/365;
		    this.age =""+years+"";
		}
		else {
			SimpleDateFormat formatter3=new SimpleDateFormat("dd MMM yyyy");
			Date date3=formatter3.parse(this.birthday);
			Date cur=formatter3.parse(this.death);
		    long diffInMillies = Math.abs(cur.getTime() - date3.getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    int years=(int)diff/365;
		    this.age =""+years+"";
		}
		
	}
	
	public String getChildOf() {
		return childOf;
	}
	public void setChildOf(String childOf) {
		this.childOf = childOf;
	}
	public String getAlive() {
		if(this.death == null)
			return "True";
		else 
			return "False";
	}
	public void setAlive(String alive) {
	}
	public Set<String> getChild() {
		return child;
	}
	public void setChild(Set<String> child) {
		this.child = child;
	}
	public Set<String> getSpous() {
		return spous;
	}
	public void setSpous(Set<String> spous) {
		this.spous = spous;
	}
	public String getDeath() {
		return death;
	}
	public void setDeath(String death) {
		this.death = death;
	}		

}
