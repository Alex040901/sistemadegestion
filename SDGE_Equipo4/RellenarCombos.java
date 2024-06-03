/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SDGE_Equipo4;

import Manejador.ManejadorBD;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author ac653
 */
public class RellenarCombos {
    
    private ManejadorBD manejadorBD;
    
    public RellenarCombos(ManejadorBD manejadorBD){
        this.manejadorBD = manejadorBD;
    }

    
    
    public void RellenarCombos(String tabla, String valor, JComboBox<String> combo){
        String sql = "SELECT * FROM " + tabla;
        //Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            Connection conexion = manejadorBD.getConexion();
            if (conexion != null) {
                st = conexion.createStatement();
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    String item = rs.getString(valor);
                    System.out.println("Adding item: " + item); // Debug print
                    combo.addItem(item);
                }
            } else {
                System.out.println("Connection is null");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.toString());
            }
        }
    }
    
}
