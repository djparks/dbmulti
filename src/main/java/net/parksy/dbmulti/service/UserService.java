package net.parksy.dbmulti.service;

import jakarta.persistence.EntityManager;

public class UserService {
    private final EntityManager entityManager;
    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
