package com.gestionevenements.services;

import com.gestionevenements.database.StockDAO;
import com.gestionevenements.models.Stock;

import java.util.List;

public class StockService {

    private StockDAO stockDAO;

    public StockService() {
        this.stockDAO = new StockDAO();
    }

    public boolean ajouterStock(Stock stock) {
        return stockDAO.ajouter(stock) != -1;
    }

    public boolean modifierStock(Stock stock) {
        return stockDAO.modifier(stock);
    }

    public boolean supprimerStock(int id) {
        return stockDAO.supprimer(id);
    }

    public List<Stock> getAllStocks() {
        return stockDAO.getTout();
    }

    public Stock getStockById(int id) {
        return stockDAO.getById(id);
    }
}

