package br.com.waes.jb64diff.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.waes.jb64diff.model.Diff;
import br.com.waes.jb64diff.model.Insight;
import br.com.waes.jb64diff.model.JB64Data;

@RunWith(SpringRunner.class)
public class InsightServiceTest {
	
	@MockBean
	private JB64DataService jb64DataService;
	
	@InjectMocks
	InsightServiceImpl insightService;
	
	JB64Data mockEquals = new JB64Data(1L, "equals", "equals");
	JB64Data mockNotEqualsNotSameSize = new JB64Data(1L, "equals", "");
	JB64Data mockNotEqualsSameSize = new JB64Data(1L, "equals", "equlas");
	
	Insight mockEqualInsight = new Insight(true, true, new ArrayList<Diff>());
	Insight mockNotEqualNotSameSizeInsight = new Insight(false, false, new ArrayList<Diff>());
	Insight mockNotEqualWithSameSizeInsight = new Insight(false, true,
			new ArrayList<Diff>(Arrays.asList(new Diff(4, 2))));
	
	@Before
    public void setUp() throws Exception {
      MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getInsightsForEqual() throws Exception {
		
		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(mockEquals);
		
		Insight returnedInsight = insightService.getInsightById(1L);
		
		Assert.assertEquals(mockEqualInsight, returnedInsight);
	}
	
	@Test
	public void getInsightsForNonEqualNotSameSize() throws Exception {

		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(mockNotEqualsNotSameSize);

		Insight returnedInsight = insightService.getInsightById(1L);

		Assert.assertEquals(mockNotEqualNotSameSizeInsight, returnedInsight);
	}
	
	@Test
	public void getInsightsForNonEqualWithSameSize() throws Exception {

		Mockito.when(jb64DataService.getJB64DataById(Mockito.anyLong())).thenReturn(mockNotEqualsSameSize);

		Insight returnedInsight = insightService.getInsightById(1L);

		Assert.assertEquals(mockNotEqualWithSameSizeInsight, returnedInsight);

	}


}
