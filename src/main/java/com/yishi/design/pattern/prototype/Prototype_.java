package com.yishi.design.pattern.prototype;

import java.io.IOException;
import java.io.Serializable;

public interface Prototype_ extends Serializable{
    Prototype_ Clone() ;
}
