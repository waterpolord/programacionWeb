package org.web.carritodecompras.Services;

import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.Product;
import org.web.carritodecompras.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductService extends DataBaseRepository<Product> {

    private static ProductService productService;

    public ProductService() {
        super(Product.class);
    }

    public static ProductService getInstance(){
        if(productService == null){
            productService = new ProductService();
        }
        return productService;
    }

    public List<Product> getPagedProducts(int page){
        int size = 5;
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM Product",Product.class);
        query.setFirstResult((page - 1)*size);
        query.setMaxResults(size);
        return query.getResultList();
    }


}
