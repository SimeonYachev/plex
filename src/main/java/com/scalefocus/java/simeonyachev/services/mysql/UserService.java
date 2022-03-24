package com.scalefocus.java.simeonyachev.services.mysql;

import com.scalefocus.java.simeonyachev.domain.mysql.User;
import com.scalefocus.java.simeonyachev.exceptions.UserAlreadyRegisteredException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

public interface UserService {
    Collection<User> getUsers();

    User register(User user) throws SQLIntegrityConstraintViolationException;

    User getById(Long id);

    User getByUsername(String username);

    String delete(Long id);
}
