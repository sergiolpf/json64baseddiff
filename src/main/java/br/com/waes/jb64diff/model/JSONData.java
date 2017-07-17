package br.com.waes.jb64diff.model;

/**
 * POJO used by Jackson to parse the JSON to be used by the Controller
 * 
 * @author sergiolpf
 *
 */
public class JSONData {

	private String encodedData;
	
	public JSONData(){
		
	}

	public JSONData(String data) {
		super();
		this.encodedData = data;
	}

	public String getEncodedData() {
		return encodedData;
	}

	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}

	@Override
	public String toString() {
		return "JSONData [encodedData= \"" + encodedData + "\"]";
	}

	
	
	
}
