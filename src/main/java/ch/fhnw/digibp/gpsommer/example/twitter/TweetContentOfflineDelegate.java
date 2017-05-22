package ch.fhnw.digibp.gpsommer.example.twitter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("dataPreparationAdapter")
public class TweetContentOfflineDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    String content = (String) execution.getVariable("requesterName");
    execution.setVariable("age",20);
    execution.setVariable("bmi",20);

    System.out.println("\n\n\n######\n\n\n");
    System.out.println("NAME IS: '" + content + "'");
    System.out.println("AGE IS: '" + (Integer) execution.getVariable("age") + "'");
    System.out.println("BMI IS: '" + (Integer) execution.getVariable("bmi") + "'");
    System.out.println("\n\n\n######\n\n\n");
  }

}
