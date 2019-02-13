package com.example.crudeTable.model.table;

import java.util.Calendar;

public class Word {
    private Calendar date;
    private String word;
    private String translation;
    private String examplesOfUsage;
    private String definition;

    public Word() {}
    public Word(Calendar date, String word, String translation, String examplesOfUsage, String definition) {
        this.date = date;
        this.word = word;
        this.translation = translation;
        this.examplesOfUsage = examplesOfUsage;
        this.definition = definition;
    }

    public Calendar getDate() { return date; }
    public void setDate(Calendar date) {
        this.date = date;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    public String getExamplesOfUsage() {
        return examplesOfUsage;
    }
    public void setExamplesOfUsage(String examplesOfUsage) {
        this.examplesOfUsage = examplesOfUsage;
    }
    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
