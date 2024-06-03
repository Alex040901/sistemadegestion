package Manejador;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManejadorBD {
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private String tipo;
    
    public ManejadorBD() {
        conexion = null;
            
    }
    public ManejadorBD(Connection conexion) {
        this.conexion = conexion;
        
    }

    public Connection getConexion() {
        return conexion;
    }

    public String getTipo() {
        return tipo;
    }
    
    public void conectar(String bd) {
        try {
        tipo = "MySQL";
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Actualiza con tus propios detalles de usuario, contraseña y la ubicación correcta de tu servidor
        String url = "jdbc:mysql://localhost:3306/sistemadegestionempresarial?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String usuario = "empleado";
        String contraseña = "empleado1";

        conexion = DriverManager.getConnection(url, usuario, contraseña);
        System.out.println("Conexion exitosa a la base de datos en MySQL");
    } catch (ClassNotFoundException | SQLException ex) {
        System.err.println("Error al establecer conexión con la base de datos en MySQL");
        System.err.println(ex.getMessage());
    }
}


    // conexion a una BD MySQL
    public void conectar( String nombreBD, String usuario, String clave, String maquina, int puerto) {
        tipo = "MySQL";
        try {
            String url = "jdbc:mysql://" + maquina + ":" + puerto + "/" + nombreBD + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            conexion = DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexión exitosa a la base de datos " + nombreBD + " en MySQL");
        } catch (SQLException e) {
            System.err.println("Error al conectarse a MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // informacion acerca de la conexion
    public void informacionConexion() {
        try {
            System.out.println("Modo auto-commit:"+conexion.getAutoCommit());
            System.out.println("Catalogo:"+conexion.getCatalog());
            System.out.println("Conexion cerrada:"+conexion.isClosed());
            System.out.println("Conexion de solo lectura:"
                                    + conexion.isReadOnly());
        }
        catch(SQLException e) {
            System.err.println("Error al dar informacion de la conexion: "
                                + e.getMessage());
            
        }
    }

    // libera la conexion a una BD 
    public void cerrarConexion() {
       try {
        if (resultado != null) {
            resultado.close(); // Simplemente cierra el resultado
        }
        if (sentencia != null) {
            sentencia.close(); // Cierra la sentencia
        }
        if (conexion != null) {
            conexion.close(); // Cierra la conexión
        }
    } catch (SQLException e) {
        System.err.println("Error al cerrar recursos: " + e.getMessage());
          }
    }

    /**
     * Necesario para poder insertar datos a la tabla.
     * Este metodo debe de ir antes de utilizar el metodo
     * insertarCampo(String nombreColumna, Object valorColumna)
     * Al terminar de insertar todos los datos deseados a la Tabla
     * se debe terminar con el metodo cerrarBloque()
     */
    public void iniciarBloque(String nombreTabla) {
             try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_UPDATABLE);
                    resultado = sentencia.executeQuery("SELECT * FROM " + nombreTabla);
                    resultado.moveToInsertRow();
            } catch (SQLException e) {
                  System.err.println("Error en iniciar bloque: "
                                                      + e.getMessage());
                      e.printStackTrace();
        }
    }

    /**
     * Permite insertar campos a la BD.
     * Este metodo debe ir entre los metodos
     * iniciarBloque(String nombreTabla) y cerrarBloque().
     */
    public void insertarCampo(String nombreColumna, Object valorColumna) {
            try {
            resultado.updateObject(nombreColumna, valorColumna);
        } catch (SQLException e) {
            System.err.println("Error al insertar campo a la BD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Necesario para poder guardar el registro a la BD.
     * Este metodo debe ir despues del metodo
     * insertarCampo(String nombreColumna,Object valorColumna).
     */
    public void cerrarBloque() throws SQLException{
         
        if (resultado != null) {
        resultado.insertRow();
        resultado.close();
        sentencia.close();
        }
    }

    /**
     * Realiza una busqueda en una tabla de la BD, en caso de haber
     * encontrado el campo requerido devuelve true,
     * en caso contrario devolvera false.
     */
    public boolean existeCampo(String nombreTabla, String nombreColumna,
                               Object nombreCampoBuscado) {
            try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_READ_ONLY);
                    resultado = sentencia.executeQuery("SELECT " + nombreColumna
                                                 + " FROM " + nombreTabla);
                    while(resultado.next()) {
                            if(resultado.getObject(1).equals(nombreCampoBuscado))
                                    return true;
                    }
            } catch (SQLException e) {
                  System.err.println("Error al bucar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
            return false;
    }

    /**
     * Realiza una busqueda en una tabla de la BD;
     * por cada campo que coincida con el campo que se esta buscando,
     * se incrementa una variable de tipo int,
     * al final devuelve el numero de campos iguales que encontro.
     */
    public int buscarCampo(String nombreTabla, String nombreColumna,
                            Object nombreCampoBuscado) {
            int cantidad = 0;
            try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_READ_ONLY);
                    resultado = sentencia.executeQuery("SELECT " + nombreColumna
                                                 + " FROM " + nombreTabla);
                    while(resultado.next()) {
                            if(resultado.getObject(1).equals(nombreCampoBuscado))
                                    cantidad++;
                    }
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
            return cantidad;
    }

    /**
     * Realiza una consulta general en una tabla de la BD;
     * al final devuelve el numero de registros que encontro.
     */
    public int consultaRegistros(String nombreTabla) {
            int cantidad = 0;
            try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_UPDATABLE);
                    resultado = sentencia.executeQuery("SELECT * FROM " + nombreTabla);
                    resultado.last();
                    cantidad = resultado.getRow();
                    resultado.first();
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
            return cantidad;
    }

    /**
     * Realiza una consulta general en una tabla de la BD;
     * aplicando una condicion
     * al final devuelve el numero de registros que encontro.
     */
    public int consultaRegistros(String nombreTabla, String condicion) {
            //System.out.println("condicion: " + condicion);
            int cantidad = 0;
            try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_UPDATABLE);
                    resultado = sentencia.executeQuery("SELECT * FROM " + nombreTabla + " WHERE " + condicion);
                    resultado.last();
                    cantidad = resultado.getRow();
                    resultado.first();
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
            return cantidad;
    }

    /**
     * Realiza una consulta general sobre la BD;
     * al final devuelve el numero de registros que encontro.
     */
    public ResultSet ejecutaConsultaGeneral(String consulta) {

            try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_UPDATABLE);
                    resultado = sentencia.executeQuery(consulta);
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
                      return null;
              }
            return resultado;
    }

    /**
     * obtiene la cantidad de registros de la consulta actual.
     */
    public int indiceActual() {
            int cantidad = 0;
            try {
                    cantidad = resultado.getRow();
            } catch (SQLException e) {
                  System.err.println("Error al leer el indice actual: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
            return cantidad;
    }

    /**
     * refresca los datos del registro actual.
     */
    public void refrescaRegistroActual() {
            try {
                    if(indiceActual() != 0) {
                            resultado.refreshRow();
                    }
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * va al primer registro de la consulta actual.
     */
    public void veAlPrimerRegistro() {
            try {
                    resultado.first();
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * va al ultimo registro de la consulta actual.
     */
    public void veAlUltimoRegistro() {
            try {
                    resultado.last();
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * va al siguiente registro de la consulta actual.
     */
    public void veAlSiguienteRegistro() {
            try {
                    if(!resultado.next()) {
                            resultado.last();
                    }
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * va al anterior registro de la consulta actual.
     */
    public void veAlAnteriorRegistro() {
            try {
                    if(!resultado.previous()) {
                            resultado.last();
                    }
            } catch (SQLException e) {
                  System.err.println("Error al buscar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * Regresa el valor del campo en el registro actual.
     */
    public Object getCampoRegistroActual(String nombreCampo) {
            try {
                    return resultado.getObject(nombreCampo);
            } catch (SQLException e) {
                  System.err.println("Error al leer el campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
                      return null;
              }
    }

    /**
     * borra el registro actual.
     */
    public void borraRegistroActual() {
            try {
                    resultado.deleteRow();
            } catch (SQLException e) {
                  System.err.println("Error al borrar el registro actual: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * actualiza el registro actual.
     */
    public void actualizaRegistroActual() {
            try {
                    resultado.updateRow();
            } catch (SQLException e) {
                  System.err.println("Error al borrar el registro actual: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    // actualiza un solo campo de la tabla especificada.
    public void actualizaCampo(String nombreTabla, String nombreColumna,
                                Object nombreCampoActual,
                                Object nombreCampoNuevo) {
            try {
                    sentencia = conexion.createStatement
                                (ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_UPDATABLE);
                    resultado = sentencia.executeQuery("SELECT * FROM " + nombreTabla);
                    while(resultado.next()) {
                            if(resultado.getObject(nombreColumna).equals(nombreCampoActual)) {
                                    resultado.updateObject(nombreColumna,nombreCampoNuevo);
                                    resultado.updateRow();
                            }
                    }
            } catch (SQLException e) {
                  System.err.println("Error al actualizar campo: "
                                                      + e.getMessage());
                      e.printStackTrace();
              }
    }

    /**
     * Elimina un registro dentro la tabla especificada.
     * El campo que se desea eliminar debe ser la llave de la tabla.
     */
    public void eliminaCampo(String nombreTabla, String nombreColumnaLlave,
                                                      Object nombreCampo) {
            try {
                    sentencia = conexion.createStatement(
                                    ResultSet.TYPE_SCROLL_SENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
                    resultado = sentencia.executeQuery("SELECT " + nombreColumnaLlave
                                                 + " FROM " + nombreTabla);
                    while(resultado.next()) {
                            if(resultado.getObject(1).toString().equals(nombreCampo.toString()))
                                    resultado.deleteRow();
                    }
            } catch (SQLException e) {
                  System.err.println("Error al eliminar campo: "+ e.getMessage());
                      e.printStackTrace();
              }
    }
           
    
}