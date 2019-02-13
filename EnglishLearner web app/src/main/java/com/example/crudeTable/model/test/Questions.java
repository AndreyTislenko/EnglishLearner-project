package com.example.crudeTable.model.test;

import com.example.crudeTable.model.table.TableReader;
import com.example.crudeTable.model.table.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Questions {
    private ArrayList<String> randomListOfWords;
    private ArrayList<Question> questions;

    public Questions() { }

    public ArrayList<String> getRandomListOfWords() { return randomListOfWords; }
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setRandomListOfWords(String path) {
        randomListOfWords = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                randomListOfWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setQuestions(int amountOfQuestions) {
        questions = new ArrayList<>();
        TableReader tableReader = new TableReader();
        try {
            ArrayList<Word> records = tableReader.readJsonTable().getRecords();

            ArrayList<Integer> indicesOfWords = createIndicesList(records.size());

            for(int i = 0; i < amountOfQuestions; i++ ) {
                //This index is not repeated, because of remove() method.
                int randomIndex = indicesOfWords.remove((int)(Math.random()*indicesOfWords.size()));
                Word word = records.get(randomIndex);

                ArrayList<String> translations = new ArrayList<>();
                ArrayList<Integer> indicesOfRandomList = createIndicesList(randomListOfWords.size());
                //5 is amount of variants per question. 1st one is the actual translation.
                translations.add(word.getTranslation());
                for(int j = 0; j < 4; j++) {
                    //The index of random word from randomListOfWords also is not repeated.
                    translations.add(randomListOfWords.get(indicesOfRandomList.remove((int)(Math.random()*indicesOfRandomList.size()))));
                }

                questions.add(new Question(word.getWord(), translations));
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> createIndicesList(int amount) {
        ArrayList<Integer> indices = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            indices.add(i);
        }
        return indices;
    }

    public static ArrayList<Question> shuffleTranslations(Questions questions) {
        ArrayList<Question> copyOfQuestions = new ArrayList<>();

        try {
            for (Question question : questions.getQuestions()) {
                copyOfQuestions.add(question.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        for(Question question: copyOfQuestions) {
            Collections.shuffle(question.getTranslations());
        }
        return copyOfQuestions;
    }

    public static boolean[] checkUserResult(Questions questions, ArrayList<UserResult> sessionScopedUserResults) {
        boolean[] isRight = new boolean[sessionScopedUserResults.size()];
        for(int i = 0; i <sessionScopedUserResults.size(); i++) {
            String theWord = sessionScopedUserResults.get(i).getWord();

            String userAnswer = sessionScopedUserResults.get(i).getAnswer();
            String rightAnswer = null;
            for(Question question: questions.getQuestions()) {
                if(theWord.equals(question.getWord())) {
                    rightAnswer = question.getTranslations().get(0);
                }
            }

            isRight[i] = userAnswer.equals(rightAnswer);
        }

        //System.out.println(sessionScopedUserResults);
        return isRight;
    }

}
