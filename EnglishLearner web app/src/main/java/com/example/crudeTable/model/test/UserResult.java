package com.example.crudeTable.model.test;

import java.util.Objects;

public class UserResult {
    private String word;
    private String answer;

    public UserResult() {}
    public UserResult(String word, String answer) {
        this.word = word;
        this.answer = answer;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "word='" + word + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResult that = (UserResult) o;
        return word.equals(that.word) &&
                answer.equals(that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, answer);
    }
}
