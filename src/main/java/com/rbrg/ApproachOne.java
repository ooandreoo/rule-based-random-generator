package com.rbrg;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ApproachOne {
    private static final Logger logger = LogManager.getLogger(ApproachOne.class);

    // User provided variables
    //      penaltyTime - maxContinuousSelection

    private ArrayList<String> domain;
    private Integer maxContinuousSelection;
    private Integer penaltyTime;

    // Internal variables

    //      Internal variables for identifying repeated values
    private Integer lastValueIndex;
    private Integer repeatedTimes;

    //      Internal variables handling the repeated values that were temporarily separated from domain
    private Queue<String> waitingValues;
    private Queue<Integer> waitingValuesComebackTurn;

    //      Random instance used to generate random values :)
    private Random randomizer;

    //      Variable used as a time reference for the assignment of penalties to repeated values
    private Integer turn;

    // Domain length, max continuous selection of same value and penalty time need to fullfill a
    // specific criteria in order to avoid a scenario in which there are no available items within
    // the domain to choose from because they are all penalized (check the README file for an explanation)
    public ApproachOne(ArrayList<String> domain, Integer maxContinuousSelection, Integer penaltyTime) {
        if(domain.size() <= penaltyTime/maxContinuousSelection)
            throw new RuntimeException(String.format("At turn %s there won't be domain values available due to penalties, please choose values that fullfill this criteria: (domain length) > (penalty time + max repetitions allowed - 1) \\ (max repetitions allowed)",penaltyTime));
        this.domain = domain;
        this.maxContinuousSelection = maxContinuousSelection;
        this.penaltyTime = penaltyTime;
        this.reset();
    }

    public void reset(){
        this.randomizer = new Random();
        this.lastValueIndex = null;
        this.repeatedTimes = 0;
        this.waitingValues = new LinkedList<>();
        this.waitingValuesComebackTurn = new LinkedList<>();
        this.turn = 1;
    }

    private Integer getRandomIndexForDomain() {
        return this.randomizer.nextInt(this.domain.size());
    }

    private String getValueFromDomain(Integer index) {
        return this.domain.get(index);
    }

    private Integer getLastValueIndex() {
        return this.lastValueIndex;
    }

    private void increaseRepeatedTimes() {
        this.repeatedTimes++;
    }

    private void resetRepeatedTimes() {
        this.repeatedTimes = 1;
    }

    private void setLastValueIndex(Integer index) {
        this.lastValueIndex = index;
    }

    private void incorporateValuesWithPenaltyTimeExpired(){
        if(!waitingValuesComebackTurn.isEmpty() && waitingValuesComebackTurn.peek().equals(this.turn)){
            this.domain.add(waitingValues.poll());
            waitingValuesComebackTurn.poll();
        }
    }

    private Boolean repeatedValueMetPenaltyCriteria() {
        return this.repeatedTimes.equals(this.maxContinuousSelection);
    }

    private void sendRepeatedValueToWaitingList() {
        this.waitingValues.add(getValueFromDomain(getLastValueIndex()));
        this.waitingValuesComebackTurn.add(this.turn+this.penaltyTime);
        this.domain.remove((int)getLastValueIndex());
    }

    private String getRandomValueFromDomain() {
        // get random value
        Integer index = getRandomIndexForDomain();
        String randomValue = getValueFromDomain(index);

        logger.info(String.format("Random Value: %s",randomValue));
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        // handle repetitions
        if(index.equals(getLastValueIndex()))
            increaseRepeatedTimes();
        else{
            setLastValueIndex(index);
            resetRepeatedTimes();
        }

        // send repeated value to waiting list if requirements are met

        if(repeatedValueMetPenaltyCriteria()){
            sendRepeatedValueToWaitingList();
        }
        return randomValue;
    }

    private void increaseTurn(){
        this.turn++;
    }

    public String getRandomValue() {
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info(String.format("Turn %s",this.turn));
        logger.info(String.format("Domain: %s",this.domain.toString()));
        logger.info(String.format("Waiting Values: %s", this.waitingValues.toString()));
        logger.info(String.format("Penalty Times:  %s", this.waitingValuesComebackTurn.toString()));

        // get random value
        String randomValue = getRandomValueFromDomain();

        // incorporate waiting value with penalty time already enforced
        incorporateValuesWithPenaltyTimeExpired();

        // increase turn
        increaseTurn();
        
        return randomValue;
    }


}
