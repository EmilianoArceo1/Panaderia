
package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



   public class Inventario {
    private Map<String, Integer> productos;


    public Inventario() {
        productos = new HashMap<>();
        cargarInventarioDesdeBD();
        
    }


    private void cargarInventarioDesdeBD() {
        String sql = "SELECT producto, cantidad FROM inventario";
        try (Connection conexion = ConexionBasesDeDatos.getConnection();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {
            while (resultado.next()) {
                productos.put(
                    resultado.getString("producto"),
                    resultado.getInt("cantidad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificarDisponibilidad(String producto, int cantidad) {
        return productos.getOrDefault(producto, 0) >= cantidad;
    }
     
        public void actualizarInventario(String producto, int cantidad) {
        if (productos.containsKey(producto)) {
            productos.put(producto, productos.get(producto) + cantidad);
            String sql = "UPDATE inventario SET cantidad = ? WHERE producto = ?";
            try (Connection conexion = ConexionBasesDeDatos.getConnection();
                 PreparedStatement sentencia = conexion.prepareStatement(sql)) {
                sentencia.setInt(1, productos.get(producto));
                sentencia.setString(2, producto);
                sentencia.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            agregarProducto(producto, cantidad);
        }
    }

        public void agregarProducto(String producto, int cantidad) {
        productos.put(producto, cantidad);
        String sql = "INSERT INTO inventario (producto, cantidad) VALUES (?, ?)";
        try (Connection conexion = ConexionBasesDeDatos.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, producto);
            sentencia.setInt(2, cantidad);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        public void borrarProducto(String producto) {
        if (productos.containsKey(producto)) {
            productos.remove(producto);
            String sql = "DELETE FROM inventario WHERE producto = ?";
            try (Connection conexion = ConexionBasesDeDatos.getConnection();
                 PreparedStatement sentencia = conexion.prepareStatement(sql)) {
                sentencia.setString(1, producto);
                sentencia.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public Map<String, Integer> getInventario() {
        return productos;
    }

   }