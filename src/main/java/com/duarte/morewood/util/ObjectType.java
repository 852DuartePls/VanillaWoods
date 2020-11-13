package com.duarte.morewood.util;

public enum ObjectType {
    BARREL("barrel"),
    BOOKSHELF("bookshelf"),
    CHEST("chest"),
    LADDER("ladder");

    private final String name;

    ObjectType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
