package com.example.jamesbrowning.countlesspies;

public class Player {

    private int _id;
    private String _playername;
    private int _score;

    public Player() {
    }

    public Player(String playername, int score) {
        set_playername(playername);
        set_score(score);
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_playername(String playername) {
        this._playername = playername;
    }

    public void set_score(int score) {
        this._score = score;
    }

    public int get_id() {
        return _id;
    }

    public String get_playername() {
        return _playername;
    }

    public int get_score() {
        return _score;
    }
}
