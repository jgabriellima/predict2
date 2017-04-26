package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Discretizar;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import br.ufpa.lprad.predict.redebayesiana.inferencias.MetaDados;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Processamento;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author Gabriel
 */
public class PainelInferencia extends JPanel implements Runnable {

    public Thread comecar;
    private Processamento proc;
    private MetaDados meta;
    public static Itens itens;
    public boolean arquivoAberto = false;
    public JLabel l;
    public JTextArea jt;
    public JProgressBar pbar;

    public PainelInferencia() {

        setLayout(new FlowLayout());
        setBorder(new javax.swing.border.TitledBorder(null, "Inferência Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));
        setPreferredSize(new java.awt.Dimension(320, 100000));
        comecar = new Thread(this);


        jt = new JTextArea();
        jt.setFont(new Font("TimesRoman", Font.BOLD, 14));
        jt.setText("\n\n\n\n\n\n\n\nAguarde enquanto o programa " +
                "gera as probabilidades." + "\n\nEsse processo pode demorar alguns minutos \ndevido a grande quantidade de cálculos \nque estão sendo realizados...");
        jt.setOpaque(false);
        jt.setEditable(false);

        pbar = new JProgressBar();
        pbar.setIndeterminate(true);
        pbar.setLocation(0, this.getHeight() / 5);
        add(jt);
        add(pbar);
    }

    public void fazInferencias() {

        try {
            proc = new Processamento(this);
            arquivoAberto = proc.abreArquivo(true);
            meta = new MetaDados(proc.getArqTxt(), this);
            proc.runScullyRun(meta);
//            Discretizar.defineFaixas();
//            K2.montaEstrutura(PredictPropriedades.getCaminhoCorrelacao());

            itens = new Itens(meta, meta.getOriginais(), this, this);

            setLayout(null);
            remove(jt);
            remove(pbar);
            updateUI();
            try {
                updateUI();
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Thread getComecar() {

        return comecar;
    }

    public void run() {
        try {

            fazInferencias();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
