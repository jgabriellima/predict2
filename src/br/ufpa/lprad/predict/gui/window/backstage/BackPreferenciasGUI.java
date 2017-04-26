/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.backstage;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.util.ArrayList;
import java.util.List;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.SkinInfo;

/**
 *
 * @author J. Gabriel Lima
 */
public class BackPreferenciasGUI {

    public static String[] getLookAndFeel() throws PredictException {
        try {

            List listaSkins = new ArrayList(SubstanceLookAndFeel.getAllSkins().values());
            String resultado[] = new String[listaSkins.size()];
            //Adiciona o nome dos Skins no JComboBox

            for (int i = 0; i < listaSkins.size(); i++) {
                resultado[i] = ((SkinInfo) listaSkins.get(i)).getDisplayName();
            //System.out.println(((SkinInfo)listaSkins.get(i)).getDisplayName()+ " "+((SkinInfo)listaSkins.get(i)).getClassName());
            }

            return resultado;
        } catch (Exception e) {
            throw new PredictException("Erro ao preencher a combo de Look And Feel: " + e.getMessage());

        }
    }

    public static void salvarAlteracoes() {
        try {

            PredictPropriedades.gravaProperties();
           // PredictPropriedades.setLookAndFeel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNomeClasseLookAndFeel(String nome) throws PredictException {
        try {

            String nomeClasse = "";
            List listaSkins = new ArrayList(SubstanceLookAndFeel.getAllSkins().values());
            //Adiciona o nome dos Skins no JComboBox
            for (int i = 0; i < listaSkins.size(); i++) {
                if ((((SkinInfo) listaSkins.get(i)).getDisplayName()).equalsIgnoreCase(nome)) {
                    nomeClasse = ((SkinInfo) listaSkins.get(i)).getClassName();
                }
            }

            return nomeClasse;
        } catch (Exception e) {
            throw new PredictException("Erro e obter o nome da classe do lookandfeel.");
        }
    }
}
