package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.samples.entities.TipoItem;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import edu.eci.cvds.sampleprj.dao.PersistenceException;

public class MyBATISItemRentadoDAO implements ItemRentadoDAO{

    @Inject
    private ItemRentadoMapper itemRentadoMapper;

    @Override
    public List<ItemRentado> load(long idClient) throws PersistenceException {
        try{
            return itemRentadoMapper.consultarItemsRentadosCliente(idClient);
        } catch (org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los items rentados por el cliente " + idClient, e);
        }
    }

    @Override
    public ItemRentado loadByItem(int idItem) throws PersistenceException {
        try{
            return itemRentadoMapper.consultarItemRentadoporItem(idItem);
        } catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar item rentado por el id del item" + idItem, e);
        }
    }
}