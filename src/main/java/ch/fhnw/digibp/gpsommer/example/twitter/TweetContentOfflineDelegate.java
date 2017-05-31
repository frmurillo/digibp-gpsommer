package ch.fhnw.digibp.gpsommer.example.twitter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.context.Context;
import sun.awt.KeyboardFocusManagerPeerImpl;

import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Named("dataPreparationAdapter")
public class TweetContentOfflineDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    Integer height = (Integer) execution.getVariable("requesterHeight");
    Integer weight = (Integer) execution.getVariable("requesterWeight");
    String dateOfBirthString = (String) execution.getVariable("dateOfBirth");
    Double bmi = calculateBMI(height, weight);
    Integer age = calculateAge(dateOfBirthString);

    execution.setVariable("bmi",bmi);
    execution.setVariable("age",age);

    Context.getCommandContext().getProcessEngineConfiguration().getProcessEngine().getCaseService().manuallyStartCaseExecution("PlanItem_1ba2n47");

    System.out.println("\n\n\n######\n\n\n");
    System.out.println("AGE IS: '" + (Integer) execution.getVariable("age") + "'");
    System.out.println("BMI IS: '" + (Double) execution.getVariable("bmi") + "'");
    System.out.println("\n\n\n######\n\n\n");
  }

  private Integer calculateAge(String birthDay){
    Calendar currentDate = Calendar.getInstance();
    Integer age = 0;
    if (birthDay!=null) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calBirthDay = Calendar.getInstance();
        calBirthDay.setTime(sdf.parse(birthDay));

        age = currentDate.get(Calendar.YEAR) - calBirthDay.get(Calendar.YEAR);
        if (calBirthDay.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH) || (calBirthDay.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) && calBirthDay.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH))) {
          age--;
        }
      } catch (java.text.ParseException e){
        //keep age 0, no handling implemented
      }
    }

    return age;
  }

  private Double calculateBMI(Integer heigth, Integer weight){
    Double bmi = 0D;
    if (heigth!=null && weight!=null){
      bmi = weight/Math.pow((heigth/100),2);
    }
    return bmi;
  }

}
