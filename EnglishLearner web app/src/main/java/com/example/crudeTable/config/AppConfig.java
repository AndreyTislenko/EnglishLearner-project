package com.example.crudeTable.config;

import com.example.crudeTable.model.table.Table;
import com.example.crudeTable.model.table.TableReader;
import com.example.crudeTable.model.test.Questions;
import com.example.crudeTable.model.test.UserResult;
import com.example.crudeTable.model.table.Word;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@Configuration
public class AppConfig {

    @Bean
    public TableReader tableReader() {
        return new TableReader();
    }

    @Bean
    public Table table() {
        return new Table();
    }

    @Bean
    public ArrayList<Word> records() {
        return new ArrayList<>();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ArrayList<UserResult> sessionScopedUserResults() {
        return new ArrayList<>();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Questions questions() {
        return new Questions();
    }

    @Bean
    public ArrayList<ArrayList<UserResult>> sessionCollection() {
        return new ArrayList<>();
    }
}