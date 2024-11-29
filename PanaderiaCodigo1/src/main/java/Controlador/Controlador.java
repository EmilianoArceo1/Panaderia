package Controlador;


import Modelo.Inventario;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controlador {
    private Inventario inventario;
    
    public Controlador(){
        inventario = new Inventario();
    }
    
    public List<String> getProductos(){
        List<String> nombreProductos = new ArrayList<>(getInventario().keySet());
        return nombreProductos;
    }
    
    public void agregarProducto(String producto, int cantidad){
        inventario.agregarProducto(producto, cantidad);
    }
    
    public void borrarProducto(String producto){
        inventario.borrarProducto(producto);
    }

    public Map<String, Integer> getInventario() {
        return inventario.getInventario();
    }

}