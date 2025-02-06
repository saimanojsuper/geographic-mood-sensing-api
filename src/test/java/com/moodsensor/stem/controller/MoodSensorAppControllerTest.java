package com.moodsensor.stem.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.moodsensor.stem.service.MoodDataService;

public class MoodSensorAppControllerTest {

  @Mock private MoodDataService moodDataService;
  @InjectMocks private MoodSensorAppController moodSensorAppController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(moodSensorAppController).build();
  }

  @Test
  public void testuploadMood() throws Exception {

    String jsonPayload =
        "{\n" + "  \"userId\": 12345,\n" + "  \"mood\": \"happy\",\n" + "  \"latitude\": 40.712776, \n" +
            "  \"longitude\": -74.005974\n" + "}\n";

    Mockito.doNothing().when(moodDataService).uploadMood(12345L, "happy", 40.712776, -74.005974);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/mood/upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_PLAIN))
        .andReturn();

    String expected = "Mood uploaded successfully.";
    String actual = result.getResponse().getContentAsString();
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testuploadMoodInvalidData() throws Exception {

    String jsonPayload =
        "{\n" + "  \"userId\": 12345,\n" + "  \"mood\": \"happys\",\n" + "  \"latitude\": 40.712776, \n" +
            "  \"longitude\": -74.005974\n" + "}\n";

    Mockito.doNothing().when(moodDataService).uploadMood(12345L, "happy", 40.712776, -74.005974);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/mood/upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload))
        .andExpect(status().isOk())
        //        .andExpect(content().contentType(MediaType.TEXT_PLAIN))
        .andReturn();

    String expected = "Mood uploaded successfully.";
    String actual = result.getResponse().getContentAsString();
    Assertions.assertEquals(expected, actual);
    //Assert the data invalid execption
  }

  public void testuploadMoodInvalidUser() {

    //Assert the invalid user exception
  }

  public void testGetMoodFrequency() {

    //Assert various moods
  }

  public void testGetMoodFrequencyThrowDataServicesException() {

    //Assert exception
  }

  public void testGetClosestHappyLocation() {

    //Assert data
  }

  public void testGetClosestHappyLocationThrowDataServicesException() {

    //Assert exception
  }

}
