package com.moodsensor.stem.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.json.JSONObject;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.moodsensor.stem.Enum.MoodState;
import com.moodsensor.stem.exception.DataServicesException;
import com.moodsensor.stem.exception.InvalidUserException;
import com.moodsensor.stem.service.MoodDataService;

import jakarta.servlet.ServletException;

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

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/mood/upload")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload)).andExpect(status().isOk()).andReturn();

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

    mockMvc.perform(MockMvcRequestBuilders.post("/api/mood/upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload))
        .andExpect(status().is4xxClientError())
        .andExpect(response -> assertTrue(
            response.getResolvedException() instanceof MethodArgumentNotValidException))
        .andReturn();

  }

  @Test
  public void testuploadMoodInvalidUser() throws Exception {

    String jsonPayload =
        "{\n" + "  \"userId\": 12345,\n" + "  \"mood\": \"happy\",\n" + "  \"latitude\": 40.712776, \n" +
            "  \"longitude\": -74.005974\n" + "}\n";

    Mockito.doThrow(new InvalidUserException("invalid user"))
        .when(moodDataService)
        .uploadMood(12345L, "happy", 40.712776, -74.005974);

    Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(
            MockMvcRequestBuilders.post("/api/mood/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
        .andExpect(status().is4xxClientError())
        .andExpect(response -> assertTrue(response.getResolvedException() instanceof InvalidUserException))
        .andReturn());

  }

  @Test
  public void testGetMoodFrequency() throws Exception {

    when(moodDataService.getMoodFrequency(1L)).thenReturn(Map.of(MoodState.HAPPY.getName(), 1l));

    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/mood/frequency/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
    Assertions.assertEquals(1, jsonObject.get(MoodState.HAPPY.getName()));
  }

  @Test
  public void testGetMoodFrequencyThrowDataServicesException() throws Exception {

    when(moodDataService.getMoodFrequency(1L)).thenThrow(DataServicesException.class);

    Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(
            MockMvcRequestBuilders.get("/api/mood/frequency/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(response -> assertTrue(response.getResolvedException() instanceof DataServicesException))
        .andReturn());

  }

//  @Test
//  public void testGetClosestHappyLocation() throws Exception {
//
//    when(moodDataService.getClosestHappyLocation(1L, 0.0, 0.0)).thenReturn(new NearestMoodData());
//
//    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/mood/happy-location/1")
//        .requestAttr("latitude", 0.0)
//        .requestAttr("longitude", 0.0)
//        ).andExpect(status().isOk()).andReturn();
//
//    Assertions.assertNotNull(result.getResponse());
//  }

  @Test
  public void testGetClosestHappyLocationThrowDataServicesException() throws Exception {

    when(moodDataService.getClosestHappyLocation(1L, 0.0, 0.0)).thenThrow(DataServicesException.class);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/mood/happy-location/1")
            .requestAttr("latitude", 0.0)
            .requestAttr("longitude", 0.0)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(response -> assertTrue(response.getResolvedException() instanceof ServletException))
        .andReturn();

  }

}
