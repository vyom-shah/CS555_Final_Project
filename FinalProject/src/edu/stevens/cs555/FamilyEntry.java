package edu.stevens.cs555;

/**
 * Import files
 */
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTable;

/**
 * Class to set/get family entry.
 * 
 * @author Kunj Desai, Dhruval Thakkar, Nihir Patel,
 * 		   Vyom Shah, Yash Navadiya
 * 
 */
public class FamilyEntry {
	private String id,married,divorced,h_id,h_name,w_id;
	JTable j;
	Set<String> child= new HashSet<String>(); 
	
	public FamilyEntry(String fam) {
		this.id = fam;
	}
	public String getId() {
		return id;
	}
	//Setter and getter methods.
	public String getMarried() {
		return married;
	}
	public void setMarried(String married) {
		this.married = married;
	}
	public String getDivorced() {
		return divorced;
	}
	public void setDivorced(String divorced) {
		this.divorced = divorced;
	}
	public String getH_id() {
		return h_id;
	}
	public void setH_id(String h_id) {
		this.h_id = h_id;
	}
	public String getH_name() {
		return h_name;
	}
	public void setH_name(String h_name) {
		this.h_name = h_name;
	}
	public String getW_id() {
		return w_id;
	}
	public void setW_id(String w_id) {
		this.w_id = w_id;
	}
	public Set<String> getChild() {
		return child;
	}
	public void setChild(Set<String> child) {
		this.child = child;
	}
}
