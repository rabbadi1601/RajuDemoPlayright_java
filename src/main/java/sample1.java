import java.util.Map;

import com.baselibrary.KeyUtilities;

public class sample1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 String xlsfilePath = ".//TestData//TestData.xlsx";
		 KeyUtilities ku=new KeyUtilities();
		 Map<String, String> knowmeData = ku.readExcelData(xlsfilePath, "Knowme");
		 System.out.println(knowmeData.get("vehicles"));
		 System.out.println(knowmeData.get("employees"));
	}

}
