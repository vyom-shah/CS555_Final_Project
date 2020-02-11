package edu.stevens.cs570.assignments.tests;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;  
import javax.swing.*;

class ind {
	private String Id,name,gender,birthday,age,alive,death;
	Set<String> child = new HashSet<String>(); 
	Set<String> spous= new HashSet<String>(); 
	public ind(String ind) {
		this.Id=ind;
	}
	public String getId() {
		return (String)this.Id;
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
	public String getAlive() {
		if(this.death == null)
			return "True";
		else 
			return "False";
	}
	public void setAlive(String alive) {
		this.alive = alive;
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
class fam{
	private String Id,Married,divorced,h_id,h_name,w_id;
	JTable j;
	Set<String> child= new HashSet<String>(); 
	public fam(String fam) {
		this.Id=fam;
	}
	public String getMarried() {
		return Married;
	}
	public void setMarried(String married) {
		Married = married;
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

public class proj03 {
	public static void main(String [] args) throws IOException, ParseException{
		
		String inputfile= "C:\\Users\\Nihir\\Desktop\\cs555\\Project_Input(gedcom).ged";
    	File file = new File(inputfile);
    	FileWriter fw=new FileWriter("C:\\Users\\Nihir\\Desktop\\cs555\\Project_Input(parsed).txt"); 
    	
    	HashMap<String, ArrayList<String>> tagsmap  = new HashMap<>(); 
    	ArrayList<String> zero=new ArrayList<>(Arrays.asList("INDI", "FAM", "HEAD","TRLR","NOTE"));
    	ArrayList<String> one=new ArrayList<>(Arrays.asList("NAME", "SEX", "BIRT","DEAT","FAMC","FAMS","MARR","HUSB","WIFE","CHIL","DIV"));
    	ArrayList<String> two=new ArrayList<>(Arrays.asList("DATE"));
    	ArrayList<String> three=new ArrayList<>(Arrays.asList(""));
    	ArrayList<String> four=new ArrayList<>(Arrays.asList(""));
    	tagsmap.put("0", zero); 
    	tagsmap.put("1", one); 
    	tagsmap.put("2", two);
    	tagsmap.put("3", three); 
    	tagsmap.put("4", four); 
    	BufferedReader br = null;
    	try {
			br = new BufferedReader(new FileReader(file));
			 String contentLine = br.readLine();
			 while (contentLine != null) {
			      //System.out.println("-->"+contentLine);
			      StringBuilder str = new StringBuilder();
			      String[] arrOfStr = contentLine.split(" ");
			      if(arrOfStr.length>=3) {
			      if(arrOfStr[2].equals("INDI") || arrOfStr[2].equals("FAM")) {
			    	 // System.out.println("dbhjd");
			    	  str.append(arrOfStr[0]);
			    	  str.append("|");
			    	  str.append(arrOfStr[2]+"|");
			    	  if(tagsmap.get(arrOfStr[0]).contains(arrOfStr[2])) {
				    	  str.append("Y|");
				      }
				      else {
				    	  str.append("N|");
				      }
			    	  str.append(arrOfStr[1]);
			    	  fw.write(str.toString()+"\n");
			    	 // System.out.println("<--"+str.toString());
			    	  contentLine = br.readLine();
			    	  continue;
			      }}
			      str.append(arrOfStr[0]);
			      str.append("|");
			      str.append(arrOfStr[1]+"|");
			      if(tagsmap.get(arrOfStr[0]).contains(arrOfStr[1])) {
			    	  str.append("Y|");
			      }
			      else {
			    	  str.append("N|");
			      }
			      for(int i=2;i<arrOfStr.length;i++) {
			    	  str.append(arrOfStr[i]+" ");
			      }
			      fw.write(str.toString()+"\n");
			    //  System.out.println("<--"+str.toString());
			      contentLine = br.readLine();
			   }
			 fw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		
		
		String inputfile1= "C:\\Users\\Nihir\\Desktop\\cs555\\Project_Input(parsed).txt";
    	File file1 = new File(inputfile1);
    	
    	HashMap<String, ind> hind= new HashMap<>(); 
    	HashMap<String, fam> hfam= new HashMap<>();
    
    //	BufferedReader br = null;
    	ind curI=null;
    	fam curF=null;
    	boolean inf=false;
    	boolean faf=false;
    	boolean b=false;
   	  	boolean d=false;
   	  	boolean m=false;
   	  	boolean di=false;
   	  	
    	try {
			br = new BufferedReader(new FileReader(file1));
			 String contentLine = br.readLine();
			 while (contentLine != null) {
				 System.out.println(contentLine);
			      String[] arrOfStr = contentLine.split("\\|");
			      if(arrOfStr[2].equals("N"))
			      {
			    	  contentLine = br.readLine();
			    	  continue;
			      }
			      if(arrOfStr[0].equals("0") && arrOfStr[1].equals("INDI")) {
			    	  curI=new ind(arrOfStr[3]);
			    	  hind.put(arrOfStr[3], curI);
			    	  contentLine = br.readLine();
			    	  inf=true;
			    	  faf=false;
			    	  continue;

			      }
			      if(arrOfStr[0].equals("0") && arrOfStr[1].equals("FAM")) {
			    	  curF=new fam(arrOfStr[3]);
			    	  hfam.put(arrOfStr[3], curF);
			    	  contentLine = br.readLine();
			    	  faf=true;
			    	  inf=false;
			    	  continue;
			      }
			      if(inf){
			    	 
			    	switch(arrOfStr[1]) {
			    	case "NAME":
			    		curI.setName(arrOfStr[3]);
			    		//contentLine = br.readLine();
			    		break;
			    	case "SEX":
			    		curI.setGender(arrOfStr[3]);
			    		//contentLine = br.readLine();
			    		break;
			    	case "BIRT":
			    		b=true;
			    		d=false;
			    		//contentLine = br.readLine();
			    		break;
			    	case "DEAT":
			    		b=false;
			    		d=true;
			    		//contentLine = br.readLine();
			    		break;
			    	case "FAMC":
			    		curI.getChild().add(arrOfStr[3]);
			    		//contentLine = br.readLine();
			    		break;
			    	case "FAMS":
			    		curI.getSpous().add(arrOfStr[3]);
			    		//contentLine = br.readLine();
			    		break;
			    	case "DATE":
			    		if(b) {
			    			curI.setBirthday(arrOfStr[3]);
			    			//contentLine = br.readLine();
			    			b=false;
				    		break;
			    		}
			    		else if(d) {
			    			curI.setDeath(arrOfStr[3]);
			    			//contentLine = br.readLine();
			    			d=false;
			    			break;
			    		}
			    		break;
			    		default:
			    			//contentLine = br.readLine();
				    		break;
			    	}
			    	contentLine = br.readLine();
			    	continue;
			      }
			      else if(faf) {
			    	  switch(arrOfStr[1]) {
			    	  case "MARR":
				    		m=true;
				    		//contentLine = br.readLine();
				    		break;
			    	  case "HUSB":
			    		    curF.setH_id(arrOfStr[3]);
				    		//contentLine = br.readLine();
				    		break;
			    	  case "WIFE":
				    		curF.setW_id(arrOfStr[3]);
				    		//contentLine = br.readLine();
				    		break;
			    	  case "CHIL":
				    		curF.getChild().add(arrOfStr[3]);
				    		//contentLine = br.readLine();
				    		break;
			    	  case "DIV":
			    		  	di=true;
				    		//contentLine = br.readLine();
				    		break;
			    	  case "DATE":
			    		  if(m) {
				    			curF.setMarried(arrOfStr[3]);
				    			//contentLine = br.readLine();
				    			m=false;
					    		break;
				    		}
				    		else if(di) {
				    			curF.setDivorced(arrOfStr[3]);
				    			//contentLine = br.readLine();
				    			di=false;
				    			break;
				    		}
			    		  break;
			    	  
			    	  }
			    	  contentLine = br.readLine();
			    	  continue;
			      } 
			      contentLine = br.readLine();
			     
			   }

			  System.out.println();
			  System.out.println("Individual"); 
			 Iterator hmIterator = hind.entrySet().iterator(); 
			 System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", "ID","Name","Gender","Birthday","Age","Alive","Death","Child","Spouse");
			 for (Map.Entry mapElement : hind.entrySet()) { 
				
		            String key = (String)mapElement.getKey(); 
		  
					ind value = (ind)mapElement.getValue(); 
					
					SimpleDateFormat formatter3=new SimpleDateFormat("dd MMM yyyy");
					Date birt=formatter3.parse(value.getBirthday());
		            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		            String birthdate=formatter.format(birt);
		            String Death="NA";
		            if(value.getDeath()!=null) {
		            	Date deat=formatter3.parse(value.getBirthday());
			           
			            Death=formatter.format(birt);
		            }
		            
					if(value.getChild().isEmpty() && value.getSpous().isEmpty()){
						System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", key,value.getName(),value.getGender(),birthdate,value.getAge(),value.getAlive(),Death,"NA","NA");						 
					}
					else if(value.getChild().isEmpty() && !value.getSpous().isEmpty()){
						System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", key,value.getName(),value.getGender(),birthdate,value.getAge(),value.getAlive(),Death,"NA",value.getSpous());						 	
					}
					else if(!value.getChild().isEmpty() && value.getSpous().isEmpty()){
						System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", key,value.getName(),value.getGender(),birthdate,value.getAge(),value.getAlive(),Death,value.getChild(),"NA");						 	
					}
					else{
						 System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", key,value.getName(),value.getGender(),birthdate,value.getAge(),value.getAlive(),Death,value.getChild(),value.getSpous());
					}

				} 
				
				System.out.println();
				System.out.println("Family");
				Iterator hmIterator1 = hfam.entrySet().iterator();
				System.out.format("%-10s%-15s%-10s%-15s%-25s%-10s%-20s%-15s\n", "ID","Married","Divorced","Husband ID","Husband Name","Wife ID","Wife Name","Children");		
				for (Map.Entry mapElement : hfam.entrySet()) { 
				
		            String key = (String)mapElement.getKey(); 
		  
					fam value = (fam)mapElement.getValue();
					SimpleDateFormat formatter3=new SimpleDateFormat("dd MMM yyyy");
					
		            String Married="NA";
		            if(value.getMarried()!=null) {
		            	Date mar=formatter3.parse(value.getMarried());
			            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			            Married=formatter.format(mar);
		            }
					String divorce="NA";
					if(value.getDivorced()!=null) {
						Date div=formatter3.parse(value.getMarried());
			            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			            divorce=formatter.format(div);
					}
					if(value.getChild().isEmpty()){
						System.out.format("%-10s%-15s%-10s%-15s%-25s%-10s%-20s%-15s\n", key,Married,divorce,value.getH_id(),hind.get(value.getH_id().trim()).getName(),value.getW_id(),hind.get(value.getW_id().trim()).getName(),"NA");
					}
					else{
						 System.out.format("%-10s%-15s%-10s%-15s%-25s%-10s%-20s%-15s\n", key,Married,divorce,value.getH_id(),hind.get(value.getH_id().trim()).getName(),value.getW_id(),hind.get(value.getW_id().trim()).getName(),value.getChild());
					}
		        } 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
