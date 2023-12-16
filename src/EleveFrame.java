import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class EleveFrame extends JFrame implements ActionListener{

    //initialisation des variables afin d'avoir la possibilité des les utiliser tout au long du classe
    String Specialité[] = {"","AV","CM","IM","BD","MIME","INLog","INrev"};
    String club[] = {"","Microsoft","Enactus","LOG","Orenda","J2i"};
    JLabel labels[];
    JButton boutons[];
    JComboBox combos[];
    JTextField zones[];
    JPanel panels[];

    EleveFrame(){
        //déclarer qq propriétés
        Color bgcolor = new Color(73, 94, 87);
        Color yello = new Color(244, 206, 20);
        Color whit = new Color(255,255,255);
        Color blck = new Color(69, 71, 75);
        Font fBanBol = new Font("bahnschrift",Font.BOLD,25);
        Font fBanTitle = new Font("bahnschrift",Font.BOLD,45);
        Font fBanMed = new Font("bahnschrift",Font.PLAIN,22);

        //définir propriétés du fenétre
        setSize(450,600);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)tailleEcran.getWidth()/2-225,(int)tailleEcran.getHeight()/2-310);//centrer la fenétre
        setMinimumSize(new Dimension(450,550));
        setTitle("Inscription");
        getContentPane().setBackground(bgcolor);
        getContentPane().setLayout(new BorderLayout(20,20));
        ImageIcon appico = new ImageIcon("images/sabonner.png");
        setIconImage(appico.getImage());

        /*frame components*/

        //création des panels
        panels = new JPanel[]{new JPanel(),new JPanel(),new JPanel(),new JPanel()};//array des panels pour moins de variables
        for (JPanel p : panels) {
            p.setBackground(bgcolor);
        }
        panels[3].setLayout(new GridLayout(6,2,20,20));

        //déclarer les icones afin de les mettre dans les boutons
        ImageIcon tick = new ImageIcon("images/valider.png");
        ImageIcon annuler = new ImageIcon("images/annuler.png");
        ImageIcon retour = new ImageIcon("images/retour.png");

        //array des labels
        labels = new JLabel[]{new JLabel("Nom :"),new JLabel("Prénom :"),
                    new JLabel("Specialité :"),new JLabel("Club :"),
                    new JLabel("Inscription"),
                    new JLabel("Copyright© None is reserved ")};

        for (JLabel lbl : labels) {
            if (lbl.getText()=="Inscription"){lbl.setFont(fBanTitle);}
            else {lbl.setFont(fBanBol);}
            lbl.setForeground(whit);
        }

        //array des zones de text
        zones = new JTextField[]{new JTextField(),new JTextField()};
        for (JTextField tf : zones) {
            tf.setFont(fBanMed);
            tf.setBackground(blck);
            tf.setForeground(whit);
        }

        //array des listes déroulantes
        combos = new JComboBox[]{new JComboBox<>(Specialité),new JComboBox<>(club)};
        for (JComboBox cb : combos) {
            cb.setFont(fBanBol);
            cb.setForeground(Color.black);
        }

        //array des boutons
        boutons = new JButton[]{new JButton("inscrire"),new JButton("Annuler"),new JButton("Retour")};
        boutons[0].setIcon(tick);
        boutons[1].setIcon(annuler);
        boutons[2].setIcon(retour);
        for (JButton btn : boutons) {
            btn.setFont(fBanBol);
            btn.setBackground(blck);
            btn.setForeground(yello);
            btn.setHorizontalTextPosition(JButton.RIGHT);
            btn.setFocusable(false);
            btn.addActionListener(this);
        }

        //ajouter les elements aux panels
        panels[0].add(labels[4]);
        panels[3].add(labels[0]);panels[3].add(zones[0]);
        panels[3].add(labels[1]);panels[3].add(zones[1]);
        panels[3].add(labels[2]);panels[3].add(combos[0]);
        panels[3].add(labels[3]);panels[3].add(combos[1]);
        panels[3].add(boutons[0]);panels[3].add(boutons[1]);
        panels[1].add(panels[3]);
        panels[3].add(boutons[2]);
        panels[2].add(labels[5]);

        //ajouter les panels au fenétre
        add(panels[0],BorderLayout.NORTH);
        add(panels[1],BorderLayout.CENTER);
        add(panels[2],BorderLayout.SOUTH);

        //apparaitre la fenétre
        setVisible(true);

    }

    //gérer les cliques sur les boutons pour performer différent fontcion
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==boutons[2]){
            this.setVisible(false);
            new MainFrame();
        }
        if(e.getSource()==boutons[1]){
            for (JTextField tf : zones) {
                tf.setText("");
            }
            for (JComboBox cb : combos){
                cb.setSelectedItem("");
            }
        }
        if(e.getSource()==boutons[0]){
            boolean valid = true;
            if(zones[0].getText().strip().length()<=0){
                JOptionPane.showMessageDialog(null, "remplir le champ nom !", "Missing data", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
            else if(zones[1].getText().strip().length()<=0){
                JOptionPane.showMessageDialog(null, "remplir le champ prénom !", "Missing data", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
            else if(combos[0].getSelectedItem()==""){
                JOptionPane.showMessageDialog(null, "choisir votre spécialité !", "Missing data", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
            else if(combos[1].getSelectedItem()==""){
                JOptionPane.showMessageDialog(null, "choisir votre club !", "Missing data", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
            if (valid){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/university","root","");
                    String requete = "insert into student(nom,prenom,spec,club) values("+"'"+zones[0].getText()+"'"+','+"'"+zones[1].getText()+"'"+','+"'"+combos[0].getSelectedItem()+"'"+','+"'"+combos[1].getSelectedItem()+"'"+')';
                    PreparedStatement st = con.prepareStatement(requete);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inscription succées", "Success", JOptionPane.INFORMATION_MESSAGE);
                    con.close();
                }
                catch (Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Vérifier que le serveur mysql est lancé", "Connexion échoué", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
