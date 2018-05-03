package com.example.toutis.fypproject;

/**
 * Created by Alexandre on 16-Apr-18.
 */

import android.util.Log;

import com.fuzzylite.*;
import com.fuzzylite.activation.*;
import com.fuzzylite.defuzzifier.*;
import com.fuzzylite.factory.*;
import com.fuzzylite.hedge.*;
import com.fuzzylite.imex.*;
import com.fuzzylite.norm.*;
import com.fuzzylite.norm.s.*;
import com.fuzzylite.norm.t.*;
import com.fuzzylite.rule.*;
import com.fuzzylite.term.*;
import com.fuzzylite.variable.*;
import java.util.Scanner;

public class SecondController {

    private FirstController first;
    private double dueDate;
    private double weight;
    private double hoursFirst;

    public FirstController getFirst() {
        return first;
    }

    public void setFirst(FirstController first) {
        this.first = first;
    }

    public double getDueDate() {
        return dueDate;
    }

    public void setDueDate(double dueDate) {
        this.dueDate = dueDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHoursFirst() {
        return hoursFirst;
    }

    public void setHoursFirst(double hoursFirst) {
        this.hoursFirst = hoursFirst;
    }

    public double getAdjustedHours(){
        Engine engine = new Engine();
        engine.setName("SecondController");
        engine.setDescription("");

        InputVariable dueTime = new InputVariable();
        dueTime.setName("dueTime");
        dueTime.setDescription("");
        dueTime.setEnabled(true);
        dueTime.setRange(60.0, 95.0);
        dueTime.setLockValueInRange(false);
        dueTime.addTerm(new Gaussian("verySoon", 60.000, 4.956));
        dueTime.addTerm(new Gaussian("soon", 71.67, 4.956));
        dueTime.addTerm(new Gaussian("far", 83.33, 4.956));
        engine.addInputVariable(dueTime);

        InputVariable weightOfTask = new InputVariable();
        weightOfTask.setName("weightOfTask");
        weightOfTask.setDescription("");
        weightOfTask.setEnabled(true);
        weightOfTask.setRange(0.0, 100.0);
        weightOfTask.setLockValueInRange(false);
        weightOfTask.addTerm(new Gaussian("low", 0.0, 21.23));
        weightOfTask.addTerm(new Gaussian("medium", 50.0, 21.23));
        weightOfTask.addTerm(new Gaussian("high", 100.0, 21.23));
        engine.addInputVariable(weightOfTask);

        InputVariable inputOutput = new InputVariable();
        inputOutput.setName("inputOutput");
        inputOutput.setDescription("");
        inputOutput.setEnabled(true);
        inputOutput.setRange(3.0, 9.0);
        inputOutput.setLockValueInRange(false);
        inputOutput.addTerm(new Gaussian("veryFew", 3.0, 1.274));
        inputOutput.addTerm(new Gaussian("few", 6.0, 1.274));
        inputOutput.addTerm(new Gaussian("enough", 9.0, 1.274));
        inputOutput.addTerm(new Gaussian("much", 6.0, 1.274));
        inputOutput.addTerm(new Gaussian("significant", 9.0, 1.274));
        engine.addInputVariable(inputOutput);

        OutputVariable adjustedHours = new OutputVariable();
        adjustedHours.setName("adjustedHours");
        adjustedHours.setDescription("");
        adjustedHours.setEnabled(true);
        adjustedHours.setRange(3.0, 9.0);
        adjustedHours.setLockValueInRange(false);
        adjustedHours.setAggregation(new Maximum());
        adjustedHours.setDefuzzifier(new Centroid(100));
        adjustedHours.setDefaultValue(Double.NaN);
        adjustedHours.setLockPreviousValue(false);
        adjustedHours.addTerm(new Gaussian("veryFew", 3.0, 0.6372));
        adjustedHours.addTerm(new Gaussian("few", 4.5, 0.6372));
        adjustedHours.addTerm(new Gaussian("enough", 6.0, 0.6372));
        adjustedHours.addTerm(new Gaussian("much", 7.5, 0.6372));
        adjustedHours.addTerm(new Gaussian("significant", 9.0, 0.6372));
        engine.addOutputVariable(adjustedHours);

        RuleBlock mamdani = new RuleBlock();
        mamdani.setName("mamdani");
        mamdani.setDescription("");
        mamdani.setEnabled(true);
        mamdani.setConjunction(new Minimum());
        mamdani.setDisjunction(new Maximum());
        mamdani.setImplication(new Minimum());
        mamdani.setActivation(new General());
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is low and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is medium and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is high and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is low and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is medium and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is high and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is low and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is medium and inputOutput is veryFew then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is high and inputOutput is veryFew then adjustedHours is few", engine));
        //10
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is low and inputOutput is few then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is medium and inputOutput is few then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is high and inputOutput is few then adjustedHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is low and inputOutput is few then adjustedHours is few", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is medium and inputOutput is few then adjustedHours is few", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is high and inputOutput is few then adjustedHours is few", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is low and inputOutput is few then adjustedHours is few", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is medium and inputOutput is few then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is high and inputOutput is few then adjustedHours is enough", engine));
        //19
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is low and inputOutput is enough then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is medium and inputOutput is enough then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is high and inputOutput is enough then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is low and inputOutput is enough then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is medium and inputOutput is enough then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is high and inputOutput is enough then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is low and inputOutput is enough then adjustedHours is enough", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is medium and inputOutput is enough then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is high and inputOutput is enough then adjustedHours is much", engine));
        //28
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is low and inputOutput is much then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is medium and inputOutput is much then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is high and inputOutput is much then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is low and inputOutput is much then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is medium and inputOutput is much then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is high and inputOutput is much then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is low and inputOutput is much then adjustedHours is much", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is medium and inputOutput is much then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is high and inputOutput is much then adjustedHours is significant", engine));
        //37
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is low and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is medium and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is far and weightOfTask is high and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is low and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is medium and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is soon and weightOfTask is high and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is low and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is medium and inputOutput is significant then adjustedHours is significant", engine));
        mamdani.addRule(Rule.parse("if dueTime is verySoon and weightOfTask is high and inputOutput is significant then adjustedHours is significant", engine));
        engine.addRuleBlock(mamdani);

        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status))
            throw new RuntimeException("[engine error] engine is not ready:n" + status);

        InputVariable dueT = engine.getInputVariable("dueTime");
        InputVariable WOT = engine.getInputVariable("weightOfTask");
        InputVariable IO = engine.getInputVariable("inputOutput");
        OutputVariable adjustedH = engine.getOutputVariable("adjustedHours");

        setDueDate(dueT.getMaximum()); //Add user imput
        dueT.setValue(this.dueDate);
        setWeight(WOT.getMaximum());//Add user input
        WOT.setValue(this.weight);
        setHoursFirst(IO.getMaximum());//Output of controller 1
        IO.setValue(this.hoursFirst);
        engine.process();
        double finalHours = adjustedH.getValue();

        return finalHours;
    }

    public static void main(String[] args) {

    }
}
