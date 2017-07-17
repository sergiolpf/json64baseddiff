package br.com.waes.jb64diff.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.waes.jb64diff.model.Insight;
import br.com.waes.jb64diff.model.JB64Data;
import br.com.waes.jb64diff.model.JSONData;
import br.com.waes.jb64diff.service.InsightService;
import br.com.waes.jb64diff.service.JB64DataService;

/**
 * Controller responsible for handling all REST requests
 * 
 * @author sergiolpf
 *
 */
@RestController
@RequestMapping(value = "/v1/diff")
public class JB64DataController {
	
	
	@Autowired
	private JB64DataService jb64DataService;
	
	@Autowired
	private InsightService insightService;
	
	/**
	 * Method responsible for returning all Data Stored in
	 * the database;
	 * 
	 * @return List of JB64Data
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public List<JB64Data> getJB64Datas() {
		return jb64DataService.getAllJB64Data();
	}

	
	/**
	 * Calculate de {@link Insight} between the two given
	 * base64 Json.
	 * 
	 * @see InsightService#getInsightById(long)
	 * 
	 * @param id
	 * @return the Insight
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Insight getJB64Data(@PathVariable("id") long id) {
		return insightService.getInsightById(id);
	}
	
	
	/**
	 * Adds or modifies the left side of record as per 
	 * the content of the sent JSON
	 * 
	 * @param id
	 * @param encodedData - JSON with the format 
	 * 		{"encodedData":"base64EncodedData"}
	 * 
	 * @return the modified record
	 */
    @RequestMapping(value = "/{id}/left", method = RequestMethod.POST)
    public JB64Data addToLeftJB64Data(@PathVariable("id") long id, @RequestBody JSONData encodedData) {
    	
    	JB64Data returnedData = jb64DataService.getJB64DataById(id);
    	
    	if (null == returnedData) {
    		returnedData = jb64DataService.addJB64Data(new JB64Data(id,
    				new String(Base64.getDecoder().decode(encodedData.getEncodedData())),
    				""));
    	}
    	else {
    		returnedData.setLeft(new String(Base64.getDecoder().decode(encodedData.getEncodedData())));
    		returnedData = jb64DataService.modify(returnedData);
    	}
		return encode(returnedData);
	}

    /**
	 * Adds or modifies the right side of record as per 
	 * the content of the sent JSON
	 * 
	 * @param id
	 * @param encodedData - JSON with the format 
	 * 		{"encodedData":"base64EncodedData"}
	 * 
	 * @return the modified record
	 */
    @RequestMapping(value = "/{id}/right", method = RequestMethod.POST)
    public JB64Data addToRightJB64Data(@PathVariable("id") long id, @RequestBody JSONData encodedData) {
    	JB64Data returnedData = jb64DataService.getJB64DataById(id);
    	
    	if (null == returnedData) {
    		returnedData = jb64DataService.addJB64Data(new JB64Data(id, 
    				"", 
    				new String(Base64.getDecoder().decode(encodedData.getEncodedData()))));
    	}
    	else {
    		returnedData.setRight(new String(Base64.getDecoder().decode(encodedData.getEncodedData())));
    		returnedData = jb64DataService.modify(returnedData);
    	}
    	return encode(returnedData);
	}
    
    private JB64Data encode(JB64Data input) {
    	if (input == null) return null;
    	
    	if (input.getLeft().length() != 0){
    		input.setLeft(Base64.getEncoder().encodeToString(input.getLeft().getBytes()));
    	}
    	if (input.getRight().length() != 0){
    		input.setRight(Base64.getEncoder().encodeToString(input.getRight().getBytes()));
    	}
    	
    	return input;
    }
}
