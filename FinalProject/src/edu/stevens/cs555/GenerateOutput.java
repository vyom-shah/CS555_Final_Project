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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
						String marriageDate = null, birthDate = null;
						
						if (valueFam.getMarried() != null) {
							Date marriageD = dateFormatGiven.parse(valueFam.getMarried());
							marriageDate = dateFormat.format(marriageD);
						}
						if(indValue.getBirthday() != null)
						{
							Date birt = dateFormatGiven.parse(indValue.getBirthday());
							birthDate = dateFormat.format(birt);
						}
						
						if(keyFam.equals(spouseID) && (marriageDate == null || birthDate == null || marriageDate.compareTo(birthDate) < 0 ))
						{
							String failStr = "ERROR: INDIVIDUAL: US02: "+keyInd+": Birth date "+birthDate+" occurs after marriage date "+marriageDate;
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
				String keyFam = mapElement.getKey();
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
					String failStr = "ERROR: FAMILY: US04: "+ keyFam + ": Marriage date "+married+ " occurs after divorce date "+ divorce;
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
					String failStr="ERROR: INDIVIDUAL: US22: "+ind1.getId()+": "+ind1.getName()+": has the same ID as " + ind2.getId()+": "+ind2.getName();
					failures.add(failStr);
					errorcode=false;
					failuresFlag=true;
					}
			}
			if(!dupInd.isEmpty()) {
				for(int i=0;i<dupFam.size();i++) {
					FamilyEntry fam1=dupFam.get(i);
					FamilyEntry fam2=famMap.get(dupFam.get(i).getId());
					String failStr="ERROR: FAMILY: US22:"+fam1.getId()+": has the same ID as " + fam2.getId();
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
									String failStr = "ERROR: INDIVIDUAL: US06: "+ indId  +": Divorce date "+divorce+ " occurs after death date "+ death;
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

		/**
		 * 
			 * Author: Kunj Desai
			 * ID: US10
			 * Name: Marriage after 14
			 * Description: Marriage should be at least 14 years after birth of both spouses (parents must be at least 14 years old)
			 * Date created: Mar 2, 20201:48:55 AM
		 * @throws ParseException 
		 */
		public static boolean us10_marriage_after_14() throws ParseException
		{
			boolean flag = true;
			
			for (Iterator<Entry<String, IndividualEntry>> iteratorInd = hind.entrySet().iterator(); iteratorInd
					.hasNext();) {
				Entry<String, IndividualEntry> indMapElement = iteratorInd.next();
				String keyInd = indMapElement.getKey();

				IndividualEntry indValue = indMapElement.getValue();
				Date birt = null;
				if(indValue.getBirthday() != null)
				{
					birt = dateFormatGiven.parse(indValue.getBirthday());
				}
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
						int years = 0;
						if (valueFam.getMarried() != null && indValue.getBirthday() != null) {
							Date marriageD = dateFormatGiven.parse(valueFam.getMarried());
							long diffInMillies = Math.abs(marriageD.getTime() - birt.getTime());
							long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
							years=(int)diff/365;
						}
						
						if(keyFam.equals(spouseID) && years < 14)
						{
							String failStr = "ERROR: INDIVIDUAL: US10: "+keyInd+": Marrige occured before the age of 14.";
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
		 * Author: Dhruval Thakkar
			 * ID: US03
			 * Name: Birth before Death
			 * Description: Birth should be before death of an individual if individual is dead. 
			 * Date created: Feb 24, 202012:14:12 AM
			 * @throws ParseException 
		 */
		public static boolean us03_birth_before_death() throws ParseException
		{
			boolean flag = true;
			
			for (Iterator<Entry<String, IndividualEntry>> iteratorFam = hind.entrySet().iterator(); iteratorFam
					.hasNext();) {
				Entry<String, IndividualEntry> mapElement = iteratorFam.next();
				IndividualEntry valueFam = mapElement.getValue();

				String birth = "NA";
				String death = "NA";
				if (valueFam.getBirthday() != null) {
					Date birthDate = dateFormatGiven.parse(valueFam.getBirthday());
					birth = dateFormat.format(birthDate);
				}				
				if (valueFam.getDeath() != null) {
					Date deathDate = dateFormatGiven.parse(valueFam.getDeath());
					death = dateFormat.format(deathDate);
				}
				
				if(!death.equals("NA") || death.compareTo(birth) < 0 )
				{
					String failStr = "ERROR: INDIVIDUAL: US03: "+valueFam.getId()+ ": Birth date "+birth+ " occurs after death date "+ death;
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
		 * Author: Dhruval Thakkar
			 * ID: US05
			 * Name: Marriage before death
			 * Description: Marriage should occur before death.
			 * Date created: Mar 02, 202012:14:12 AM
			 * @throws ParseException 
		 */
		public static boolean us05_marriage_before_death() throws ParseException
		{
			boolean flag = true;
			
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam.hasNext();) {
				Entry<String, FamilyEntry> mapElement = iteratorFam.next();
				FamilyEntry valueFam = mapElement.getValue();

				for (Iterator<Entry<String, IndividualEntry>> iteratorInd = hind.entrySet().iterator(); iteratorInd.hasNext();) {
					Entry<String, IndividualEntry> mapElement1 = iteratorInd.next();
					IndividualEntry valueInd = mapElement1.getValue();

					String husbandID = valueFam.getH_id().replaceAll("\\s", "");
					String wifeID = valueFam.getW_id().replaceAll("\\s", "");
					
					String married = "NA";
					String death = "NA";

					if (valueFam.getMarried() != null) {
						Date marriageDate = dateFormatGiven.parse(valueFam.getMarried());
						married = dateFormat.format(marriageDate);
					}	
					if (valueInd.getDeath()!= null) {
						Date deathDate = dateFormatGiven.parse(valueInd.getDeath());
						death = dateFormat.format(deathDate);
					}
//					if (valueFam.getH_id().equals(valueInd.getId()) && valueInd.getDeath() != null) {
//						Date deathDate = dateFormatGiven.parse(valueInd.getDeath());
//						death = dateFormat.format(deathDate);
//					}
//					if	(valueFam.getW_id().equals(valueInd.getId()) && valueInd.getDeath() != null) {
//						Date deathDate = dateFormatGiven.parse(valueInd.getDeath());
//						death = dateFormat.format(deathDate);
//					}
					if((husbandID.equals(valueInd.getId()) || wifeID.equals(valueInd.getId())) && (married.compareTo(death) < 0 || married == null) && (!death.equals("NA")))
					{
						String failStr = "ERROR: INDIVIDUAL: US05: "+valueInd.getId() + ": Marriage date "+married+ " occurs after death date "+ death;	
						failures.add(failStr);
						flag = false;
						failuresFlag = true;
					}
				}
			}
			if(flag)
				return true;
			else
				return false;

		}

		/**
	 * Author: Yash Navadiya ID: US07 Name: Less than 150 years old Description:
	 * Death should be less than 150 years after birth for dead people, and current
	 * date should be less than 150 years after birth for all living people Date
	 * created: Feb 27, 20205:05:02 PM
	 * 
	 * @throws ParseException
	 */

	public static boolean us07_less_than_150yrs() throws ParseException {
		boolean flag = true;
		Map<String, IndividualEntry> map = new HashMap<String, IndividualEntry>(hind);
		Iterator<Map.Entry<String, IndividualEntry>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, IndividualEntry> entry = entries.next();
			IndividualEntry indi = entry.getValue();
			
			int birthyear = 0;
			if(indi.getBirthday() != null)
			{
				Date bdate = dateFormatGiven.parse(indi.getBirthday());
				String birth = dateFormat.format(bdate);
				birthyear = Integer.parseInt(birth.split("-")[0]);
			}
			
			

			// Check if the person is alive or not . If it is , compare birth date and
			// todays date.
			// If not, compare the birth date with death date of that person.

			if (indi.getDeath() == null) {
				// use calendar to get the date of today
				Calendar ca = Calendar.getInstance();
				int year = ca.get(Calendar.YEAR);
				int diff = year - birthyear;
				if (diff > 150) {
					String failStr =  "ERROR: INDIVIDUAL: US07: "+ indi.getId() + " - " + indi.getName()
							+ " is greater than 150 years old.DOB: " + indi.getBirthday();
					failures.add(failStr);
					flag = false;
					failuresFlag = true;
				}
			} else {
				Date ddate = dateFormatGiven.parse(indi.getDeath());
				String deat = dateFormat.format(ddate);

				int deathyear = Integer.parseInt(deat.split("-")[0]);
				int diff = deathyear - birthyear;
				if (diff > 150) {
					String failStr = "ERROR: INDIVIDUAL: US07: " + indi.getId() + ": " + indi.getName()
							+ " The difference between their birthdate and the death is greater than 150 years. DOB: "
							+ indi.getBirthday() + " DOD: " + indi.getDeath();
					failures.add(failStr);
					flag = false;
					failuresFlag = true;
				}
			}
		}
		if (flag)
			return true;
		else
			return false;
	}

	/**
	 * Author: Yash Navadiya ID: US08 Name: Birth before marriage of parents
	 * Description:Children should be born after marriage of parents (and not more
	 * than 9 months after their divorce) created: Feb 27, 20209:25:02 PM
	 * 
	 * @throws ParseException
	 */

	public static boolean us08_birth_before_marriage() throws ParseException {
		boolean flag = true;

		Date divorceDate = null;
		Date birthDate = null;
		Map<String, IndividualEntry> indMap = new HashMap<String, IndividualEntry>(hind);
		Map<String, FamilyEntry> famMap = new HashMap<String, FamilyEntry>(hfam);

		Iterator<Map.Entry<String, FamilyEntry>> famEntries = famMap.entrySet().iterator();
		while (famEntries.hasNext()) {
			Map.Entry<String, FamilyEntry> famEntry = famEntries.next();
			FamilyEntry fam = famEntry.getValue();

			Date marriageDate = dateFormatGiven.parse(fam.getMarried());

			if (fam.getChild() != null) {
				for (Iterator<String> it = fam.getChild().iterator(); it.hasNext();) {
					IndividualEntry indi = hind.get(it.next().trim());

					if(indi.getBirthday() != null)
						birthDate = dateFormatGiven.parse(indi.getBirthday());
					if (birthDate.before(marriageDate)) {
						String failStr = "ERROR: FAMILY: US08: " + fam.getId() + ": Individual: " + indi.getId() + ": "
								+ indi.getName() + " Has been born before parents' marriage DOB: " + indi.getBirthday()
								+ " Parents Marriage Date: " + fam.getMarried();
						failures.add(failStr);
						flag = false;
						failuresFlag = true;
					}
					if (fam.getDivorced() != null) {
						divorceDate = dateFormatGiven.parse(fam.getDivorced());
						if (birthDate.after(divorceDate)) {
							String failStr =  "ERROR: FAMILY: US08: "+"Family ID: " + fam.getId() + " Individual: " + indi.getId() + ": "
									+ indi.getName() + " Has been born after parents' divorce";
									
							failures.add(failStr);
							flag = false;
							failuresFlag = true;
						}
					}
				}
			}
		}
		if (flag)
			return true;
		else
			return false;

	}
	
	public static boolean us18_siblings_should_not_marry () throws ParseException
	{
		boolean flag = true;
		for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
				.hasNext();) {
			Entry<String, FamilyEntry> famMapElement = iteratorFam.next();
			String keyFam = famMapElement.getKey().trim();
			FamilyEntry famValue = famMapElement.getValue();
			String H_id=famValue.getH_id().trim();
			String W_id=famValue.getW_id().trim();
			if(hind.get(H_id).getChild().size()!=0 && hind.get(W_id).getChild().size()!=0) {
				if(hind.get(H_id).getChild().equals(hind.get(W_id).getChild())) {
					flag=false;
					String failStr = "ERROR: FAMILY: US18: "+hind.get(H_id).getName().trim()+" should not marry his sibling "+hind.get(W_id).getName().trim();
					failures.add(failStr);
					failuresFlag = true;
				}
			}
			
//			if(indValue.getGender().equals("M") && !indValue.spous.isEmpty()) {
//				String famID;
//				for (Iterator<String> it = indValue.spous.iterator(); it.hasNext(); ) {
//					famID=it.next();
//				}
//				
			}
		
		
		return flag;
	}
	
	/**
	 * Author: Nihir Patel
		 * ID: US16
		 * Name: Male Last name
		 * Description: All male members of a family should have the same last name
		 * Date created: Feb 27, 20201:23:06 AM
		 * @throws ParseException 
	 */
	public static boolean us16_Male_last_name () throws ParseException
	{
		boolean flag = true;
		for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
				.hasNext();) {
			Entry<String, FamilyEntry> famMapElement = iteratorFam.next();
			String keyFam = famMapElement.getKey().trim();
			FamilyEntry famValue = famMapElement.getValue();
			String famlastname=hind.get(famValue.getH_id().trim()).getName().split("/")[1];
			for (Iterator<String> it = famValue.getChild().iterator(); it.hasNext(); ) {
				IndividualEntry indValue=  hind.get(it.next().trim());
				String indlastname=indValue.getName().split("/")[1];
				
				if(indValue.getGender().trim().equals("M") && !indlastname.equals(famlastname)) {
					flag=false;
					String failStr =  "ERROR: FAMILY: US16: "+famValue.getId()+": "+indValue.getName().trim()+"  and "+hind.get(famValue.getH_id().trim()).getName()+" does not have same last name!";
					failures.add(failStr);
					failuresFlag = true;
				}
			}
			
		}
		return flag;
		
	}

					
		/**
		 * Author: Dhruval Thakkar
			 * ID: US15
			 * Name: Fewer than 15 siblings
			 * Description: There should be fewer than 15 siblings in a family 
			 * Date created: March 04, 2020 05:10:52 PM
			 * @throws ParseException 
		 */
		public static boolean us15_fewer_than_15_siblings() throws ParseException
		{
			boolean flag = true;
			
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
					.hasNext();) {
				Entry<String, FamilyEntry> mapElement = iteratorFam.next();
				FamilyEntry valueFam = mapElement.getValue();

				int sibling_Count = 0;
				
				if (valueFam.getChild() != null) {
					for (String var : valueFam.getChild()) {
						sibling_Count += 1;
					}
				}				
				
				if(sibling_Count >= 15)
				{
					String failStr = "ERROR: FAMILY: US15: "+valueFam.getId()+ ": has "+sibling_Count+ " number of siblings in the family";
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
		 * Author: Dhruval Thakkar
			 * ID: US20
			 * Name: Aunts and uncles
			 * Description: Aunts and uncles should not marry their nieces or nephews 
			 * Date created: March 07, 2020 12:00:04 PM
			 * @throws ParseException 
		 */
		public static boolean us20_aunts_and_uncles() throws ParseException
		{
			boolean flag = true;
					
			for (Iterator<Entry<String, IndividualEntry>> iteratorInd = hind.entrySet().iterator(); iteratorInd
				.hasNext();) {
				Entry<String, IndividualEntry> mapElement = iteratorInd.next();
				IndividualEntry valueInd = mapElement.getValue(); 
				
				for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
				.hasNext();) {
					Entry<String, FamilyEntry> mapElement1 = iteratorFam.next();
					FamilyEntry valueFam = mapElement1.getValue(); 

					for (Iterator<Entry<String, FamilyEntry>> iteratorFam1 = hfam.entrySet().iterator(); iteratorFam1
					.hasNext();) {
						Entry<String, FamilyEntry> mapElement2 = iteratorFam1.next();
						FamilyEntry valueFam1 = mapElement2.getValue();	
						
						String child1 = null;
						
						for (String child : valueInd.getChild()) {
							child1 = child.replaceAll("\\s", "");;
						}
							String fam_id = valueFam.getId();
							if(valueInd.getSpous() != null && fam_id.equals(child1)){
								for (String vari : valueInd.getSpous()) {
									String var = vari.replaceAll("\\s", "");
					
										if(valueFam.child.contains(valueFam1.getH_id()) && valueFam1.getId().equals(var)){
											String failStr = "ERROR: FAMILY: US20: "+valueInd.getId()+ ": can not marry "+ valueInd.getSpous();
											failures.add(failStr);
											flag = false;
											failuresFlag = true;
										}

										else if(valueFam.child.contains(valueFam1.getW_id()) && valueFam1.getId().equals(var) ){
											String failStr = "ERROR: FAMILY: US20: "+valueInd.getId()+ ": can not marry "+ valueInd.getSpous();
											failures.add(failStr);
											flag = false;
											failuresFlag = true;
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

		/**
		 * Author: Vyom Shah
			 * ID: US35
			 * Name: List Recent Births
			 * Description: List all people in a GEDCOM file who were born in the last 30 days 
			 * Date created: March 07, 2020 12:34:04 AM
			 * @throws ParseException 
		 */
		public static boolean us_35_recentbirth() throws ParseException
		{
			boolean flag=true;
			
			Map<String, IndividualEntry> map=new HashMap<String, IndividualEntry>(hind);
			Iterator<Map.Entry<String, IndividualEntry>> entries=map.entrySet().iterator();
//			final SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy");
			Date nowTime=new Date(System.currentTimeMillis());
			Calendar cal2=Calendar.getInstance();
			int diffDay=0;
			int diffMonth=0;
			int diffYear=0;
			int birthyear = 0;
			int birthmonth=0;
			int birthdate=0;
			while(entries.hasNext()) {
				Map.Entry<String, IndividualEntry> entry=entries.next();
				IndividualEntry indi=entry.getValue();
				if(indi.getBirthday() != null)
				{
					Date bdate = dateFormatGiven.parse(indi.getBirthday());
					String birth = dateFormat.format(bdate);
					birthyear = Integer.parseInt(birth.split("-")[0]);
					birthmonth = Integer.parseInt(birth.split("-")[1]);
					birthdate=Integer.parseInt(birth.split("-")[2]);
					
				}	
					//dob = dateFormatGiven.parse(indi.getBirthday());
					//Date ddate = dateFormatGiven.parse(indi.getDeath());
					//String deat = dateFormat.format(ddate);
					//dob= sdf.parse(indi.getBirthday());
					//System.out.print();
					//cal1.setTime(birthyear);
					cal2.setTime(nowTime);
					diffYear=birthyear-cal2.get(Calendar.YEAR);
					if(diffYear==0) {
						diffDay=birthdate-cal2.get(Calendar.DAY_OF_MONTH);
						diffMonth=birthmonth-(cal2.get(Calendar.MONTH)+1);
						
					}
				
				if(diffYear==0 && diffMonth <= 1 && diffDay<30) {
					String failStr = "ERROR: INDIVIDUAL: US35: "+indi.getId()+" - "+ indi.getName()+ "was born in last 30 days from today";
					failures.add(failStr);
					flag = false;
					failuresFlag = true;
				}
			}
			
			
			return flag;
		}

		/**
		 * Author: Vyom Shah
			 * ID: US38
			 * Name: List Upcoming Birthdays
			 * Description: List all living people in a GEDCOM file whose birthdays occur in the next 30 days 
			 * Date created: March 07, 2020 02:22:34 AM
			 * @throws ParseException 
		 */
		public static boolean us_38_upcomingbirthdays() throws ParseException
		{
			boolean flag=true;
			Map<String,IndividualEntry> map=new HashMap<String, IndividualEntry>(hind);
			Iterator<Map.Entry<String, IndividualEntry>> entries=map.entrySet().iterator();
			Date nowTime=new Date(System.currentTimeMillis());
			
			Calendar cal2=Calendar.getInstance();
			int diffDay=0;
			int diffMonth=0;
			while(entries.hasNext()) {
				Map.Entry<String, IndividualEntry> entry=entries.next();
				IndividualEntry indi=entry.getValue();
				int birthmonth=0;
				int birthdate=0;
				if(indi.getBirthday() != null)
				{	
					//System.out.println(indi.getBirthday());
					
					Date bdate = dateFormatGiven.parse(indi.getBirthday());
					String birth = dateFormat.format(bdate);
					birthmonth = Integer.parseInt(birth.split("-")[1]);
					birthdate=Integer.parseInt(birth.split("-")[2]);
					
				}

				cal2.setTime(nowTime);
				diffDay=birthdate-cal2.get(Calendar.DAY_OF_MONTH);
				diffMonth=birthmonth-(cal2.get(Calendar.MONTH)+1);
				if(diffMonth==0&&diffDay<30) {
					String failStr = "ERROR: INDIVIDUAL: US38: "+indi.getId()+" - "+ indi.getName()+ "has a birthday less than 30 days from today";
					failures.add(failStr);
					flag = false;
					failuresFlag = true;
				}
			}
			return flag;
		}	

		
		/**
		 * 
			 * Author: Kunj Desai
			 * ID: US11
			 * Name: No bigamy
			 * Description: Marriage should not occur during marriage to another spouse
			 * Date created: Mar 11, 20207:54:08 PM
		 * @throws ParseException 
		 */
		public static boolean us11_no_bigamy() throws ParseException
		{
			boolean flag = true;
			HashSet<String> familyWithConflicts = new HashSet<String>();
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam
					.hasNext();) {
				
				String divorced = null, married = null;
				Entry<String, FamilyEntry> mapElement = iteratorFam.next();
				String keyFam = mapElement.getKey();
				FamilyEntry valueFam = mapElement.getValue();
				
				
				if (valueFam.getMarried() != null) {
					Date marriageDate = dateFormatGiven.parse(valueFam.getMarried());
					married = dateFormat.format(marriageDate);
					
					if (valueFam.getDivorced() != null) {
						Date divorceDate = dateFormatGiven.parse(valueFam.getDivorced());
						divorced = dateFormat.format(divorceDate);
					}
				}	
				
				for (Iterator<Entry<String, FamilyEntry>> iteratorFam1 = hfam.entrySet().iterator(); iteratorFam1
						.hasNext();) {
					
					String married1 = null;
					Entry<String, FamilyEntry> mapElement1 = iteratorFam1.next();
					String keyFam1 = mapElement1.getKey();
					FamilyEntry valueFam1 = mapElement1.getValue();
					
					if(!keyFam.equals(keyFam1))
					{
						if (valueFam1.getMarried() != null) {
							Date marriageDate1 = dateFormatGiven.parse(valueFam1.getMarried());
							married1 = dateFormat.format(marriageDate1);
							
						}	
						if(valueFam.getH_id().equals(valueFam1.getH_id()) || valueFam.getW_id().equals(valueFam1.getW_id()))
						{
							String failStr = null;
							if(divorced == null && married1 != null)
							{
								failStr = "ERROR: FAMILY: US11: "+ keyFam+": has conflicting marriage dates with "+keyFam1;
								failures.add(failStr);
								flag = false;
								failuresFlag = true;
							}
							else if(divorced != null && married1.compareTo(divorced) < 0)
							{
								failStr = "ERROR: FAMILY: US11: "+ keyFam+": has conflicting marriage dates with "+keyFam1;
								failures.add(failStr);
								flag = false;
								failuresFlag = true;
							}
						}
					}
					familyWithConflicts.add(keyFam);
					familyWithConflicts.add(keyFam1);
					
				}	
			
			}

			if(flag)
				return true;
			else
				return false;
		}

		/**
		 * 
			 * Author: Nihir Patel
			 * ID: US13
			 * Name: Sibling Spacing
			 * Description: Birth dates of siblings should be more than 8 months apart or less than 2 days apart (twins may be born one day apart, e.g. 11:59 PM and 12:02 AM the following calendar day)
			 * Date created: Mar 11, 20207:54:08 PM
		 * @throws ParseException 
		 */
		public static boolean us13_sibling_spacing() throws ParseException
		{
			boolean flag = true;
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam.hasNext();) {
				Entry<String, FamilyEntry> famMapElement = iteratorFam.next();
				FamilyEntry famValue = famMapElement.getValue();
				if(famValue.getChild().size()>=2) {
					for (Iterator<String> it = famValue.getChild().iterator(); it.hasNext(); ) {
						String child1=it.next().trim();
						for (Iterator<String> it1 = famValue.getChild().iterator(); it1.hasNext(); ) {
							String child2=it1.next().trim();
							if(child1.equals(child2)) {
								continue;
							}
							else {
								if(hind.get(child1).getBirthday()==null || hind.get(child2).getBirthday()==null) {
									continue;
								}
								Date bchild1=dateFormatGiven.parse(hind.get(child1).getBirthday());
								Date bchild2=dateFormatGiven.parse(hind.get(child2).getBirthday());
								long diffInMillies = Math.abs(bchild2.getTime() - bchild1.getTime());
							    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
							    if(diff>1 && diff<(30.42*8)) {
							    	flag=false;
							    	String failStr =  "ERROR: FAMILY: US13: Birthdays of "+hind.get(child1).getName()+"  and "+hind.get(child2).getName()+" are more then 2 days and less then 8 months";
									failures.add(failStr);
									failuresFlag = true;
							    }
							}
						}
					}
				}
				
			}
			return flag;
		}
		/**
		 * 
			 * Author: Nihir Patel
			 * ID: US14
			 * Name: Multiple births
			 * Description: No more than five siblings should be born at the same time
			 * Date created: Mar 11, 20207:54:08 PM
		 * @throws ParseException 
		 */
		public static boolean us14_multiple_births() throws ParseException
		{
			boolean flag = true;
			for (Iterator<Entry<String, FamilyEntry>> iteratorFam = hfam.entrySet().iterator(); iteratorFam.hasNext();) {
				Entry<String, FamilyEntry> famMapElement = iteratorFam.next();
				FamilyEntry famValue = famMapElement.getValue();
				if(famValue.getChild().size()>=6) {
					
				outer:	for (Iterator<String> it = famValue.getChild().iterator(); it.hasNext(); ) {
						int count=0;
						String child1=it.next().trim();
						for (Iterator<String> it1 = famValue.getChild().iterator(); it1.hasNext(); ) {
							String child2=it1.next().trim();
							if(child1.equals(child2)) {
								continue;
							}
							else {
								if(hind.get(child1).getBirthday()==null || hind.get(child2).getBirthday()==null) {
									continue;
								}
								Date bchild1=dateFormatGiven.parse(hind.get(child1).getBirthday());
								Date bchild2=dateFormatGiven.parse(hind.get(child2).getBirthday());
								long diffInMillies = Math.abs(bchild2.getTime() - bchild1.getTime());
							    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
							    if(diff<2) {
							    	count+=1;
							    	if(count>5) {
							    		flag=false;
							    		String failStr =  "ERROR: FAMILY: US14: More than five children were born at same time in family: "+famMapElement.getKey();
							    		failures.add(failStr);
									failuresFlag = true;
									break outer;
							    	}						    	
							    }
							}
						}
					}
				}
				
			}
			return flag;
		}
		
/**
	 * 
	 * Author: Yash Navadiya ID: US17 Name: No marriage to children Description:
	 * Parent should not marry either of their children Date created: Mar 18, 2020
	 * 7:54:08 PM
	 * 
	 * @throws ParseException
	 */

	public static boolean us17_no_marriage_to_children() throws ParseException {
		boolean flag = true;
		Map<String, FamilyEntry> fam_map = new HashMap<String, FamilyEntry>(hfam);
		Map<String, IndividualEntry> map = new HashMap<String, IndividualEntry>(hind);
		Iterator<Map.Entry<String, IndividualEntry>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, IndividualEntry> entry = entries.next();
			IndividualEntry indi = entry.getValue();

			Set<String> parents = new HashSet<String>();
			Set<String> c_set = indi.getChild();
			for (String id : c_set) {
				id = id.trim();
				FamilyEntry fam_object = hfam.get(id);
				parents.add(fam_object.getH_id());
				parents.add(fam_object.getW_id());
			}

			Set<String> family_set = indi.getSpous();

			for (String id : family_set) {
				id = id.trim();
				FamilyEntry fam_object = hfam.get(id);
				String spouse_id = "";
				if (indi.getGender().equals("M")) {
					spouse_id = fam_object.getW_id();
				} else {
					spouse_id = fam_object.getH_id();
				}
				if (parents.contains(spouse_id)) {
					flag = false;
					String failStr = "ERROR: FAMILY : " + id + ", parent can not marry their child";
					failures.add(failStr);
					failuresFlag = true;
					continue;
					// throw error
				}
			}
		}
		return flag;
	}
		
//====================================================== End of user stories ======================================================
		public static Object getAge() {
			return null;
		}
//====================================================== End of user stories ======================================================

	/**
	 * Please update the GEDCOM file path at line number: 310
	 * Please update the .txt file path at line number: 312 and 354	
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
			String intitalInputFile = System.getProperty("user.dir")+ "/GEDCOM/sprint-1.ged";
			File outputFile = new File(intitalInputFile);
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+ "/GEDCOM/sprint.txt");

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

			String textInputFile = System.getProperty("user.dir")+"/GEDCOM/sprint.txt";
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
				
				String birthdate = "NA";
				if(indValue.getBirthday() != null)
				{
					Date birt = dateFormatGiven.parse(indValue.getBirthday());
					birthdate = dateFormat.format(birt);
				}
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
			/*
			As we are using Hashmap Duplicate entries cannot be stored and will confilcts
			So to test US22- We need to halt the code before second table (that is Family Table) creation 
			In individual table the duplicate entry has been skipped.
			Map<String, IndividualEntry> indMap=new HashMap<String, IndividualEntry>(hind);
			final ArrayList<IndividualEntry> dupInd = new ArrayList<IndividualEntry>();
			if(!dupInd.isEmpty()) {
				for(int i=0;i<dupInd.size();i++) {
					IndividualEntry ind1=dupInd.get(i);
					IndividualEntry ind2=indMap.get((dupInd).get(i).getId());
					System.out.println("ERROR: INDIVIDUAL: US22: "+ind1.getId()+": "+ind1.getName()+": has the same ID as " + ind2.getId()+": "+ind2.getName());
					}
			}
			*/
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
				}
				else {
					System.out.format("%-10s%-15s%-15s%-15s%-25s%-10s%-20s%-15s\n", keyFam, married, divorce,
							valueFam.getH_id(), hind.get(valueFam.getH_id().trim()).getName(), valueFam.getW_id(),
							hind.get(valueFam.getW_id().trim()).getName(), valueFam.getChild());
				}
			}

			System.out.println("");
			
			//=====================================Sprint - 1 USER STORIES==================================
			
			us02_birth_b4_marriage();           //KD
			us04_marriage_b4_divorce();         //KD
			us22_unique_ids(); 					//VS
			
			us16_Male_last_name(); 				//NP
			us18_siblings_should_not_marry(); 	//NP No Error
			us03_birth_before_death(); 			//DT
			us05_marriage_before_death(); 		//DT
			us07_less_than_150yrs(); 			//YN
			us08_birth_before_marriage(); 		//YN		
			
			//=====================================Sprint - 2 USER STORIES==================================
			us06_divorce_b4_death(); 			//KD	
			us10_marriage_after_14(); 			//KD
			us_35_recentbirth();				//VS
			us_38_upcomingbirthdays(); 			//VS
			us13_sibling_spacing(); 			//NP
			us14_multiple_births();				//NP No Error
			us15_fewer_than_15_siblings(); 		//DT
			us20_aunts_and_uncles(); 			//DT
			us17_no_marriage_to_children();     //YN
			//=====================================Sprint - 3 USER STORIES==================================
			us11_no_bigamy(); 					//KD

			if(failuresFlag)
			 {
				 for(String failString: failures)
				 {
					 System.out.println(failString);
				 }			 
			 }
			 else
			 {
				 System.out.println("All user stories passed successfully!");
         
			 }
			  
			//======================================================   End of all user stories   ======================================================
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
