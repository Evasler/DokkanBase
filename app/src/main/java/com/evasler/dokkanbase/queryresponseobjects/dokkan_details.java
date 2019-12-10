package com.evasler.dokkanbase.queryresponseobjects;

public class dokkan_details {

    private String dokkan_awakened_card_id;
    private int dokkan_awakening_medal_combination_id;

    public String getDokkan_awakened_card_id() {
        return dokkan_awakened_card_id;
    }

    public int getDokkan_awakening_medal_combination_id() {
        return dokkan_awakening_medal_combination_id;
    }

    public void setDokkan_awakened_card_id(String card_id) {
        this.dokkan_awakened_card_id = card_id;
    }

    public void setDokkan_awakening_medal_combination_id(int medal_combination) {
        this.dokkan_awakening_medal_combination_id = medal_combination;
    }
}
