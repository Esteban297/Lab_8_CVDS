/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.samples.services.client;



import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;


/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        ClienteMapper cm = sqlss.getMapper(ClienteMapper.class);
        System.out.println("\n---------------------------------------- CONSULTAR CLIENTES ----------------------------------------");
        System.out.println(cm.consultarClientes());

        int documentoPrueba = -1874919427;
        System.out.println("\n--------------------------- CONSULTAR CLIENTE DOCUMENTO = " + documentoPrueba + " ---------------------------");
        System.out.println(cm.consultarCliente(documentoPrueba));

        /* INSERCION AL CLIENTE 9819381 EL ITEM 2132735 */
        //cm.agregarItemRentadoACliente(346774, 1, Date.valueOf("2021-03-15"), Date.valueOf("2021-03-18"));

        ItemMapper im = sqlss.getMapper(ItemMapper.class);

        System.out.println("\n------------------------------------------ CONSULTAR ITEMS -----------------------------------------");
        System.out.println(im.consultarItems());

        int idItemPrueba = 545343;
        System.out.println("\n--------------------------- CONSULTAR ITEM ID = " + idItemPrueba + " ---------------------------");
        System.out.println(im.consultarItem(idItemPrueba));

        /* INSERCION EN TABLA ITEM  */
        //im.insertarItem(new Item(new TipoItem(3, "Peliculas"), 545343, "Nullq", "feugia", Date.valueOf("2021-03-16"), 20000, "euismo", "facilisi"));

        sqlss.commit();
        sqlss.close();

        
        
    }


}
