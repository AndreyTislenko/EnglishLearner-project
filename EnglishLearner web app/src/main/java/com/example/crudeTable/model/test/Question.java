package com.example.crudeTable.model.test;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String word;
    private List<String> translations;

    public Question() {}
    public Question(String word, List<String> translations) {
        this.word = word;
        this.translations = translations;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public List<String> getTranslations() {
        return translations;
    }
    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "Question{" +
                "word='" + word + '\'' +
                ", translations=" + translations.toString() +
                '}';
    }

    @Override
    protected Question clone() throws CloneNotSupportedException {
        return new Question(word, new ArrayList<String>(translations));
    }
}
