package com.jianda.beauty.design.creational.builder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PersonDirector personDirector = new PersonDirector();
        Builder builder = new ConcreteBuilder("头", "身体", "四肢");
        Person person = personDirector.createPerson(builder);
    }
}
