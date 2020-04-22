package com.example.ingredieat.base;

public enum Category {
    // category name, category value
    MEAT("Meat"),
    PRODUCE("Produce"),
    SEAFOOD("Seafood"),
    MILK_EGGS_OTHER_DAIRY("Milk, Eggs, Other Dairy"),
    BAKING("Baking"),
    BEVERAGE("Beverages"),
    OIL_VINEGAR_SALAD_DRESSING("Oil, Vinegar, Salad Dressing"),
    SPICES_AND_SEASONINGS("Spices and Seasonings"),
    ALL("all");

    private String categoryValue;

    Category(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public static Category getCategoryName(String categoryValue){
        for (Category s : Category.values()) {
            if(categoryValue.equals(s.getCategoryValue())){
                return s;
            }
        }
        return null;
    }
}
