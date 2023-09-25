package com.example.uocquizgame;

import com.example.uocquizgame.placeholder.PlaceholderContent;

import java.util.ArrayList;

public class GameController {
    private static GameController instance;
    private ArrayList<GameControllerObserver> observers = new ArrayList<>();

    // Variables de instancia
    private int score;
    private int level;

    public UnitType [] unitsPassed = new UnitType[]{UnitType.NOT_ANSWERED,UnitType.NOT_ANSWERED,UnitType.NOT_ANSWERED,UnitType.NOT_ANSWERED,UnitType.NOT_ANSWERED,UnitType.NOT_ANSWERED};

    public enum UnitType{
        NOT_ANSWERED, FAILED, PASSED
    }

    public void changeUnitState(UnitType newState,int quiz){
        unitsPassed[quiz]=newState;
        notifyObservers();
    }

    private GameController() {
        score = 0;
        level = 1;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void updateScore(int points) {
        score += points;
    }

    public void updateLevel() {
        level++;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }


    public void addObserver(GameControllerObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameControllerObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (GameControllerObserver observer : observers) {
            observer.onQuizStateChanged();
        }
    }

    public interface GameControllerObserver{
        public void onQuizStateChanged();
    }
}
