package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;

public class ParserHandler implements FollowMeParserHandler {
    private boolean isParsing;

    public ParserHandler() {
        this.isParsing = false;
    }

    /**
     * Flag per mostrare se si sta effettuando il parsing di un programma
     * @return true se il parsing è in corso, false se non lo è
     */
    public boolean isParsing() {
        return isParsing;
    }

    public void setParsing(boolean parsing) {
        isParsing = parsing;
    }

    @Override
    public void parsingStarted() {
        isParsing = true;
    }

    @Override
    public void parsingDone() {
        isParsing = false;
    }

    @Override
    public void moveCommand(double[] args) {

    }

    @Override
    public void moveRandomCommand(double[] args) {

    }

    @Override
    public void signalCommand(String label) {

    }

    @Override
    public void unsignalCommand(String label) {

    }

    @Override
    public void followCommand(String label, double[] args) {

    }

    @Override
    public void stopCommand() {

    }

    @Override
    public void waitCommand(int s) {

    }

    @Override
    public void repeatCommandStart(int n) {

    }

    @Override
    public void untilCommandStart(String label) {

    }

    @Override
    public void doForeverStart() {

    }

    @Override
    public void doneCommand() {

    }
}
