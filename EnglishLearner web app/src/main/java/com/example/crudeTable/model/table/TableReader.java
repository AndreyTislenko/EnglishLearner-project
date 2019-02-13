package com.example.crudeTable.model.table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class TableReader {

    private String stringPathToTable = "table.json";

    public TableReader() {}

    //synchronization is crucial when there is concurrent table updating.
    public synchronized void saveTableInJson(Table table) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        /*
         * I don't know why but this line below doesn't work...
         * The strange thing is it was working fine, but just in one moment stopped.
         * I checked everything, I even reverted the done work to the initial state.
         * Nothing helped.
         */
        //gson.toJson(table, new FileWriter(stringPathToTable));

        String jsonTable = gson.toJson(table);
        FileWriter fileWriter = new FileWriter(stringPathToTable);
        fileWriter.write(jsonTable);
        fileWriter.close();
    }

    public Table readJsonTable() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(stringPathToTable), Table.class);
    }
}