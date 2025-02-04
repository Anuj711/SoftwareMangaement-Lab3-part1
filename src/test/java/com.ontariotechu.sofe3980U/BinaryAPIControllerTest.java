package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

    //Tests normal add behavior
    //Targets the add() function of the binary class
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }

    //Tests normal add behavior with JSON format
    //Targets the add() function of the binary class
    @Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    //3 more tests
    //Ensures that the summation value doubles when adding identical binary numbers
    //Targets the add() function of the binary class
    @Test
    public void addIdenticalBinaryNumbers() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "1011").param("operand2", "1011"))
                .andExpect(status().isOk())
                .andExpect(content().string("10110")); // 1011 + 1011 = 10110
    }


    //Tests to make sure leading zeros do not affect the output
    //Targets the add() function of the binary class
    @Test
    public void addLeadingZeros() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "000101").param("operand2", "000011"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000")); // 5 + 3 = 8 in binary
    }

    //Tests to make sure adding 0 to a number returns the original number
    //Targets the add() function of the binary class
    @Test
    public void addZeroEdgeCase() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("1011")); // 1011 + 0 = 1011
    }


    //Test cases for the new operators *, |, and &
    //Tests normal function of multiplying two binary numbers
    //Targets the multiply() function of the binary class
    @Test
    public void apiMultiplyBinaryNumbers() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "101").param("operand2", "11"))
                .andExpect(status().isOk())
                .andExpect(content().string("1111")); // 101 * 11 = 1111
    }

    //Tests multiplying by zero resulting in zero each time
    //Targets the multiply() function of the binary class
    @Test
    public void apiMultiplyByZero() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("0")); // Any number * 0 = 0
    }

    //Tests multiplying by one resulting in the same operand each time
    //Targets the multiply() function of the binary class
    @Test
    public void apiMultiplyByOne() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "1011").param("operand2", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1011")); // Any number * 1 = itself
    }

    // Tests normal OR behavior
    //Targets the or() function of the binary class
    @Test
    public void apiBitwiseOrBinaryNumbers() throws Exception {
        this.mvc.perform(get("/bitwise_or").param("operand1", "101").param("operand2", "011"))
                .andExpect(status().isOk())
                .andExpect(content().string("111")); // 101 | 011 = 111
    }

    // Tests OR operator with zero resulting in the original operand each time
    //Targets the or() function of the binary class
    @Test
    public void apiBitwiseOrWithZero() throws Exception {
        this.mvc.perform(get("/bitwise_or").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("1011")); // Any number | 0 = itself
    }

    //Tests the normal AND operator behavior
    //Targets the and() function of the binary class
    @Test
    public void apiBitwiseAndBinaryNumbers() throws Exception {
        this.mvc.perform(get("/bitwise_and").param("operand1", "101").param("operand2", "011"))
                .andExpect(status().isOk())
                .andExpect(content().string("1")); // 101 & 011 = 001
    }

    //Tests the AND operator behavior with zero resulting in zero every time
    //Targets the and() function of the binary class
    @Test
    public void apiBitwiseAndWithZero() throws Exception {
        this.mvc.perform(get("/bitwise_and").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("0")); // Any number & 0 = 0
    }

    //Tests the AND operator behavior with itself resulting in original operand every time
    //Targets the and() function of the binary class
    @Test
    public void apiBitwiseAndWithItself() throws Exception {
        this.mvc.perform(get("/bitwise_and").param("operand1", "1011").param("operand2", "1011"))
                .andExpect(status().isOk())
                .andExpect(content().string("1011")); // Any number & itself = itself
    }


    //The following tests are all the same tests as above but give the result in JSON format
    @Test
    public void multiplyJson() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "101").param("operand2", "11"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

    @Test
    public void multiplyByZeroJson() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

    @Test
    public void multiplyByOneJson() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "1011").param("operand2", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

    @Test
    public void bitwiseOrJson() throws Exception {
        this.mvc.perform(get("/bitwise_or_json").param("operand1", "101").param("operand2", "011"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("bitwise_or"));
    }

    @Test
    public void bitwiseOrWithZeroJson() throws Exception {
        this.mvc.perform(get("/bitwise_or_json").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("bitwise_or"));
    }

    @Test
    public void bitwiseAndJson() throws Exception {
        this.mvc.perform(get("/bitwise_and_json").param("operand1", "101").param("operand2", "011"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("bitwise_and"));
    }

    @Test
    public void bitwiseAndWithZeroJson() throws Exception {
        this.mvc.perform(get("/bitwise_and_json").param("operand1", "1011").param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("bitwise_and"));
    }

    @Test
    public void bitwiseAndWithItselfJson() throws Exception {
        this.mvc.perform(get("/bitwise_and_json").param("operand1", "1011").param("operand2", "1011"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("bitwise_and"));
    }


}