# datamill [![Build Status](https://travis-ci.org/rchodava/datamill.svg?branch=master)](https://travis-ci.org/rchodava/datamill) [![Coverage Status](https://coveralls.io/repos/rchodava/datamill/badge.svg?branch=master&service=github)](https://coveralls.io/github/rchodava/datamill?branch=master)
## Introduction

datamill is a Java framework for web applications using a functional reactive style built on RxJava. It is intended to
be used with Java 8 and lambdas. Unlike other modern Java frameworks, it makes the flow and manipulation of data through
your application highly visible. That means that you won't find yourself strewing your code with magic annotations,
whose function and effect are hidden within complex framework code and documentation. Instead, you will explicitly
specify how data flows through your application, and how to modify that data as it does. And you do so using the simple
style that RxJava allows.

## Reflection

One of the primary utilities in datamill is an API for performing reflection on your Java classes. A core concept in this reflection API is an outline. An outline provides an easy way for you to get the names of the various properties and methods of your classes. For example, let's build an outline for a simple entity:

```java
import org.chodavarapu.datamill.reflection.Outline;
import org.chodavarapu.datamill.reflection.OutlineBuilder;

public class Main {
    public class SystemUser {
        private String firstName;

        public String getFirstName() {
            return firstName;
        }
    }

    public static void main(String[] arguments) {
        Outline<SystemUser> userOutline = new OutlineBuilder(SystemUser.class).defaultSnakeCased().build();
    }
}
```

Using this outline, we can now get the name of the class itself, as well as the property:

```java
String entityName = userOutline.name(); // returns "system_user"
String propertyName = userOutline.name(userOutline.members().getFirstName()); // returns "first_name"
```

Note that the `name` methods return snake_cased names. This is because we built an outline, calling `defaultSnakeCased()` on the builder. We could have defaulted to using camelCased names by calling `defaultcamelCased()` instead. The outline also has specific methods for obtaining camelCased, snake_cased, and pluralized names (using an English inflector library).

Note specifically the way property names are obtained. Calling the `members()` method on an `Outline` returns a special proxy instance of the entity class. Calling the `getFirstName` getter on this proxy instance records a call on this particular getter so that when the `name` method is called on the outlin`, it will return the last call made to the outline proxy's methods. Note that this mechanism is thread-safe so that an outline can be safely re-used in multiple threads and still return the correct name.
