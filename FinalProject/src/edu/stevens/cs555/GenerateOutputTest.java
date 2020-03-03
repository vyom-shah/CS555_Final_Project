package edu.stevens.cs555;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
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
			assertEquals(true,GenerateOutput.us22_unique_ids(),"User story 22 failed!");
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
}
