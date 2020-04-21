package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;

public class InheritanceTestSubclass extends InheritanceTestSuperclass {

    Circle shape;

    InheritanceTestSubclass() {
        super.shape = new Circle();
    }

    //@BeforeEach
    //void givenOneDefaultCircle() {
    //    shape = new Circle();
    //}
}
