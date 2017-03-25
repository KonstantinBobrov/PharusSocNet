package ru.pharus.socnetwork.service;

import java.util.Set;

@FunctionalInterface
public interface EntValidator {
    Set validate();
    /*public Set validate(T obj);*/

}
