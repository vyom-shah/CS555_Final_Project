package edu.stevens.cs555;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class GenerateOutput {
	
public static void main(String [] args) throws IOException, ParseException{
	try {
		String intitalInputFile= "/Users/kunj/Downloads/test.ged";
		File outputFile = new File(intitalInputFile);
    	FileWriter fw=new FileWriter("/Users/kunj/Downloads/test.txt"); 
    	
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
    	
    	 BufferedReader br = new BufferedReader(new FileReader(outputFile));
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
		
		
		
		String textInputFile= "/Users/kunj/Downloads/test.txt";
    	File validatedFile = new File(textInputFile);
    	
    	HashMap<String, IndividualEntry> hind= new HashMap<>(); 
    	HashMap<String, FamilyEntry> hfam= new HashMap<>();
    
    	IndividualEntry curI=null;
    	FamilyEntry curF=null;
    	boolean inf=false;
    	boolean faf=false;
    	boolean b=false;
   	  	boolean d=false;
   	  	boolean m=false;
   	  	boolean di=false;
   	  	
    	
			br = new BufferedReader(new FileReader(validatedFile));
			 String contentLine1 = br.readLine();
			 while (contentLine1 != null) {
				 System.out.println(contentLine1);
			      String[] arrOfStr = contentLine1.split("\\|");
			      if(arrOfStr[2].equals("N"))
			      {
			    	  contentLine1 = br.readLine();
			    	  continue;
			      }
			      if(arrOfStr[0].equals("0") && arrOfStr[1].equals("INDI")) {
			    	  curI=new IndividualEntry(arrOfStr[3]);
			    	  hind.put(arrOfStr[3], curI);
			    	  contentLine1 = br.readLine();
			    	  inf=true;
			    	  faf=false;
			    	  continue;

			      }
			      if(arrOfStr[0].equals("0") && arrOfStr[1].equals("FAM")) {
			    	  curF=new FamilyEntry(arrOfStr[3]);
			    	  hfam.put(arrOfStr[3], curF);
			    	  contentLine1 = br.readLine();
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
			    	contentLine1 = br.readLine();
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
			    	  contentLine1 = br.readLine();
			    	  continue;
			      } 
			      contentLine1 = br.readLine();
			     
			   }

			  System.out.println();
			  System.out.println("Individual"); 
			 System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", "ID","Name","Gender","Birthday","Age","Alive","Death","Child","Spouse");
			 for (Iterator<Entry<String, IndividualEntry>> iterator = hind.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, IndividualEntry> mapElement = iterator.next();
				String key = mapElement.getKey(); 
  
				IndividualEntry value = mapElement.getValue(); 
				
				SimpleDateFormat formatter3=new SimpleDateFormat("dd MMM yyyy");
				Date birt=formatter3.parse(value.getBirthday());
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
				String birthdate=formatter.format(birt);
				String Death="NA";
				if(value.getDeath()!=null) {
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
				System.out.format("%-10s%-15s%-10s%-15s%-25s%-10s%-20s%-15s\n", "ID","Married","Divorced","Husband ID","Husband Name","Wife ID","Wife Name","Children");		
				for (Iterator<Entry<String, FamilyEntry>> iterator = hfam.entrySet().iterator(); iterator.hasNext();) {
					Entry<String, FamilyEntry> mapElement = iterator.next();
					String key = (String)mapElement.getKey(); 
		  
					FamilyEntry value = (FamilyEntry)mapElement.getValue();
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
			e.printStackTrace();
		}
	}

}
