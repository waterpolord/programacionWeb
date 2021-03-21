package org.web.carritodecompras.Services;

import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.Client;
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

public class ClientService extends DataBaseRepository<Client> {

    private static ClientService clientService;

    public ClientService() {
        super(Client.class);
    }

    public static ClientService getInstance(){
        if(clientService == null){
            clientService = new ClientService();
        }
        return clientService;
    }

    public Client findClientByMail(String mail){
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Client.findClientByMail");
        query.setParameter("mail", "%"+mail+"%");
        List<Client> lista = query.getResultList();

            if(lista.isEmpty()){
                System.out.println("Cliente no encontrado");
                return null;
            }
            return lista.get(0);


    }


}
