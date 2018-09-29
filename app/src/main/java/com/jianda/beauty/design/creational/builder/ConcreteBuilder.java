package com.jianda.beauty.design.creational.builder;

/**
 * 实现Builder接口，针对不同的商业逻辑，具体化复杂对象
 * 的各部分的创建。 在建造过程完成后，提供产品的实例。
 */
public class ConcreteBuilder implements Builder {

    private final Person person;
    private final String mHead;
    private final String mBody;
    private final String mFoot;

    public ConcreteBuilder(String head, String body, String foot) {
        mHead = head;
        mBody = body;
        mFoot = foot;
        person = new Person();
    }

    @Override
    public void buildHead() {
        person.setHead(mHead);
    }

    @Override
    public void buildBody() {
        person.setBody(mBody);
    }

    @Override
    public void buildFood() {
        person.setFoot(mFoot);
    }

    @Override
    public Person buildPerson() {
        return person;
    }
}
