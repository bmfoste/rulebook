package com.deliveredtechnologies.rulebook.runner;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.annotation.When;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Created by clong on 2/12/17.
 */
public class Util {
  private Util() {}

  public static Predicate getWhenMethodAsPredicate(FactMap facts, Object obj) {
    for (Method method : obj.getClass().getMethods()) {
      for (Annotation annotation : method.getDeclaredAnnotations()) {
        if (annotation instanceof When && method.getReturnType() == Boolean.class) {
          return new Predicate() {
            @Override
            public boolean test(Object o) {
              try {
                return (Boolean)method.invoke(obj);
              } catch (InvocationTargetException | IllegalAccessException ex) {
                return false;
              }
            }
          };
        }
      }
    }
    return new Predicate() {
      @Override
      public boolean test(Object o) {
        return false;
      }
    };
  }
}
