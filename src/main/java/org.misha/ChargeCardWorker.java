package org.misha;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.logging.Logger;

public class ChargeCardWorker {
    private final static Logger LOGGER = Logger.getLogger(ChargeCardWorker.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                                                      .baseUrl("http://localhost:8080/engine-rest")
                                                      .asyncResponseTimeout(2) // long polling timeout
                                                      .build();
        // subscribe to an external task topic as specified in the process
        client.subscribe("charge-card")
              .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
              .handler((ExternalTask externalTask, ExternalTaskService externalTaskService) -> {
                  // Put your business logic here
                  // Get a process variable
                  String item = (String) externalTask.getVariable("item");
                  Long amount = (Long) externalTask.getVariable("amount");
                  LOGGER.info("\n\n\n" + externalTask.getAllVariables());
                  LOGGER.info("Charging credit card with an amount of '" + amount + "'$ for the item '" + item + "'...");
                  // Complete the task

                  externalTaskService.complete(externalTask);

              })
              .open();
    }
}