package com.example.recodedharran.translate.models;

/**
 * Created by Recodedharran on 13.1.2018.
 */

public class Translate {
    private String select;
    private int id;
    private String definition;
    private String synonyms;

    public Translate() {
    }

    public Translate(String select, int id, String definition, String synonyms) {
        this.select = select;
        this.id = id;
        this.definition = definition;
        this.synonyms = synonyms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Translate(String select, String definition, String synonyms) {
        this.select = select;
        this.definition = definition;
        this.synonyms = synonyms;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }
}
