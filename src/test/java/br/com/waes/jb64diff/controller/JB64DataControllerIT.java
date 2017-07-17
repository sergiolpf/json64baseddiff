package br.com.waes.jb64diff.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.waes.jb64diff.Jb64diffApplication;
import br.com.waes.jb64diff.model.JB64Data;
import br.com.waes.jb64diff.model.JSONData;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jb64diffApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JB64DataControllerIT {
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void addLeft() throws Exception {
		JSONData jsonData = new JSONData();
		jsonData.setEncodedData("bGVmdERhdGE=");
		
		HttpEntity<JSONData> entity = new HttpEntity<>(jsonData);
		
		ResponseEntity<JB64Data> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/v1/diff/1/left",
				HttpMethod.POST, entity, JB64Data.class);
			
		JB64Data responseData = responseEntity.getBody();

		assertEquals(responseData.getLeft(), "bGVmdERhdGE=");
	}
	
	@Test
	public void addRight() throws Exception {
		JSONData jsonData = new JSONData();
		jsonData.setEncodedData("bGVmdERhdGE=");
		
		HttpEntity<JSONData> entity = new HttpEntity<>(jsonData);
		
		ResponseEntity<JB64Data> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/v1/diff/1/right",
				HttpMethod.POST, entity, JB64Data.class);
			
		JB64Data responseData = responseEntity.getBody();

		assertEquals(responseData.getRight(), "bGVmdERhdGE=");
	}
	
	@Test
	public void addLeftAndRightAndTheyAreTheSame()  throws Exception {
		JSONData jsonData = new JSONData();
		jsonData.setEncodedData("bGVmdERhdGE=");
		
		String resourceUrl = "http://localhost:" + port + "/v1/diff/1";
		
		HttpEntity<JSONData> entity = new HttpEntity<>(jsonData);
		
		ResponseEntity<JB64Data> responseEntity = restTemplate.exchange(
				resourceUrl + "/left",
				HttpMethod.POST, entity, JB64Data.class);
		
		responseEntity = restTemplate.exchange(
				resourceUrl + "/right",
				HttpMethod.POST, entity, JB64Data.class);
		
		JB64Data responseData = responseEntity.getBody();
		
		assertEquals(responseData.getRight(), responseData.getLeft());
		
		ResponseEntity<String> response =  
		  restTemplate.getForEntity(resourceUrl, String.class);
		
		System.out.println(response.getBody());
	}
	
	
}
