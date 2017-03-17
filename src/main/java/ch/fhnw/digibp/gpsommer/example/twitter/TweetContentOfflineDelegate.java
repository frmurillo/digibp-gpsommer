package ch.fhnw.digibp.gpsommer.example.twitter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("tweetAdapter")
public class TweetContentOfflineDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    String content = (String) execution.getVariable("content");

    System.out.println("\n\n\n######\n\n\n");
    System.out.println("NOW WE WOULD TWITTER: '" + content + "'");
    System.out.println("\n\n\n######\n\n\n");
  }

}
