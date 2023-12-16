import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener{
    
    //initialisation des variables afin d'avoir la possibilité des les utiliser tout au long du classe
    JLabel labels[];
    JButton boutons[];
    JPanel panels[];

    MainFrame(){

        //déclarer qq propriétés
        Color bgcolor = new Color(73, 94, 87);
        Color yello = new Color(244, 206, 20);
        Color whit = new Color(255,255,255);
        Color blck = new Color(69, 71, 75);
        Font fBanBol = new Font("bahnschrift",Font.BOLD,25);
        Font fBanTitle = new Font("bahnschrift",Font.PLAIN,45);
        Font fBanMed = new Font("bahnschrift",Font.PLAIN,22);

        //définir propriétés du fenétre
        setSize(400,500);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)tailleEcran.getWidth()/2-200,(int)tailleEcran.getHeight()/2-250);//centrer la fenétre
        setTitle("Acceuil");
        getContentPane().setBackground(bgcolor);
        getContentPane().setLayout(new BorderLayout(20,40));
        setMinimumSize(new Dimension(400,400));
        ImageIcon appico = new ImageIcon("images/sabonner.png");
        setIconImage(appico.getImage());

        /*composants du fenétre*/

        //création des panels
        panels = new JPanel[]{new JPanel(),new JPanel(),new JPanel(),new JPanel()};//array des panels pour moins de variables
        for (JPanel p : panels) {
            p.setBackground(bgcolor);
        }
        panels[1].setLayout(new FlowLayout());
        panels[3].setLayout(new GridLayout(2,1,20,20));
        panels[3].setSize(new Dimension(300,200));

        //déclarer les icones afin de les mettre dans les boutons
        ImageIcon eleve = new ImageIcon("images/eleve.png");
        ImageIcon admin = new ImageIcon("images/reglages.png");

        //création des boutons et des labels
        labels = new JLabel[]{new JLabel("Bienvenue"),new JLabel("Copyright© None is reserved")};
        labels[0].setFont(fBanTitle);
        labels[1].setFont(fBanMed);
        for (JLabel lbl : labels) {
            lbl.setForeground(whit);
        }

        //array des bouton pour moins de variables
        boutons = new JButton[]{new JButton("Admin"),new JButton("User")};
        boutons[0].setIcon(admin);boutons[1].setIcon(eleve);
        for (JButton btn : boutons) {
            btn.setFont(fBanBol);
            btn.setBackground(blck);
            btn.setForeground(yello);
            btn.setHorizontalTextPosition(JButton.RIGHT);
            btn.setFocusable(false);
            btn.addActionListener(this);
        }

        //ajouter les elements aux panels
        panels[0].add(labels[0]);
        panels[3].add(boutons[0]);
        panels[3].add(boutons[1]);
        panels[1].add(panels[3]);
        panels[2].add(labels[1]);

        //ajouter les panels au fenétre
        add(panels[0],BorderLayout.NORTH);
        add(panels[1],BorderLayout.CENTER);
        add(panels[2],BorderLayout.SOUTH);

        //apparaitre la fenétre
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();//détruire la fenétre principal
        if(e.getSource()==boutons[0]){
            new AdminFrame();
        }
        if(e.getSource()==boutons[1]){
            new EleveFrame();
        }
    }

}
