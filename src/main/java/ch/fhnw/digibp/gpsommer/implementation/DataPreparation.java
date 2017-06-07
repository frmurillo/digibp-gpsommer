package ch.fhnw.digibp.gpsommer.implementation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Named("dataPreparationAdapter")
public class DataPreparation implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    Integer height = (Integer) execution.getVariable("requesterHeight");
    Integer weight = (Integer) execution.getVariable("requesterWeight");
    String dateOfBirthString = (String) execution.getVariable("dateOfBirth");
    String gender = (String) execution.getVariable("requesterGender");

    Double bmi = calculateBMI(height, weight);
    Integer age = calculateAge(dateOfBirthString);
    String salutation = getSalutation(gender);
    String businessKey = execution.getProcessBusinessKey();

    execution.setVariable("bmi",bmi);
    execution.setVariable("age",age);
    execution.setVariable("salutation",salutation);
    execution.setVariable("businessKey",businessKey);
  }

  private String getSalutation(String gender){
    String salutation = "Mr. / Mrs.";

    if (gender.compareTo("male") == 0){
      salutation = "Mr.";
    } else if (gender.compareTo("female") == 0){
      salutation = "Mrs.";
    }

    return salutation;
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
