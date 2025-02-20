package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

    //Tests normal add behavior
    //Targets the add() function of the binary class
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }

    //Tests to make sure the operand1 is in focus
    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }

    //Tests normal behavior for add operator
    //Targets the add() function of the binary class
	@Test
    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }

    //3 newly generated test cases
    //Tests for a single operand without operator or operand2, should result in graceful error handling
    //Targets the add() function of the binary class
    @Test
    public void postSingleOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1101"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    //Tests for empty operand1 value resulting in the sum to be operand2
    //Targets the add() function of the binary class
    @Test
    public void postEmptyOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1", "").param("operator", "+").param("operand2", "101"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"));
    }

    //Tests for very large operand values handling gracefully in the GUI
    //Targets the add() function of the binary class
    @Test
    public void postLargeBinaryNumbers() throws Exception {
        this.mvc.perform(post("/").param("operand1", "110110110110110110110110110110")
                        .param("operator", "+")
                        .param("operand2", "101010101010101010101010101010"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1100001100001100001100001100000")); // Expected sum
    }


    //Test cases for the new operators *, |, and &
    //Targets the multiply() function of the binary class
    @Test
    public void postMultiplicationNormalCase() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "*").param("operand2", "11"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1111")); // 5 * 3 = 15 (binary: 101 * 11 = 1111)
    }

    //Tests for multiplication by zero which should result in zero in all cases
    //Targets the multiply() function of the binary class
    @Test
    public void postMultiplicationByZero() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1010").param("operator", "*").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "0")); // Anything * 0 = 0
    }

    //Tests for multiplication by zero which should result in zero in all cases
    //Targets the multiply() function of the binary class
    @Test
    public void postMultiplicationByOne() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1010").param("operator", "*").param("operand2", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1010")); // Anything * 1 = Itself
    }

    //Tests multiplication with leading zeros which should not affect the final result
    //Targets the multiply() function of the binary class
    @Test
    public void postMultiplicationWithLeadingZeros() throws Exception {
        this.mvc.perform(post("/").param("operand1", "0011").param("operator", "*").param("operand2", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "110")); // 3 * 2 = 6 (binary: 0011 * 10 = 110)
    }

    //Tests for normal behavior for the OR operator
    //Targets the or() function of the binary class
    @Test
    public void postBitwiseOrNormalCase() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "|").param("operand2", "011"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "111")); // 101 | 011 = 111
    }

    //Tests OR operator with zero which should result in the original operand value
    //Targets the or() function of the binary class
    @Test
    public void postBitwiseOrWithZero() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1010").param("operator", "|").param("operand2", "0000"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1010")); // 1010 | 0000 = 1010
    }

    //Tests OR operator with itself which should result in the original operand value
    //Targets the or() function of the binary class
    @Test
    public void postBitwiseOrWithItself() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1101").param("operator", "|").param("operand2", "1101"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1101")); // A | A = A
    }

    //Tests normal behavior of AND operator
    //Targets the and() function of the binary class
    @Test
    public void postBitwiseAndNormalCase() throws Exception {
        this.mvc.perform(post("/").param("operand1", "101").param("operator", "&").param("operand2", "011"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1")); // 101 & 011 = 001
    }

    //Tests AND operator with zero resulting in zero in every case
    //Targets the and() function of the binary class
    @Test
    public void postBitwiseAndWithZero() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1101").param("operator", "&").param("operand2", "0000"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "0")); // A & 0 = 0
    }

    //Tests AND operator with itself which should always result in the original operand values
    //Targets the and() function of the binary class
    @Test
    public void postBitwiseAndWithItself() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1101").param("operator", "&").param("operand2", "1101"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1101")); // A & A = A
    }

}
