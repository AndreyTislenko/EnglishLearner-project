package com.example.crudeTable.controller;

import com.example.crudeTable.model.table.Table;
import com.example.crudeTable.model.table.TableReader;
import com.example.crudeTable.model.test.Question;
import com.example.crudeTable.model.test.Questions;
import com.example.crudeTable.model.test.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;


@RestController
class RestWebController {

    @Autowired
    private TableReader tableReader;

    @Autowired
    private  Table table;

    //session scoped.
    @Resource(name = "sessionScopedUserResults")
    private ArrayList<UserResult> sessionScopedUserResults;

    //session scoped.
    @Resource(name = "questions")
    private Questions questions;


    @GetMapping("/table")
    public Table getTable() {
        try {
            table = tableReader.readJsonTable();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    @GetMapping("/default")
    public Table getDefaultTable() {
        try {
            table = Table.createDefaultTable();
            tableReader.saveTableInJson(table);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    @PostMapping("/save")
    public int saveTable(@RequestBody Table table){
        //System.out.println(Arrays.toString(table.getRecords().get(0)));
        try {
            tableReader.saveTableInJson(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 200;
    }

    @GetMapping("/questions")
    public ArrayList<Question> getQuestions() {
        sessionScopedUserResults.clear();
        questions.setRandomListOfWords("listOfRandomWords.txt");
        questions.setQuestions(5);
        return Questions.shuffleTranslations(questions);
    }

    @GetMapping("notShuffledQuestions")
    public ArrayList<Question> getNotShuffledQuestions() {
        return questions.getQuestions();
    }

    @PostMapping("/userResult")
    public int postUserResult(@RequestBody ArrayList<UserResult> results) {
        sessionScopedUserResults.clear();
        sessionScopedUserResults.addAll(results);

        return 200;
    }

    @GetMapping("/userResult")
    public boolean[] getUserResult() {
        return Questions.checkUserResult(questions, sessionScopedUserResults);
    }

    @GetMapping("/getSessionScopedUserResults")
    public ArrayList<UserResult> getSessionScopedUserResults() {
        return sessionScopedUserResults;
    }
}