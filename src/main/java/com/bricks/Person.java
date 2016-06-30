package com.bricks;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author caonn@mediav.com
 * @version 16/6/30.
 */
public class Person {

  public enum Sex {
    MALE, FEMALE
  }

  String name;
  LocalDate birthday;
  Sex gender;
  String emailAddress;
  int age;

  public int getAge() {
    return age;
  }

  public static void printPersonsOlderThan(List<Person> roster, int age) {
    for (Person p : roster) {
      if (p.getAge() >= age) {
        p.printPerson();
      }
    }
  }

  public static void printPersonsWithinAgeRange(List<Person> roster, int low, int high) {
    for (Person p : roster) {
      if (low <= p.getAge() && p.getAge() < high) {
        p.printPerson();
      }
    }
  }

  public static void printPersons(List<Person> roster, CheckPerson tester) {
    for (Person p : roster) {
      if (tester.test(p)) {
        p.printPerson();
      }
    }
  }

  public void printPerson() {
    System.out.println(toString());
  }

  @Override
  public String toString() {
    return "Name :" + name + " " +
            "Birthday :" + birthday + " " +
            "Gender :" + gender + " " +
            "Email Address :" + emailAddress + " " +
            "Age :" + age;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public Sex getGender() {
    return gender;
  }
  public static void printPersonsWithPredicate(
          List<Person> roster, Predicate<Person> tester) {
    for (Person p : roster) {
      if (tester.test(p)) {
        p.printPerson();
      }
    }
  }
  public static void processPersons(
          List<Person> roster,
          Predicate<Person> tester,
          Consumer<Person> block) {
    for (Person p : roster) {
      if (tester.test(p)) {
        block.accept(p);
      }
    }
  }
  public static void processPersonsWithFunction(
          List<Person> roster,
          Predicate<Person> tester,
          Function<Person, String> mapper,
          Consumer<String> block) {
    for (Person p : roster) {
      if (tester.test(p)) {
        String data = mapper.apply(p);
        block.accept(data);
      }
    }
  }
  public static <X, Y> void processElements(
          Iterable<X> source,
          Predicate<X> tester,
          Function <X, Y> mapper,
          Consumer<Y> block) {
    for (X p : source) {
      if (tester.test(p)) {
        Y data = mapper.apply(p);
        block.accept(data);
      }
    }
  }



  public static void main(String [] args) {
    List<Person> roster = Lists.newArrayList();
    printPersons(roster, new CheckPersonEligibleForSelectiveService());
    printPersons(
            roster,
            new CheckPerson() {
              public boolean test(Person p) {
                return p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25;
              }
            }
    );
    printPersons(
            roster,
            (Person p) -> p.getGender() == Person.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25
    );
    printPersonsWithPredicate(
            roster,
            p -> p.getGender() == Person.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25
    );

    processPersons(
            roster,
            p -> p.getGender() == Person.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25,
            p -> p.printPerson()
    );
    processElements(
            roster,
            p -> p.getGender() == Person.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25,
            Person::getEmailAddress,
            System.out::println
    );
    roster.stream()
            .filter(p -> p.getGender() == Person.Sex.MALE
                            && p.getAge() >= 18
                            && p.getAge() <= 25)
            .map(Person::getEmailAddress)
            .forEach(System.out::println);
  }

}

interface CheckPerson {
  boolean test(Person p);
}


interface Predicate<T> {
  boolean test(T t);
}

class CheckPersonEligibleForSelectiveService implements CheckPerson {
  public boolean test(Person p) {
    return p.gender == Person.Sex.MALE &&
            p.getAge() >= 18 &&
            p.getAge() <= 25;
  }
}