package com.example.toutis.fypproject;


/**
 * Created by Alexandre on 09-Apr-18.
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

public class FirstController {

    private double grade;
    private double difficulty;
    private double available;
    private double hoursToStudy;

    public FirstController(double grade, double difficulty, double available) {
        this.grade = grade;
        this.difficulty = difficulty;
        this.available = available;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public double getHoursToStudy() {
        return hoursToStudy;
    }

    public void setHoursToStudy(double hoursToStudy) {
        this.hoursToStudy = hoursToStudy;
    }

    public double getOutput(){
        Engine engine = new Engine();
        engine.setName("FirstController");
        engine.setDescription("");

        InputVariable targetGrade = new InputVariable();
        targetGrade.setName("targetGrade");
        targetGrade.setDescription("");
        targetGrade.setEnabled(true);
        targetGrade.setRange(60.0, 95.0);
        targetGrade.setLockValueInRange(false);
        targetGrade.addTerm(new Gaussian("poor", 60.000, 4.956));
        targetGrade.addTerm(new Gaussian("average", 71.67, 4.956));
        targetGrade.addTerm(new Gaussian("good", 83.33, 4.956));
        targetGrade.addTerm(new Gaussian("excellent", 95, 4.956));
        engine.addInputVariable(targetGrade);

        InputVariable difficulty = new InputVariable();
        difficulty.setName("difficulty");
        difficulty.setDescription("");
        difficulty.setEnabled(true);
        difficulty.setRange(0.0, 100.0);
        difficulty.setLockValueInRange(false);
        difficulty.addTerm(new Gaussian("easy", 0.0, 21.23));
        difficulty.addTerm(new Gaussian("medium", 50.0, 21.23));
        difficulty.addTerm(new Gaussian("hard", 100.0, 21.23));
        engine.addInputVariable(difficulty);

        InputVariable timeAvailable = new InputVariable();
        timeAvailable.setName("timeAvailable");
        timeAvailable.setDescription("");
        timeAvailable.setEnabled(true);
        timeAvailable.setRange(3.0, 9.0);
        timeAvailable.setLockValueInRange(false);
        timeAvailable.addTerm(new Gaussian("few", 3.0, 1.274));
        timeAvailable.addTerm(new Gaussian("enough", 6.0, 1.274));
        timeAvailable.addTerm(new Gaussian("significant", 9.0, 1.274));
        engine.addInputVariable(timeAvailable);

        OutputVariable studyHours = new OutputVariable();
        studyHours.setName("studyHours");
        studyHours.setDescription("");
        studyHours.setEnabled(true);
        studyHours.setRange(3.0, 9.0);
        studyHours.setLockValueInRange(false);
        studyHours.setAggregation(new Maximum());
        studyHours.setDefuzzifier(new Centroid(100));
        studyHours.setDefaultValue(Double.NaN);
        studyHours.setLockPreviousValue(false);
        studyHours.addTerm(new Gaussian("veryFew", 3.0, 0.6372));
        studyHours.addTerm(new Gaussian("few", 4.5, 0.6372));
        studyHours.addTerm(new Gaussian("enough", 6.0, 0.6372));
        studyHours.addTerm(new Gaussian("much", 7.5, 0.6372));
        studyHours.addTerm(new Gaussian("significant", 9.0, 0.6372));
        engine.addOutputVariable(studyHours);

        RuleBlock mamdani = new RuleBlock();
        mamdani.setName("mamdani");
        mamdani.setDescription("");
        mamdani.setEnabled(true);
        mamdani.setConjunction(new Minimum());
        mamdani.setDisjunction(new Maximum());
        mamdani.setImplication(new Minimum());
        mamdani.setActivation(new General());
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is easy and timeAvailable is few then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is easy and timeAvailable is enough then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is easy and timeAvailable is significant then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is medium and timeAvailable is few then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is medium and timeAvailable is enough then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is medium and timeAvailable is significant then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is hard and timeAvailable is few then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is hard and timeAvailable is enough then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is poor and difficulty is hard and timeAvailable is significant then studyHours is few", engine));
        //10
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is easy and timeAvailable is few then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is easy and timeAvailable is enough then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is easy and timeAvailable is significant then studyHours is veryFew", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is medium and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is medium and timeAvailable is enough then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is medium and timeAvailable is significant then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is hard and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is hard and timeAvailable is enough then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is average and difficulty is hard and timeAvailable is significant then studyHours is enough", engine));
        //19
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is easy and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is easy and timeAvailable is enough then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is easy and timeAvailable is significant then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is medium and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is medium and timeAvailable is enough then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is medium and timeAvailable is significant then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is hard and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is hard and timeAvailable is enough then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is good and difficulty is hard and timeAvailable is significant then studyHours is much", engine));
        //28
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is easy and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is easy and timeAvailable is enough then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is easy and timeAvailable is significant then studyHours is much", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is medium and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is medium and timeAvailable is enough then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is medium and timeAvailable is significant then studyHours is significant", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is hard and timeAvailable is few then studyHours is few", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is hard and timeAvailable is enough then studyHours is enough", engine));
        mamdani.addRule(Rule.parse("if targetGrade is excellent and difficulty is hard and timeAvailable is significant then studyHours is significant", engine));
        engine.addRuleBlock(mamdani);

        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status))
            throw new RuntimeException("[engine error] engine is not ready:n" + status);

        InputVariable grade2 = engine.getInputVariable("targetGrade");
        InputVariable diff2 = engine.getInputVariable("difficulty");
        InputVariable available2 = engine.getInputVariable("timeAvailable");
        OutputVariable hours = engine.getOutputVariable("studyHours");

        //Set value to user input
        setGrade(grade2.getMaximum());
        grade2.setValue(this.grade);
        //Set value to user input
        setDifficulty(diff2.getMaximum());
        diff2.setValue(this.difficulty);
        //Set value to user input
        setAvailable(available2.getMaximum());
        available2.setValue(this.available);
        engine.process();
        FuzzyLite.logger().info(
                String.format("grade2.input = %s -> diff2.input = %s -> available2.input = %s -> hours.output = %s", Op.str(this.grade), Op.str(this.difficulty),Op.str(this.available), Op.str(hours.getValue())));
        setHoursToStudy(hours.getValue());

        return hoursToStudy;
    }
    public static void main(String[] args) {
        FirstController cont = new FirstController(0,0,0);
        Log.d("grade value", String.valueOf(cont.getOutput()));
    }
}
