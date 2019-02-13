package com.example.crudeTable.model.table;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Table {
    private String[] columnNames = {"date", "word", "translation", "examples of usage", "definition"};

    @Autowired
    private ArrayList<Word> records;

    public Table() {}
    public Table(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnNames() {
        return columnNames;
    }
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
    public ArrayList<Word> getRecords() {
        return records;
    }
    public void setRecords(ArrayList<Word> records) {
        this.records = records;
    }

    public static Table createDefaultTable() {
        Table table = new Table();
        table.getRecords().add(new Word(new GregorianCalendar(2018, Calendar.DECEMBER,1), "jocular", "шутливый", "\"she sounded in a jocular mood\"", "fond of or characterized by joking; humorous or playful"));
        table.getRecords().add(new Word(new GregorianCalendar(2018, Calendar.DECEMBER,2), "retaliate", "мстить", "\"the blow stung and she retaliated immediately\"", "make an attack or assault in return for a similar attack"));
        table.getRecords().add(new Word(new GregorianCalendar(2018, Calendar.DECEMBER,3), "cranky", "капризный", "\"he was bored and cranky after eight hours of working\"", "ill-tempered; irritable"));
        return table;
    }
}
