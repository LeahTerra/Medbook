////////////////////////////////////////////////////////////////////////
//
//  Name: Leah Sheptycki
//  Class: CMPUT 301 Section D01
//  SID: 1547619
//
////////////////////////////////////////////////////////////////////////

package com.example.medbook;

import java.util.ArrayList;
import java.util.Date;

/*
    Medication Class
    \- Medication(dateStart, name, dose, doseUnit, frequency)
    \- getInfo(): getter for medication information
    \- getFrequency(): getter for frequency specific for use in totalDailyDoses
    \- setInfo(): setter for medication information

    The general idea for this class was to create a distinct object to hold all the attributes
    of a specific medication while still providing encapsulation with getters and setters.

 */
public class Medication {
    private String dateStart;
    private String name;
    private Integer dose;
    private String doseUnit;
    private Integer frequency;

    public Medication(String dateStart, String name, Integer dose, String doseUnit,
                      Integer frequency) {
        this.dateStart = dateStart;
        this.name = name;
        this.dose = dose;
        this.doseUnit = doseUnit;
        this.frequency = frequency;
    }
    // Returns all info in a object as a simple collective getter
    public ArrayList<String> getInfo() {
        ArrayList<String> infoArray = new ArrayList<>();
        infoArray.add(String.valueOf(this.dateStart));
        infoArray.add(this.name);
        infoArray.add(String.valueOf(this.dose));
        infoArray.add(this.doseUnit);
        infoArray.add(String.valueOf(this.frequency));
        return infoArray;
    }
    public Integer getFrequency() {
        return this.frequency;
    }
    public void setInfo(String dateStart, String name, Integer dose, String doseUnit,
                        Integer frequency) {
        this.dateStart = dateStart;
        this.name = name;
        this.dose = dose;
        this.doseUnit = doseUnit;
        this.frequency = frequency;
    }
}
