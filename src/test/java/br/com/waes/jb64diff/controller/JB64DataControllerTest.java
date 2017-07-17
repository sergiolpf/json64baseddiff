package br.com.waes.jb64diff.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.waes.jb64diff.model.Diff;
import br.com.waes.jb64diff.model.Insight;
import br.com.waes.jb64diff.model.JB64Data;
import br.com.waes.jb64diff.repository.JB64DataRepository;
import br.com.waes.jb64diff.service.InsightService;
import br.com.waes.jb64diff.service.JB64DataService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = JB64DataController.class, secure = false)
public class JB64DataControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JB64DataService jb64DataService;

	@MockBean
	private JB64DataRepository repository;

	@MockBean
	private InsightService insightService;

	List<JB64Data> mockList = new ArrayList<>(
			Arrays.asList(new JB64Data(1L, "leftData", "rightData"), new JB64Data(2L, "John", "Smit")));

	Insight mockEqualInsight = new Insight(true, true, new ArrayList<Diff>());
	Insight mockNotEqualNotSameSizeInsight = new Insight(false, false, new ArrayList<Diff>());
	Insight mockNotEqualWithSameSizeInsight = new Insight(false, true,
			new ArrayList<Diff>(Arrays.asList(new Diff(2, 1))));

	String exampleOfEncodedData = "{\"encodedData\":\"bGVmdERhdGE=\"}";// means leftData
	String exampleOfEncodedData2 = "{\"encodedData\":\"cmlnaHREYXRh\"}"; // means righData
	
	JB64Data mockOnlyLeftRespose = new JB64Data(1L, new String(Base64.getDecoder().decode("bGVmdERhdGE=")), "" );
	JB64Data mockOnlyRightRespose = new JB64Data(1L, "", new String(Base64.getDecoder().decode("cmlnaHREYXRh")) );
	JB64Data mockModifyRespose = new JB64Data(1L, 
			new String(Base64.getDecoder().decode("bGVmdERhdGE")), 
			new String(Base64.getDecoder().decode("cmlnaHREYXRh")) );
	
	JB64Data mockEquals = new JB64Data(1L, "equals", "equals");

	// String exampleListOfJSON =
	// "[{\"id\":1,\"left\":\"leftData\",\"right\":\"rightData\"},{\"id\":2,\"left\":\"John\",\"right\":\"Smit\"}]";

	@Test
	public void retrieveAllJB64Data() throws Exception {

		Mockito.when(jb64DataService.getAllJB64Data()).thenReturn(mockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/diff/").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{\"id\":1,\"left\":\"leftData\",\"right\":\"rightData\"},{\"id\":2,\"left\":\"John\",\"right\":\"Smit\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getInsightsForEqual() throws Exception {
		
		Mockito.when(insightService.getInsightById(Mockito.anyLong())).thenReturn(mockEqualInsight);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{\"areEqual\":true,\"areSameSize\":true,\"diffs\":[]}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void getInsightsForNonEqualNotSameSize() throws Exception {

		Mockito.when(insightService.getInsightById(Mockito.anyLong())).thenReturn(mockNotEqualNotSameSizeInsight);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"areEqual\":false,\"areSameSize\":false,\"diffs\":[]}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void getInsightsForNonEqualWithSameSize() throws Exception {

		Mockito.when(insightService.getInsightById(Mockito.anyLong())).thenReturn(mockNotEqualWithSameSizeInsight);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"areEqual\":false,\"areSameSize\":true,\"diffs\":[{\"offSet\":2,\"length\":1}]}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void setLeftOfNonExistingRecord() throws Exception {
		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(jb64DataService.addJB64Data(Mockito.any(JB64Data.class)))
				.thenReturn(mockOnlyLeftRespose);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.content(exampleOfEncodedData).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"id\":1,\"left\":\"bGVmdERhdGE=\",\"right\": \"\"}";
				
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);

	}
	
	@Test
	public void setLeftOfExistingRecord() throws Exception {
		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(mockOnlyRightRespose);
		Mockito.when(jb64DataService.modify(Mockito.any(JB64Data.class)))
				.thenReturn(mockModifyRespose);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.content(exampleOfEncodedData).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"id\":1,\"left\":\"bGVmdERhdGE=\",\"right\": \"cmlnaHREYXRh\"}";
				
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);

	}
	
	@Test
	public void setRightOfNonExistingRecord() throws Exception {
		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(jb64DataService.addJB64Data(Mockito.any(JB64Data.class)))
				.thenReturn(mockOnlyRightRespose);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.content(exampleOfEncodedData).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"id\":1,\"left\":\"\",\"right\": \"cmlnaHREYXRh\"}";
				
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);

	}
	
	@Test
	public void setRightOfExistingRecord() throws Exception {
		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(mockOnlyLeftRespose);
		Mockito.when(jb64DataService.modify(Mockito.any(JB64Data.class)))
				.thenReturn(mockModifyRespose);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.content(exampleOfEncodedData).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"id\":1,\"left\":\"bGVmdERhdGE=\",\"right\": \"cmlnaHREYXRh\"}";
				
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);

	}
}
