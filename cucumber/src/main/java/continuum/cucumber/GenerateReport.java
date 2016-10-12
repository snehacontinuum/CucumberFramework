package continuum.cucumber;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;

public class GenerateReport {

	
	
	public static void generateReport(){
		CucumberResultsOverview results = new CucumberResultsOverview();
		results.setOutputDirectory("test-report");
		results.setOutputName("cucumber-results");
		results.setSourceFile("test-report/cucumber.json");
		try {
			results.executeFeaturesOverviewReport();
		} catch (Exception e) {
			System.out.println("Not able to create cucumber reports"+e.getMessage());
		}
		
	}
}

