package edu.stevens.cs555;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class GenerateOutputTest {


	@BeforeAll
	static void setUpBeforeClass() throws Exception { 
		String []args = null;
		GenerateOutput.main(args);
	}
	
	@Test
	void test_us02_birth_b4_marriage() {
		try {
			assertEquals(true, GenerateOutput.us02_birth_b4_marriage(), "User story 02 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	void test_us04_marriage_b4_divorce() {
		try {
			assertEquals(true, GenerateOutput.us04_marriage_b4_divorce(), "User story 04 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us22_unique_id() {
		try {
			assertEquals(true,GenerateOutput.us20_aunts_and_uncles(),"User story 22 failed!");
		}
		catch( ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	void test_us06_divorce_b4_death() {
		try {
			assertEquals(true, GenerateOutput.us06_divorce_b4_death(), "User story 06 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test 
	void test_us10_marriage_after_14() {
		try {
			assertEquals(true, GenerateOutput.us10_marriage_after_14(), "User story 10 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us03_birth_before_death() {
		try {
			assertEquals(true, GenerateOutput.us03_birth_before_death(), "User story 03 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us05_marriage_before_death() {
		try {
			assertEquals(true, GenerateOutput.us05_marriage_before_death(), "User story 05 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us07_less_than_150yrs() {
		try {
			assertEquals(true, GenerateOutput.us07_less_than_150yrs(), "User story 07 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us08_birth_before_marriage() {
		try {
			assertEquals(true, GenerateOutput.us07_less_than_150yrs(), "User story 08 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us16_Male_last_name() {
		try {
			assertEquals(true, GenerateOutput.us16_Male_last_name(), "User story 16 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us18_siblings_should_not_marry() {
		try {
			assertEquals(true, GenerateOutput.us18_siblings_should_not_marry(), "User story 18 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us15_fewer_than_15_siblings() {
		try {
			assertEquals(true, GenerateOutput.us15_fewer_than_15_siblings(), "User story 15 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us20_aunts_and_uncles()  {
		try {
			assertEquals(true, GenerateOutput.us20_aunts_and_uncles(), "User story 20 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

//	@Test
//	void test_getAge()  {
//		try {
//			assertEquals(true, GenerateOutput.getAge(), "User story 27 failed!");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
	@Test
	void test_us35_recentbirth()  {
		try {
			assertEquals(true, GenerateOutput.us_35_recentbirth(), "User story 35 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us32_list_multiple_births()  {
		try {
			assertEquals(true, GenerateOutput.us32_list_multiple_births(), "User story 32 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
		@Test
	void test_us38_upcomingbirthdays() {
		try {
			assertEquals(true, GenerateOutput.us_38_upcomingbirthdays(), "User story 38 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
		@Test
		void test_us13_sibling_spacing() {
			try {
				assertEquals(true, GenerateOutput.us13_sibling_spacing(), "User story 13 failed!");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		@Test
		void test_us14_multiple_births() {
			try {
				assertEquals(true, GenerateOutput.us14_multiple_births(), "User story 14 failed!");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
      
      
	  @Test    
	  void test_us11_no_bigamy()  {
			try {
				assertEquals(true, GenerateOutput.us11_no_bigamy(), "User story 11 failed!");
	
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	  
	  @Test    
	  void test_us12_parents_not_too_old()  {
			try {
				assertEquals(true, GenerateOutput.us12_parents_not_too_old(), "User story 12 failed!");
	
			} catch (ParseException e) {
				e.printStackTrace();
			}
	  }
	  @Test
		void test_us27individualAge() {
			assertEquals(true,GenerateOutput.getAge(),"User story 27 failed!");
		}

		@Test
	void test_us17no_marriage_to_children() {
		try {
			assertEquals(true, GenerateOutput.us17_no_marriage_to_children(), "User story 17 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us21_correctgender_for_role() {
		try {
			assertEquals(true, GenerateOutput.us21_correctgender_for_role(), "User story 21 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us09_birthbeforedeathofparents() {
		try {
			assertEquals(true, GenerateOutput.us09_birthbeforedeathofparents(), "User story 09 failed!");
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
  }
  	@Test
	void test_us24_Unique_families_by_spouses()  {
		try {
		assertEquals(true, GenerateOutput.us24_Unique_families_by_spouses(), "User story 24 failed!");
    }catch (ParseException e) {
			e.printStackTrace();
		}
  }

  @Test
	void test_us36_recentdeaths() {
		try {
			assertEquals(true, GenerateOutput.us_36_recentdeaths(), "User story 36 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us_39_upcominganniversaries() {
		try {
			assertEquals(true, GenerateOutput.us_39_upcominganniversaries(), "User story 39 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us31_listLivingSingle() {
		try {
			assertEquals(true, GenerateOutput.us24_Unique_families_by_spouses(), "User story 31 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us30_list_living_married()  {
		try {
			String expectedId="@I2@";
			ArrayList<String> ans=GenerateOutput.us30_list_living_married();   
			assertEquals(true, ans.contains(expectedId), "User story 30 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us34_list_living_married()  {
		try {
			String expectedId="@I35@";
			ArrayList<String> ans=GenerateOutput.us34_list_living_married();   
			assertEquals(true, ans.contains(expectedId), "User story 34 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us29_list_deceased()
	{
		try {
			String id_of_libving="@I19@";
			ArrayList<String> id_of_deceased = GenerateOutput.us29_list_deceased();   
			assertEquals(true, id_of_deceased.contains(id_of_libving), "User story 29 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void test_us23_unique_name_and_birth_date()
	{
		try {
			assertEquals(true, GenerateOutput.us23_unique_name_and_birth_date(), "User story 23 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test_us25_unique_firstnames_infamilies() {
		try {
			assertEquals(true, GenerateOutput.us25_unique_firstnames_infamilies(), "User story 25 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	void test_us37_listrecent_survivors() {
		try {
			assertEquals(true, GenerateOutput.us24_Unique_families_by_spouses(), "User story 37 failed!");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	void test_us01_datesBeforeCurrentdate() {
//		try {
//			assertEquals(true, GenerateOutput.us01_datesBeforeCurrentdate(), "User story 01 failed!");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
}
