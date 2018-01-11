package com.yishi.design.pattern.builder;

/**
 *产品类
 * 通常使用建造者模式的产品结构都比较复杂，由多部分构成，可能各部分之间还有关联
 */
public class ComputerProduct {
    private Mouse mouse;
    private Screen screen;
    private Keyboard keyboard;
    private MainEngine mainEngine;

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public MainEngine getMainEngine() {
        return mainEngine;
    }

    public void setMainEngine(MainEngine mainEngine) {
        this.mainEngine = mainEngine;
    }

    @Override
    public String toString() {
        return "ComputerProduct{" +
                "mouse=" + mouse +
                ", screen=" + screen +
                ", keyboard=" + keyboard +
                ", mainEngine=" + mainEngine +
                '}';
    }
}
