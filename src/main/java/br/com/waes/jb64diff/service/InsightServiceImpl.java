package br.com.waes.jb64diff.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.waes.jb64diff.model.Diff;
import br.com.waes.jb64diff.model.Insight;
import br.com.waes.jb64diff.model.JB64Data;

/**
 * Provides a method to calculate an Insight
 * @author sergiolpf
 *
 */
@Service("insightService")
public class InsightServiceImpl implements InsightService {
	
	@Autowired
	private JB64DataService jb64DataService;
	
	@Override
	public Insight getInsightById(long id) {
		JB64Data findOne = jb64DataService.getJB64DataById(id);
		
		return checkDiff(findOne.getLeft(), findOne.getRight());
		
	}
	
	/**
	 * Calculates an {@link Insight} and returns:
	 * If left equals right then set areEqual to true;
	 * If not of equal size then set areSameSize to false;
	 * If of same size provide mainly offsets + length in the data
	 * 
	 * @param left
	 * @param right
	 * @return {@link Insight} instance
	 */
	private Insight checkDiff(String left, String right){
		Insight insight = new Insight();
		
		if (left.equals(right)){
			insight.setAreEqual(true);
			insight.setAreSameSize(true);
		} else if (left.length() == right.length()) {
			
			int offSet = 0;
			int length = 0;
			List<Diff> diffs = new ArrayList<>();
			
			String[] leftArray = left.split("");
			String[] rightArray = right.split("");
			
			for (int position = 0; position < left.length(); position++){
				if (leftArray[position].equals(rightArray[position]) && length > 0) {
					diffs.add(new Diff(offSet, length));
					length=0;
				}
				if (!(leftArray[position].equals(rightArray[position]))){
					if (length==0){
						offSet = position+1;
					}
					++length;
					
				}
			}
			
			if (length > 0 ) {
				diffs.add(new Diff(offSet, length));
			}
			
			insight.setDiffs(diffs);
			insight.setAreSameSize(true);
		}
		
		return insight;
	}
}
