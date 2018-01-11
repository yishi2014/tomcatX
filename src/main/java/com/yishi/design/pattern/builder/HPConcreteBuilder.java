package com.yishi.design.pattern.builder;

public class HPConcreteBuilder extends Builder{
    private String brand="hp";
    @Override
    public void buidMounse() {
        this.computer.setMouse(new Mouse() {
            @Override
            public String toString() {
                return brand+" mouse";
            }
        });
    }

    @Override
    public void buildKeyBoard() {
        this.computer.setKeyboard(new Keyboard() {
            @Override
            public String toString() {
                return brand+" keyboard";
            }
        });

    }

    @Override
    public void buildScreen() {
        this.computer.setScreen(new Screen() {
            @Override
            public String toString() {
                return brand+" screen";
            }
        });
    }

    @Override
    public void buildMainEngine() {
        this.computer.setMainEngine(new MainEngine() {
            @Override
            public String toString() {
                return brand+" mainEngine";
            }
        });
    }

    @Override
    public ComputerProduct getProduct() {
        return this.computer;
    }
}
