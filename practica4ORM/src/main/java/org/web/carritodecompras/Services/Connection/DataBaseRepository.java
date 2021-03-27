package org.web.carritodecompras.Services.Connection;

import org.h2.tools.Server;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.*;
import java.util.List;

public class DataBaseRepository<T> {
    /*public static  DataBaseService dataBaseService;

    private String CONNECTION = "jdbc:h2:tcp://localhost/~/online_shop";
    public static DataBaseService getInstance(){
        if(dataBaseService == null ){
            dataBaseService = new DataBaseService();
        }
        return dataBaseService;
    }

    private  DataBaseService(){
        setDriver();
    }

    private void setDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("cannot register driver: "+e);
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(CONNECTION, "sa", "1234");
        } catch (SQLException e) {
            System.out.println("cannot access database: "+e);
        }
        return con;
    }*/

    private static EntityManagerFactory emf;
    private Class<T> entityClass;

    public DataBaseRepository(Class<T> entityClass) {
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public T create(T entity) throws IllegalArgumentException, EntityExistsException, PersistenceException {
        EntityManager em = getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

        }finally {
            em.close();
        }
        return entity;
    }

    public T update(T entity) throws PersistenceException{
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return entity;
    }

    public boolean delete(Object id) throws PersistenceException{
        boolean ok = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(entityClass, id);
            em.remove(entidad);
            em.getTransaction().commit();
            ok = true;
        }finally {
            em.close();
        }
        return ok;
    }

    public T find(Object parameter) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.getTransaction().commit();
            return em.find(entityClass, parameter);
        } finally {
            em.close();
        }
    }




    public List<T> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }






}
