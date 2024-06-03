/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SDGE_Equipo4;

import Manejador.ManejadorBD;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author ac653
 */
public class Ubicacion extends javax.swing.JFrame {
    
    private final ManejadorBD manejadorBD;

    /**
     * Creates new form Ubicacion
     */
    public Ubicacion() {
        initComponents();
        this.manejadorBD = new ManejadorBD();
        manejadorBD.conectar("sistemadegestionempresarial");

        llenarVentana("Ubicacion"); 
    }
    
        public Ubicacion(ManejadorBD manejadorBD) {
        initComponents();
        this.manejadorBD = manejadorBD;
        llenarVentana("Ubicacion");
    }
        
        private void llenarVentana(String tabla) {
        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        if (manejadorBD.consultaRegistros(tabla) != 0) {
            muestraRegistroActual();
            btnPrimero.setEnabled(true);
            btnSiguiente.setEnabled(true);
            btnAnterior.setEnabled(true);
            btnUltimo.setEnabled(true);
            btnEditar.setEnabled(true);

            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);
        }
    }
        
         private void muestraRegistroActual() {
        if (manejadorBD.indiceActual() != 0) {              
            manejadorBD.refrescaRegistroActual();
            btnEditar.setEnabled(true);
            txtIDUbicacion.setText(manejadorBD.getCampoRegistroActual("ID_Ubicacion").toString());
            txtNombre.setText(manejadorBD.getCampoRegistroActual("Nombre").toString());
            txtCapacidad.setText(manejadorBD.getCampoRegistroActual("Capacidad").toString());
                  
        } else {
            nuevoRegistro();
            btnEliminar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }
         
         private void primerRegistro() {
        manejadorBD.veAlPrimerRegistro();
        muestraRegistroActual();
    }

    private void ultimoRegistro() {
        manejadorBD.veAlUltimoRegistro();
        muestraRegistroActual();
    }

    private void siguienteRegistro() {
        manejadorBD.veAlSiguienteRegistro();
        muestraRegistroActual();
    }

    private void anteriorRegistro() {
        manejadorBD.veAlAnteriorRegistro();
        muestraRegistroActual();
    }
    
    private void limpiarRegistro() {
        txtIDUbicacion.setText("");
        txtNombre.setText("");
        txtCapacidad.setText("");
                
    }
    
    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistro();

    }
    
    private void activarCampos() {
        txtIDUbicacion.setEditable(false);
        txtNombre.setEditable(true);
        txtCapacidad.setEditable(true);
        
    }
    
    private void desactivarCampos() {
        txtIDUbicacion.setEditable(false);
        txtNombre.setEditable(false);
        txtCapacidad.setEditable(false);
                
    }
    
    private boolean validarCampos() {
        
        boolean correcto = true;                
        
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se debe introducir el nombre del lugar.",
                    "Error en el campo nombre",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        
        if (txtCapacidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se debe introducir la capacidad del lugar.",
                    "Error en el campo capacidad",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        
        return correcto;
    }
    
        private void insertaRegistro() {
        
        if(!validarCampos()){
            return;
        }
        
        try{
            
            manejadorBD.iniciarBloque("Ubicacion");
            //manejadorBD.insertarCampo("ID_Ubicacion", txtIDUbicacion.getText());
            manejadorBD.insertarCampo("Nombre", txtNombre.getText());
            manejadorBD.insertarCampo("Capacidad", txtCapacidad.getText());
            
            manejadorBD.cerrarBloque();
            
            limpiarRegistro();
            llenarVentana("Ubicacion");

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(this, 
                    "Error al insertar el registro en la base de datos: " + e.getMessage(),
                    "Error de inserción",
                    JOptionPane.ERROR_MESSAGE);
        }
            
        }                                        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTabla = new javax.swing.JLabel();
        lblCapacidad = new javax.swing.JLabel();
        txtCapacidad = new javax.swing.JTextField();
        lblIDUbicacion = new javax.swing.JLabel();
        txtIDUbicacion = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnPrimero = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnMenuPrincipal = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTabla.setBackground(new java.awt.Color(153, 51, 255));
        lblTabla.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        lblTabla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabla.setText("Ubicación");
        lblTabla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        lblTabla.setOpaque(true);
        jPanel1.add(lblTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 140, 40));

        lblCapacidad.setBackground(new java.awt.Color(102, 255, 255));
        lblCapacidad.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblCapacidad.setText("Capacidad");
        lblCapacidad.setOpaque(true);
        jPanel1.add(lblCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 140, 40));
        jPanel1.add(txtCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 200, 40));

        lblIDUbicacion.setBackground(new java.awt.Color(102, 255, 255));
        lblIDUbicacion.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblIDUbicacion.setText("ID Ubicacion");
        lblIDUbicacion.setOpaque(true);
        jPanel1.add(lblIDUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 140, 40));
        jPanel1.add(txtIDUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 200, 40));

        lblNombre.setBackground(new java.awt.Color(102, 255, 255));
        lblNombre.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNombre.setText("Nombre del lugar");
        lblNombre.setOpaque(true);
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 140, 40));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 200, 40));

        btnPrimero.setBackground(new java.awt.Color(153, 255, 255));
        btnPrimero.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnPrimero.setText("Primer registro");
        btnPrimero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 140, 30));

        btnSiguiente.setBackground(new java.awt.Color(153, 255, 255));
        btnSiguiente.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnSiguiente.setText("Siguiente registro");
        btnSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 140, 30));

        btnUltimo.setBackground(new java.awt.Color(153, 255, 255));
        btnUltimo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnUltimo.setText("Ultimo registro");
        btnUltimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 450, 140, 30));

        btnAnterior.setBackground(new java.awt.Color(153, 255, 255));
        btnAnterior.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnAnterior.setText("Anterior registro");
        btnAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 140, 30));

        btnNuevo.setBackground(new java.awt.Color(153, 255, 255));
        btnNuevo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 150, 30));

        btnGuardar.setBackground(new java.awt.Color(153, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Aceptar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 150, -1));

        btnCancelar.setBackground(new java.awt.Color(153, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, 150, -1));

        btnEditar.setBackground(new java.awt.Color(153, 255, 255));
        btnEditar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, 150, -1));

        btnMenuPrincipal.setBackground(new java.awt.Color(153, 255, 255));
        btnMenuPrincipal.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnMenuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Regresar.png"))); // NOI18N
        btnMenuPrincipal.setText("Menu principal");
        btnMenuPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPrincipalActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenuPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 280, 150, 40));

        btnActualizar.setBackground(new java.awt.Color(153, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 150, -1));

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 150, -1));

        btnSalir.setBackground(new java.awt.Color(255, 102, 0));
        btnSalir.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 440, 150, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        primerRegistro();
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        siguienteRegistro();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        ultimoRegistro();
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        anteriorRegistro();
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()) {
            manejadorBD.iniciarBloque("Ubicacion");
            insertaRegistro();
            manejadorBD.veAlPrimerRegistro();
            desactivarCampos();
            muestraRegistroActual();
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Los datos de la ubicacion han sido guardados.");
            manejadorBD.consultaRegistros("Ubicacion");
            manejadorBD.veAlPrimerRegistro();
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pueden guardar los datos de la ubicacion.",
                "Error al guardar el registro",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desactivarCampos();
        muestraRegistroActual();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        activarCampos();
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnMenuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPrincipalActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_PrincipalAdmin(manejadorBD).setVisible(true);
            }
        });

        dispose();
    }//GEN-LAST:event_btnMenuPrincipalActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarCampos()) {

            
            //manejadorBD.insertarCampo("ID_Ubicacion", txtIDUbicacion.getText());
            manejadorBD.insertarCampo("Nombre", txtNombre.getText());
            manejadorBD.insertarCampo("Capacidad", txtCapacidad.getText());
            //manejadorBD.insertarCampo("ID_Departamento", txtIDDepto.getText());

            manejadorBD.actualizaRegistroActual();

            JOptionPane.showMessageDialog(this, "Los datos de la ubicacion han sido actualizados.");
            llenarVentana("Ubicacion");
            desactivarCampos();
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(this,
                "No se pueden actualizar los datos de la ubicacion.",
                "Error al actualizar el registro",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Desea eliminar el registro del registro?",
            "Confirme su respuesta",
            JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            manejadorBD.borraRegistroActual();
            limpiarRegistro();
            llenarVentana("Ubicacion");
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación de la ubicacion");
        }
        desactivarCampos();

        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ubicacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ubicacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ubicacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ubicacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ubicacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMenuPrincipal;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblIDUbicacion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTabla;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextField txtIDUbicacion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
