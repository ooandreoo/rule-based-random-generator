package com.rbrg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ApproachOne {
    private ArrayList<String> domain;
    private Integer maxContinuousSelection;
    private Integer penaltyTime;
    private Integer lastValueIndex;
    private Integer repeatedTimes;
    private Queue<String> waitingValues;
    private Queue<Integer> valuesPenalties;
    private Random randomizer;
    private Integer turn;

    public ApproachOne(){}

    public void initialize(ArrayList<String> domain, Integer maxContinuousSelection, Integer penaltyTime) {
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
        this.valuesPenalties = new LinkedList<>();
        this.turn = 1;
    }

    public String getRandomValue() {
        System.out.println(String.format("Turn %s",this.turn));
        System.out.println(String.format("Domain: %s",this.domain.toString()));
        System.out.println(String.format("Waiting Values: %s", this.waitingValues.toString()));
        System.out.println(String.format("Penalty Times:  %s", this.valuesPenalties.toString()));

        // get random value
        Integer index = this.randomizer.nextInt(this.domain.size());
        String value = this.domain.get(index);

        System.out.println(String.format("Random Value: %s",value));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        // handle repetitions
        if(index.equals(this.lastValueIndex))
            this.repeatedTimes++;
        else{
            this.lastValueIndex = index;
            this.repeatedTimes = 1;
        }

        // send repeated value to waiting list if requirements are met
        if(this.repeatedTimes.equals(this.maxContinuousSelection)){
            this.waitingValues.add(value);
            this.valuesPenalties.add(this.turn+this.penaltyTime);
            this.domain.remove((int)index);
        }

        // incorporate waiting value with penalty time already enforced
        if(!valuesPenalties.isEmpty() && valuesPenalties.peek().equals(this.turn)){
            this.domain.add(waitingValues.poll());
            valuesPenalties.poll();
        }

        // increase turn
        this.turn++;
        
        return value;
    }


}
