package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.mybatis.guice.transactional.Transactional;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;

   @Inject
   private ClienteDAO clienteDAO;

   @Inject
   private TipoItemDAO tipoItemDAO;

   @Inject
   private ItemRentadoDAO itemRentadoDAO;

   public static final int MULTA_DIARIA = 5000;

   @Override
   public int valorMultaRetrasoxDia(int itemId) throws ExcepcionServiciosAlquiler {
       try{
           return (int) itemDAO.load(itemId).getTarifaxDia() + MULTA_DIARIA;
           //return MULTA_DIARIA;
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar valor multa.", ex);
       }
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       try{
           Cliente cliente = clienteDAO.load(docu);
           if (cliente == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.NO_CLIENT_REGISTRED);
           return cliente;
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar el cliente.", ex);
       }
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
       try{
           consultarCliente(idcliente);
           return itemRentadoDAO.load(idcliente);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar los items del cliente.", ex);
       }
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       try{
           return clienteDAO.loadAll();
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar los clientes.", ex);
       }
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           Item item = itemDAO.load(id);
           if (item == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.NO_ITEM);
           return item;
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler {
       try{
           return itemDAO.loadAvailable();
       } catch (PersistenceException ex){
            throw new ExcepcionServiciosAlquiler("Error al consultar items disponibles", ex);
       }
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       try{
           consultarItem(iditem);
           ItemRentado itemRentado = itemRentadoDAO.loadByItem(iditem);
           if (itemRentado == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.NO_ITEM_RENTED);
           LocalDate fechaFinal = itemRentado.getFechafinrenta().toLocalDate();
           long dias = ChronoUnit.DAYS.between(fechaFinal, fechaDevolucion.toLocalDate());
           System.out.println("Test: " + dias);
           if (dias < 0) dias = 0;
           return  dias * valorMultaRetrasoxDia(iditem);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar la multa del alquiler.", ex);
       }
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       try{
           TipoItem tipo = tipoItemDAO.load(id);
           if (tipo == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.NO_ITEM_TYPE);
           return tipo;
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item", ex);
       }
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       try{
           return tipoItemDAO.loadAll();
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar los tipos de items.", ex);
       }
   }

   @Transactional
   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
       try{
           if (numdias <=0 ) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.INVALID_DAYS);
           consultarCliente(docu); consultarItem(item.getId());
           clienteDAO.saveRenting(docu, item, date, Date.valueOf(date.toLocalDate().plusDays(numdias)));
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al registrar alquiler al cliente.");
       }
   }

   @Transactional
   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       try{
           clienteDAO.save(c);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al resgistrar el cliente.", ex);
       }
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
       if (numdias <= 0) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.INVALID_DAYS);
       try{
           return itemDAO.load(iditem).getTarifaxDia() * numdias;
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar el costo de alquiler.");

       }      
   }

   @Transactional
   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       try{
           if (tarifa <= 0) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.INVALID_RATE);
           if (itemDAO.load(id) == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.NO_ITEM);
           
           itemDAO.updateRate(id, tarifa);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al actualizar la tarifa del item.");
       }
   }

   @Transactional
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       try{
           itemDAO.save(i);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al registrar el item.", ex);
       }
   }

   @Transactional
   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
       try{
           clienteDAO.updateState(docu, estado);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al vetal al cliente.");
       }
   }
   
   @Transactional
   @Override
   public void registrarTipoItem(TipoItem ti) throws ExcepcionServiciosAlquiler {
       try{
           tipoItemDAO.save(ti);
       } catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al registrar el tipo de item.");
       }
   }
}