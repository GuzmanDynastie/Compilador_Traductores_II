
package compilador.controlador;

import compilador.vista.Compilador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Controlador implements ActionListener {

    Compilador compilador = new Compilador();
    ArrayList nameProject = new ArrayList();

    public Controlador(Compilador compilador) {
        this.compilador = compilador;
        compilador.botonCompilar.addActionListener(this);
        compilador.combobox.addActionListener(this);
    }
    
    private void saveNameProject(String name) {
        int position = name.lastIndexOf(".");
        if (position > 0) {
            String nameProject = name.substring(0, position);
            this.nameProject.add(nameProject);
        }
    }

    private void readFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "ladislao");
        fileChooser.setFileFilter(filter);

        int answer = fileChooser.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader fileReader = new FileReader(fileChooser.getSelectedFile());
                String name = fileChooser.getSelectedFile().getName();
                saveNameProject(name);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                StringBuilder content = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                bufferedReader.close();
                compilador.codigo.setText(content.toString());

            } catch (IOException e) {
                System.out.println("Ocurrio un error al leer el archivo: " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        String information = compilador.codigo.getText();
        String projectDirectory = System.getProperty("user.dir");
        Class<?> claseActual = this.getClass();
        String className = claseActual.getSimpleName();
        
        String filePath = projectDirectory + File.separator + className + ".ladislao";
        String filePathName = projectDirectory + File.separator + this.nameProject.get(0) + ".ladislao";
        
        var name = (this.nameProject != null && !this.nameProject.isEmpty()) ? filePathName : filePath;
        
        try {
            FileWriter writer = new FileWriter((String) name);
            writer.write(information);
            writer.close();
            JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente en " + name);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el archivo: " + e.getMessage());
        }
    }

    private void saveAsFile() {
        String information = compilador.codigo.getText();
        JFileChooser fileChooser = new JFileChooser();
        int answer = fileChooser.showSaveDialog(null);

        if (answer == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                try {
                    String pathRoute = selectedFile.getPath();
                    if (!pathRoute.endsWith(".ladislao")) {
                        pathRoute += ".ladislao";
                    }
                    FileWriter writer = new FileWriter(pathRoute);
                    writer.write(information);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al guardar el archivo: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo para guardar");
            }
        }
    }

    private void actionCombobox() {
        switch (compilador.combobox.getSelectedIndex()) {
            case 1 -> readFile();
            case 2 -> saveFile();
            case 3 -> saveAsFile();
            case 4 -> {
                try {
                    saveFile();
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar datos antes de cerrar el programa.");
                }
            }
            default -> System.out.println("No tiene ninguna acción");
        }    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == compilador.botonCompilar) {
            String codigo = compilador.codigo.getText();
            compilador.terminal.setText("TERMINAL\n\n" + codigo);
        }
        if (e.getSource() == compilador.combobox) {
            actionCombobox();
        }
    }
}
