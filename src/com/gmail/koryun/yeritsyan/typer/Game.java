package com.gmail.koryun.yeritsyan.typer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class Game {
    private final String OPTIONS_PATH = "resources\\options.txt";
    private final Timer timer = new Timer();
    static TopList topList = new TopList();

    private int currentScore;
    private List<String> words = new WordProvider().getWords();

    private long gameTime;
    private long startTime ;

    private boolean isRunning = true;

    public Game() {
        readSetting();
    }


    public void readSetting() {
        try {
            this.gameTime = Long.parseLong(Files.readAllLines(Paths.get(OPTIONS_PATH)).get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSetting(long gameTime) {
        try {
            Files.write(Paths.get(OPTIONS_PATH), String.valueOf(gameTime).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        readSetting();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void startGame() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String currentWord;
        Random random = new Random();

        this.startTime= System.currentTimeMillis();

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopGame(bufferedReader);
            }
        }, this.gameTime);

        while (isRunning) {
            currentWord = words.get(random.nextInt(words.size()));
            System.out.println(currentWord);

            if (bufferedReader.readLine().equals(currentWord)) {
                this.currentScore++;
            } else this.currentScore--;

            int currentTime = (int) (this.gameTime - elapsedTime()) / 1000;
            if (currentTime > 0)
                System.out.println("Time Left " + currentTime + " Seconds");
        }

        System.out.println("Your Score:" + this.currentScore);

        if (topList.checkScore(this.currentScore)) {
            System.out.println("Enter your Nick name");
            String name = bufferedReader.readLine();
            topList.add(currentScore, name);
        }
    }

    private void stopGame(BufferedReader bufferedReader) {
        this.isRunning = false;
        try {
            bufferedReader.wait();
            System.out.println();
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Gamer Over.Type anything to score.");
        }
        this.timer.cancel();
    }
}
