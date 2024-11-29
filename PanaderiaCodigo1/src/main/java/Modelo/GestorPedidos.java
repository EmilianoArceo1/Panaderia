package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {

    // Método para agregar un pedido
    public void agregarPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (producto, cantidad, fechaEntrega, horaEntrega, estado, prioridad) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBasesDeDatos.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, pedido.getProducto());
            sentencia.setInt(2, pedido.getCantidad());
            sentencia.setDate(3, new java.sql.Date(pedido.getFechaEntrega().getTime()));
            sentencia.setTime(4, pedido.getHoraEntrega());
            sentencia.setString(5, pedido.getEstado());
            sentencia.setString(6, pedido.getPrioridad());
    
            sentencia.executeUpdate();

            // Obtener el ID generado automáticamente
            try (ResultSet clavesGeneradas = sentencia.getGeneratedKeys()) {
                if (clavesGeneradas.next()) {
                    pedido.setId(clavesGeneradas.getInt(1));
                }
            }
        }
    }

    // Método para listar todos los pedidos
    public List<Pedido> listarPedidos() throws SQLException {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> listaPedidos = new ArrayList<>();
        try (Connection conexion = ConexionBasesDeDatos.getConnection();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {
            while (resultado.next()) {
                Pedido pedido = new Pedido(
                    resultado.getInt("id"),
                    resultado.getString("producto"),
                    resultado.getInt("cantidad"),
                    resultado.getDate("fechaEntrega"),
                    resultado.getTime("horaEntrega"),
                    resultado.getString("estado"),
                    resultado.getString("prioridad")  
                );
                listaPedidos.add(pedido);
            }
        }
        return listaPedidos;
    }
    
    public void actualizarEstadoPedido(int id, String nuevoEstado) throws SQLException {
    String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
    try (Connection conexion = ConexionBasesDeDatos.getConnection();
         PreparedStatement sentencia = conexion.prepareStatement(sql)) {
        sentencia.setString(1, nuevoEstado);
        sentencia.setInt(2, id);
        sentencia.executeUpdate();
    }
}

    
    
}
