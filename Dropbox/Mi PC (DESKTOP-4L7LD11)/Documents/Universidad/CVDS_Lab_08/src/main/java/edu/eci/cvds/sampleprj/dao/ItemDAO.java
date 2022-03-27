package edu.eci.cvds.sampleprj.dao;

import java.sql.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Item;

public interface ItemDAO {

   public void save(Item it) throws PersistenceException;

   public Item load(int id) throws PersistenceException;

   public List<Item> loadAvailable() throws PersistenceException;

   public void updateRate(int id, long tarifa) throws PersistenceException;

}
