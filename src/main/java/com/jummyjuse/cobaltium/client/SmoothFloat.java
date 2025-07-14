package com.jummyjuse.cobaltium.client;

public class SmoothFloat {
    private float current;
    private float target;
    private final float speed;

    public SmoothFloat(float start, float speed) {
        this.current = start;
        this.target = start;
        this.speed = speed;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public void tick() {
        float delta = target - current;
        current += delta * speed;
        if (Math.abs(delta) < 0.01f) {
            current = target;
        }
    }

    public float get() {
        return current;
    }
}