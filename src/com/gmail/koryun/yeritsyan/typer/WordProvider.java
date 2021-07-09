package com.gmail.koryun.yeritsyan.typer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WordProvider {
    final static String PATH = "resources\\words.txt";
    private static List<String> words;

    public List<String> getWords() {
        if(words==null){
            try {
                words = Files.readAllLines(Paths.get(PATH));
            } catch (IOException e) {
                System.out.println("Something got Wrong");
            }
        }
        return words;
    }

}
