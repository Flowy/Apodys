package sample.property;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.property.SimpleObjectProperty;

public class Main {

    public static void main(String[] args) {
        SimpleObjectProperty<RootObject> root = new SimpleObjectProperty<>(new RootObject());



        root.get().setTest(new RootObject.TestObject());
    }

    public static class RootObject {
        private SimpleObjectProperty<TestObject> test = new SimpleObjectProperty<>(this, "test");

        public TestObject getTest() {
            return test.get();
        }

        public SimpleObjectProperty<TestObject> testProperty() {
            return test;
        }

        public void setTest(TestObject test) {
            this.test.set(test);
        }

        public static class TestObject {}
    }

}
