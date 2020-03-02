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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GenerateOutput {
//====================================================== Add you user stories here ======================================================
		/**
			 * Author: Kunj Desai 
			 * ID:US02 
			 * Name: Birth before marriage 
			 * Description: Birth should occur before marriage of an individual 
			 * Date created: Feb 21,  202011:05:39 PM
			 * 
			 * @throws ParseException
		 */
		public static boolean us02_birth_b4_marriage() throws ParseException {
			
			boolean flag = true;
			
			for (Iterator<Entry<String, IndividualEntry>> iteratorInd = hind.entrySet().iterator(); iteratorInd
					.hasNext();) {
				Entry<String, IndividualEntry> indMapElement = iteratorInd.next();
				String keyInd = indMapElement.getKey();

				IndividualEntry indValue = indMapElement.getValue();
				Date birt = dateFormatGiven.parse(indValue.getBirthday());
				String spouseID = null;
				for(String id: indValue.getSpous())
				{
					spouseID= id.replaceAll("\\s", "");
				}
				
				if(!indValue.getSpous().isEmpty())
				{
					for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
							.hasNext();) {
						Entry<String, FamilyEntry> famMapElement = iteratorFam.next();						
						String keyFam = famMapElement.getKey();
						FamilyEntry valueFam = famMapElement.getValue();
						String marriageDate = null;
						
						if (valueFam.getMarried() != null) {
							Date marriageD = dateFormatGiven.parse(valueFam.getMarried());
							marriageDate = dateFormat.format(marriageD);
						}
						
						String birthDate = dateFormat.format(birt);
						if(keyFam.equals(spouseID) && (marriageDate.compareTo(birthDate) < 0 || marriageDate == null))
						{
							String failStr = "User Story 02: For "+keyInd+" birth date: "+birthDate+" occurs after marriage date: "+marriageDate;
							failures.add(failStr);
							flag = false;
							failuresFlag = true;
						}
					}
				}
			}
			if(flag)
				return true;
			else
				return false;
		}
		
		/**
			 * Author: Kunj Desai
			 * ID: US04
			 * Name: Marriage before divorce
			 * Description: Marriage should occur before divorce of spouses, and divorce can only occur after marriage
			 * Date created: Feb 24, 202012:14:12 AM
			 * @throws ParseException 
		 */
		public static boolean us04_marriage_b4_divorce() throws ParseException
		{
			boolean flag = true;
			
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
					.hasNext();) {
				Entry<String, FamilyEntry> mapElement = iteratorFam.next();
				FamilyEntry valueFam = mapElement.getValue();

				String married = "NA";
				String divorce = "NA";
				if (valueFam.getMarried() != null) {
					Date marriageDate = dateFormatGiven.parse(valueFam.getMarried());
					married = dateFormat.format(marriageDate);
				}				
				if (valueFam.getDivorced() != null) {
					Date divorceDate = dateFormatGiven.parse(valueFam.getDivorced());
					divorce = dateFormat.format(divorceDate);
				}
				
				if(!divorce.equals("NA") || divorce.compareTo(married) < 0 )
				{
					String failStr = "User Story 04: For "+valueFam.getH_id()+"and "+valueFam.getW_id()+ "marriage date: "+married+ " occurs after divorce date:"+ divorce;
					failures.add(failStr);
					flag = false;
					failuresFlag = true;
				}
			}
			
			if(flag)
				return true;
			else
				return false;			
		}

		
	/**
		 * Author: Vyom Shah
		 * ID: US22
		 * Name: Unique Ids
		 * Description: There should be no two same ids
		 * Date created: Feb 26, 202012:14:12 AM
	 * @throws ParseException 
	 */
		public static boolean us22_unique_ids() throws ParseException
		{
			boolean errorcode=true;
			Map<String, IndividualEntry> indMap=new HashMap<String, IndividualEntry>(hind);
			Map<String,FamilyEntry> famMap=new HashMap<String, FamilyEntry>(hfam);
			final ArrayList<IndividualEntry> dupInd = new ArrayList<IndividualEntry>();
			final ArrayList<FamilyEntry> dupFam = new ArrayList<FamilyEntry>();
			
			if(!dupInd.isEmpty()) {
				for(int i=0;i<dupInd.size();i++) {
					IndividualEntry ind1=dupInd.get(i);
					IndividualEntry ind2=indMap.get((dupInd).get(i).getId());
					String failStr="User story 22: Individual"+ind1.getId()+"-"+ind1.getName()+" has the same ID as " + ind2.getId()+" "+ind2.getName();
					failures.add(failStr);
					errorcode=false;
					failuresFlag=true;
					}
			}
			if(!dupInd.isEmpty()) {
				for(int i=0;i<dupFam.size();i++) {
					FamilyEntry fam1=dupFam.get(i);
					FamilyEntry fam2=famMap.get(dupFam.get(i).getId());
					String failStr="User story 22: Family"+fam1.getId()+" has the same ID as " + fam2.getId();
					failures.add(failStr);
					errorcode=false;
					failuresFlag=true;
				}
				if(errorcode)
					return true;
				else
					return false;
			}			
			return errorcode;
		}
			


		/**
		 * Author: Kunj Desai
			 * ID: US06
			 * Name: Divorce before death
			 * Description: Divorce can only occur before death of both spouses
			 * Date created: Feb 27, 20201:23:06 AM
			 * @throws ParseException 
		 */
		public static boolean us06_divorce_b4_death() throws ParseException
		{
			boolean flag = true;
			Set<String> set = new HashSet<String>(); 
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
					.hasNext();) {
				Entry<String, FamilyEntry> mapElement = iteratorFam.next();
				String famID = mapElement.getKey();
				FamilyEntry valueFam = mapElement.getValue();
				String husbandID = null, wifeID = null, divorce = "NA";
				if (valueFam.getDivorced() != null) {
					Date divorceDate = dateFormatGiven.parse(valueFam.getDivorced());
					divorce = dateFormat.format(divorceDate);
					husbandID = valueFam.getH_id().replaceAll("\\s", "");
					wifeID = valueFam.getW_id().replaceAll("\\s", "");
					
					for (Iterator<Entry<String, IndividualEntry>> iteratorInd = hind.entrySet().iterator(); iteratorInd
							.hasNext();) {
						Entry<String, IndividualEntry> mapElement1 = iteratorInd.next();
						String indId = mapElement1.getKey();
						IndividualEntry indValue = mapElement1.getValue();		
						String  death = "NA";
						if (indValue.getDeath() != null) {
							Date deat = dateFormatGiven.parse(indValue.getDeath());
							death = dateFormat.format(deat);
							
							if((indId.equals(husbandID)&& death.compareTo(divorce) < 0) || (indId.equals(wifeID) && death.compareTo(divorce) < 0))
							{
								if(!set.contains(indId))
								{
									String failStr = "User Story 06: For "+ indId  +" divorce date: "+divorce+ " occurs after death date: "+ death;
									failures.add(failStr);
									flag = false;
									failuresFlag = true;
									set.add(indId);
								}
							}
						}
					}				
				}				
			}
			
			if(flag)
				return true;
			else
				return false;
		}

//====================================================== End of user stories ======================================================

	/**
	 * Please update the GEDCOM file path at line number: 260
	 * Please update the .txt file path at line number: 262 and 304	
	 */
	private static HashMap<String, ArrayList<String>> tagsmap = new HashMap<>();
	private static HashMap<String, IndividualEntry> hind = new HashMap<>();
	private static HashMap<String, FamilyEntry> hfam = new HashMap<>();

	private static ArrayList<String> zero = new ArrayList<>(Arrays.asList("INDI", "FAM", "HEAD", "TRLR", "NOTE"));
	private static ArrayList<String> one = new ArrayList<>(
			Arrays.asList("NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS", "MARR", "HUSB", "WIFE", "CHIL", "DIV"));
	private static ArrayList<String> two = new ArrayList<>(Arrays.asList("DATE"));
	private static ArrayList<String> three = new ArrayList<>(Arrays.asList(""));
	private static ArrayList<String> four = new ArrayList<>(Arrays.asList(""));

	private static boolean inf = false, faf = false, birth = false, death = false, married = false, divorced = false, failuresFlag = false;

	private static SimpleDateFormat dateFormatGiven = new SimpleDateFormat("dd MMM yyyy");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static ArrayList<String> failures = new ArrayList<String>();
	public static void main(String[] args) throws IOException, ParseException {

		try {

			tagsmap.put("0", zero);
			tagsmap.put("1", one);
			tagsmap.put("2", two);
			tagsmap.put("3", three);
			tagsmap.put("4", four);
			String intitalInputFile = "/Users/kunj/Downloads/test.ged";
			File outputFile = new File(intitalInputFile);
			FileWriter fw = new FileWriter("/Users/kunj/Downloads/test.txt");

			BufferedReader br = new BufferedReader(new FileReader(outputFile));
			String contentLine = br.readLine();
			while (contentLine != null) {
				// System.out.println("-->"+contentLine);
				StringBuilder str = new StringBuilder();
				String[] arrOfStr = contentLine.split(" ");
				if (arrOfStr.length >= 3) {
					if (arrOfStr[2].equals("INDI") || arrOfStr[2].equals("FAM")) {
						str.append(arrOfStr[0]);
						str.append("|");
						str.append(arrOfStr[2] + "|");
						if (tagsmap.get(arrOfStr[0]).contains(arrOfStr[2])) {
							str.append("Y|");
						} else {
							str.append("N|");
						}
						str.append(arrOfStr[1]);
						fw.write(str.toString() + "\n");
						// System.out.println("<--"+str.toString());
						contentLine = br.readLine();
						continue;
					}
				}
				str.append(arrOfStr[0]);
				str.append("|");
				str.append(arrOfStr[1] + "|");
				if (tagsmap.get(arrOfStr[0]).contains(arrOfStr[1])) {
					str.append("Y|");
				} else {
					str.append("N|");
				}
				for (int i = 2; i < arrOfStr.length; i++) {
					str.append(arrOfStr[i] + " ");
				}
				fw.write(str.toString() + "\n");
				// System.out.println("<--"+str.toString());
				contentLine = br.readLine();
			}
			fw.close();

			String textInputFile = "/Users/kunj/Downloads/test.txt";
			File validatedFile = new File(textInputFile);

			IndividualEntry curI = null;
			FamilyEntry curF = null;

			br = new BufferedReader(new FileReader(validatedFile));
			String contentLine1 = br.readLine();
			while (contentLine1 != null) {
				System.out.println(contentLine1);
				String[] arrOfStr = contentLine1.split("\\|");
				if (arrOfStr[2].equals("N")) {
					contentLine1 = br.readLine();
					continue;
				}
				if (arrOfStr[0].equals("0") && arrOfStr[1].equals("INDI")) {
					curI = new IndividualEntry(arrOfStr[3]);
					hind.put(arrOfStr[3], curI);
					contentLine1 = br.readLine();
					inf = true;
					faf = false;
					continue;

				}
				if (arrOfStr[0].equals("0") && arrOfStr[1].equals("FAM")) {
					curF = new FamilyEntry(arrOfStr[3]);
					hfam.put(arrOfStr[3], curF);
					contentLine1 = br.readLine();
					faf = true;
					inf = false;
					continue;
				}
				if (inf) {

					switch (arrOfStr[1]) {
					case "NAME":
						curI.setName(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "SEX":
						curI.setGender(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "BIRT":
						birth = true;
						death = false;
						// contentLine = br.readLine();
						break;
					case "DEAT":
						birth = false;
						death = true;
						// contentLine = br.readLine();
						break;
					case "FAMC":
						curI.getChild().add(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "FAMS":
						curI.getSpous().add(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "DATE":
						if (birth) {
							curI.setBirthday(arrOfStr[3]);
							// contentLine = br.readLine();
							birth = false;
							break;
						} else if (death) {
							curI.setDeath(arrOfStr[3]);
							// contentLine = br.readLine();
							death = false;
							break;
						}
						break;
					default:
						// contentLine = br.readLine();
						break;
					}
					contentLine1 = br.readLine();
					continue;
				} else if (faf) {
					switch (arrOfStr[1]) {
					case "MARR":
						married = true;
						// contentLine = br.readLine();
						break;
					case "HUSB":
						curF.setH_id(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "WIFE":
						curF.setW_id(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "CHIL":
						curF.getChild().add(arrOfStr[3]);
						// contentLine = br.readLine();
						break;
					case "DIV":
						divorced = true;
						// contentLine = br.readLine();
						break;
					case "DATE":
						if (married) {
							curF.setMarried(arrOfStr[3]);
							// contentLine = br.readLine();
							married = false;
							break;
						} else if (divorced) {
							curF.setDivorced(arrOfStr[3]);
							// contentLine = br.readLine();
							divorced = false;
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
			System.out.println();
			
			System.out.println("Individual");
			System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", "ID", "Name", "Gender", "Birthday",
					"Age", "Alive", "Death", "Child", "Spouse");
			for (Iterator<Entry<String, IndividualEntry>> iteratorInd = hind.entrySet().iterator(); iteratorInd
					.hasNext();) {
				Entry<String, IndividualEntry> mapElement = iteratorInd.next();
				String indId = mapElement.getKey();

				IndividualEntry indValue = mapElement.getValue();
				Date birt = dateFormatGiven.parse(indValue.getBirthday());

				String birthdate = dateFormat.format(birt);
				String Death = "NA";
				if (indValue.getDeath() != null) {
					Date deat = dateFormatGiven.parse(indValue.getDeath());
					Death = dateFormat.format(deat);
				}

				if (indValue.getChild().isEmpty() && indValue.getSpous().isEmpty()) {
					System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", indId, indValue.getName(),
							indValue.getGender(), birthdate, indValue.getAge(), indValue.getAlive(), Death, "NA", "NA");
				} else if (indValue.getChild().isEmpty() && !indValue.getSpous().isEmpty()) {
					System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", indId, indValue.getName(),
							indValue.getGender(), birthdate, indValue.getAge(), indValue.getAlive(), Death, "NA",
							indValue.getSpous());
				} else if (!indValue.getChild().isEmpty() && indValue.getSpous().isEmpty()) {
					System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", indId, indValue.getName(),
							indValue.getGender(), birthdate, indValue.getAge(), indValue.getAlive(), Death,
							indValue.getChild(), "NA");
				} else {
					System.out.format("%-10s%-20s%-10s%-15s%-10s%-15s%-15s%-20s%-20s\n", indId, indValue.getName(),
							indValue.getGender(), birthdate, indValue.getAge(), indValue.getAlive(), Death,
							indValue.getChild(), indValue.getSpous());
				}
			}

			System.out.println();
			System.out.println("Family");
			System.out.format("%-10s%-15s%-15s%-15s%-25s%-10s%-20s%-15s\n", "ID", "Married", "Divorced", "Husband ID",
					"Husband Name", "Wife ID", "Wife Name", "Children");
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
					.hasNext();) {
				Entry<String, FamilyEntry> mapElement = iteratorFam.next();
				String keyFam = mapElement.getKey();

				FamilyEntry valueFam = mapElement.getValue();

				String married = "NA";
				if (valueFam.getMarried() != null) {
					Date marriageDate = dateFormatGiven.parse(valueFam.getMarried());
					married = dateFormat.format(marriageDate);
				}
				String divorce = "NA";
				if (valueFam.getDivorced() != null) {
					Date divorceDate = dateFormatGiven.parse(valueFam.getDivorced());
					divorce = dateFormat.format(divorceDate);
				}
				if (valueFam.getChild().isEmpty()) {
					System.out.format("%-10s%-15s%-15s%-15s%-25s%-10s%-20s%-15s\n", keyFam, married, divorce,
							valueFam.getH_id(), hind.get(valueFam.getH_id().trim()).getName(), valueFam.getW_id(),
							hind.get(valueFam.getW_id().trim()).getName(), "NA");
				} else {
					System.out.format("%-10s%-15s%-15s%-15s%-25s%-10s%-20s%-15s\n", keyFam, married, divorce,
							valueFam.getH_id(), hind.get(valueFam.getH_id().trim()).getName(), valueFam.getW_id(),
							hind.get(valueFam.getW_id().trim()).getName(), valueFam.getChild());
				}
			}

			System.out.println("");
			
			//====================================================== Check all user stories here ======================================================			
			us02_birth_b4_marriage();
			us04_marriage_b4_divorce();
			us22_unique_ids();
			us06_divorce_b4_death();			
			if(failuresFlag)
			 {
				 System.out.println("There are following errors: ");
				 for(String failString: failures)
				 {
					 System.out.println(failString);
				 }			 
			 }
			 else
			 {
				 System.out.println("User story number 02 passed successfully!");
				 System.out.println("User story number 04 passed successfully!");
				 System.out.println("user story number 22 passed successfully!");
				 System.out.println("User story number 06 passed successfully!");
				 System.out.println("User story number 27 passed successfully!");
			 }
			  
			//======================================================   End of all user stories   ======================================================
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
