package br.com.waes.jb64diff.service;

import java.util.List;

import br.com.waes.jb64diff.model.JB64Data;

public interface JB64DataService {
	JB64Data getJB64DataById(long id);
	List<JB64Data> getAllJB64Data();
	JB64Data addJB64Data(JB64Data data);
	JB64Data modify(JB64Data newData);
}
