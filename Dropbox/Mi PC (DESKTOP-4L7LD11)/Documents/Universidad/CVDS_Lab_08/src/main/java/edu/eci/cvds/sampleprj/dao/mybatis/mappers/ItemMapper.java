package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.sql.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Item;

/**
 *
 * @author 2106913
 */
public interface ItemMapper {
    
    
    public List<Item> consultarItems();        
    
    public Item consultarItem(@Param("idIt") int id);
    
    public void insertarItem(@Param("item") Item it);

    /**
     * Consulta los items que se encuentran disponibles para alquiler
     * @return los items que se encuentran disponibles para alquiler
     */
    public List<Item> consultarItemsDisponibles();

    /**
     * Actualizar la tarifa por dia del item
     * @param id, identificador del item
     * @param tarifa la nueva tarifa del item
     */
    public void actualizarTarifaItem(@Param("idIt") int id, 
                                    @Param("tarifa") long tarifa);
        
}
