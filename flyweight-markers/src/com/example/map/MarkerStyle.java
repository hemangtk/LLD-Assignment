package com.example.map;

/**
 * Immutable Flyweight — shared intrinsic state for map markers.
 * All fields are final, no setters. Instances are reused via MarkerStyleFactory.
 */
public final class MarkerStyle {

    private final String shape;
    private final String color;
    private final int size;
    private final boolean filled;

    MarkerStyle(String shape, String color, int size, boolean filled) {
        this.shape = shape;
        this.color = color;
        this.size = size;
        this.filled = filled;
    }

    public String getShape() { return shape; }
    public String getColor() { return color; }
    public int getSize() { return size; }
    public boolean isFilled() { return filled; }

    @Override
    public String toString() {
        return shape + "|" + color + "|" + size + "|" + (filled ? "F" : "O");
    }
}
