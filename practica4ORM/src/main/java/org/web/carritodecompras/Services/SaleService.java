package org.web.carritodecompras.Services;

import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.Client;
import org.web.carritodecompras.models.Product;
import org.web.carritodecompras.models.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleService extends DataBaseRepository<Sale> {

    private static SaleService saleService;
    public SaleService() {
        super(Sale.class);
    }
    public static SaleService getInstance(){
        if(saleService == null){
            saleService = new SaleService();
        }
        return saleService;
    }


}
