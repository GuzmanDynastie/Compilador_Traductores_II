package compilador.controlador;

import compilador.vista.Compilador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {

    Compilador compilador = new Compilador();

    public Controlador(Compilador compilador) {
        this.compilador = compilador;
        compilador.botonCompilar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == compilador.botonCompilar) {
            String codigo = compilador.codigo.getText();
            compilador.terminal.setText("TERMINAL\n\n" + codigo);
        }
    }
}
