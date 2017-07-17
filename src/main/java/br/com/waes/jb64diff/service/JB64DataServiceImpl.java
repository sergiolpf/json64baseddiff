package br.com.waes.jb64diff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.waes.jb64diff.model.JB64Data;
import br.com.waes.jb64diff.repository.JB64DataRepository;


@Service("jb64DataService")
public class JB64DataServiceImpl implements JB64DataService {

	@Autowired
	JB64DataRepository jb64DataRepository; 
	
	/**
	 * Retrieves record from database using id
	 * 
	 * @param id
	 * @return {@link JB64Data}
	 */
	@Override
	public JB64Data getJB64DataById(long id) {
		return jb64DataRepository.findOne(id);
	}

	/**
	 * Retrieves all record from database
	 * 
	 * 
	 * @return List<{@link JB64Data}>
	 */
	@Override
	public List<JB64Data> getAllJB64Data() {
		return jb64DataRepository.findAll();
	}

	/**
	 * Inserts record to database
	 * 
	 * @param {@link JB64Data}
	 * @return added Record
	 */
	@Override
	public JB64Data addJB64Data(JB64Data data) {
		return jb64DataRepository.saveAndFlush(data);
		
	}

	/**
	 * Modifies record
	 * 
	 * @param {@link JB64Data}
	 * @return modified Record
	 */
	@Override
	public JB64Data modify(JB64Data newData) {
		JB64Data data = jb64DataRepository.findOne(newData.getId());
		data.setLeft(newData.getLeft());
		data.setRight(newData.getRight());
		
		return jb64DataRepository.saveAndFlush(data);
	}
	
	

}
