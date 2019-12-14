package com.evasler.dokkanbase.queryresponseobjects;

public class related_card_details {

    private String card_id;
    private String type;
    private String rarity;
    private int dokkan_awakening_medal_combination_id;

    public String getCard_id() {
        return card_id;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }

    public int getDokkan_awakening_medal_combination_id() {
        return dokkan_awakening_medal_combination_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setDokkan_awakening_medal_combination_id(int medal_combination) {
        this.dokkan_awakening_medal_combination_id = medal_combination;
    }
}
