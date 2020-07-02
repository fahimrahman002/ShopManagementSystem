/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.sql.Date;


public class products {
    String productId;
    String productName;
    String type;
    Double purchaseRate;
    Double salesRate;
    int vat;
    int stock;
    Date lastUpdate;

    public products(String productId, String productName, String type, Double purchaseRate, Double salesRate, int vat, int stock, Date lastUpdate) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.purchaseRate = purchaseRate;
        this.salesRate = salesRate;
        this.vat = vat;
        this.stock = stock;
        this.lastUpdate = lastUpdate;
    }
    
    
    
    
}
