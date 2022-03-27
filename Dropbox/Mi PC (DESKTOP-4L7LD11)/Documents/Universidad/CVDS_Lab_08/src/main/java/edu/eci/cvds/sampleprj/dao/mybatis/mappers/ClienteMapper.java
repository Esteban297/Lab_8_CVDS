package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("idcli") long id); 
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id, El id del cliente al que se le registrara un item como rentado
     * @param idit, El id del item que se va a rentar
     * @param fechainicio, la fecha de inicio de la renta
     * @param fechafin, la fecha en la que se termina la renta
     */
    public void agregarItemRentadoACliente(@Param("idcli") int id, 
            @Param("iditr") int idit, 
            @Param("fini")Date fechainicio,
            @Param("ffin")Date fechafin);

    /**
     * Consultar todos los clientes
     * @return lista de todos los clientes
     */
    public List<Cliente> consultarClientes();
    
    /**
     * Insertar un cliente
     * @param cl, Objeto cliente a insertar
     */
    public void insertarCliente(@Param("cli") Cliente cl);

    /**
     * Insertar un item en rentados
     * @param docu, documento del cliente que lo va a rentar
     * @param item, identificadr del item
     * @param initialDate fecha en la que inicia el alquiler
     * @param finalDate fecha en la que deberia finalizar el alquiler
     */
    public void insertarItemRentado(@Param("cli") long docu,
                                    @Param("idIt") int item,
                                    @Param("iniD") Date initialDate,
                                    @Param("finD") Date finalDate);

    /**
     * Actualiza el estado vetado del cliente
     * @param docu, identificador del cliente
     * @param estado el nuevo estado de vetado
     */
    public void actualizarEstado(@Param("cli") long docu, 
                                @Param("vet") boolean estado);
}
