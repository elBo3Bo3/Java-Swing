import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminFrame extends JFrame implements ActionListener{
    
    //initialisation des variables afin d'avoir la possibilité des les utiliser tout au long du classe
    String Specialité[] = {"","AV","CM","IM","BD","MIME","INLog","INrev"};
    String club[] = {"","Microsoft","Enactus","LOG","Orenda","J2i"};
    JLabel labels[];
    JButton boutons[];
    JTable rechtab;
    JScrollPane sp;
    JComboBox combos[];
    JPanel panels[];
    DefaultTableModel dataien;
    int rows[];

    AdminFrame(){

        //déclarer qq propriétés
        Color bgcolor = new Color(73, 94, 87);
        Color yello = new Color(244, 206, 20);
        Color whit = new Color(255,255,255);
        Color blck = new Color(69, 71, 75);
        Font fBanBol = new Font("bahnschrift",Font.BOLD,25);
        Font fBanTitle = new Font("bahnschrift",Font.BOLD,45);
        Font fBanMed = new Font("bahnschrift",Font.PLAIN,22);

        //définir propriétés du fenétre
        setSize(750,600);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)tailleEcran.getWidth()/2-375,(int)tailleEcran.getHeight()/2-300);//centrer la fenétre
        setMinimumSize(new Dimension(750,600));
        setTitle("Administration");
        getContentPane().setBackground(bgcolor);
        getContentPane().setLayout(new BorderLayout(20,20));
        ImageIcon appico = new ImageIcon("C:\\Users\\dhiab\\Desktop\\DSJava\\images\\sabonner.png");
        setIconImage(appico.getImage());

        //array des panels
        panels = new JPanel[]{new JPanel(),new JPanel(),new JPanel(),new JPanel(),new JPanel(),new JPanel(),new JPanel()};
        for (JPanel p : panels) {
            p.setBackground(bgcolor);
        }

        //définir le type de layout de chaque panel
        panels[2].setLayout(new BorderLayout(20,20));
        panels[3].setLayout(new BorderLayout(20,20));
        panels[3].setPreferredSize(new Dimension(700,550));
        panels[4].setLayout(new GridLayout(1,2,20,20));
        panels[5].setLayout(new GridLayout(1,2,20,20));
        panels[6].setLayout(new GridLayout(2,1,20,20));

        ImageIcon retour = new ImageIcon("C:\\Users\\dhiab\\Desktop\\DSJava\\images\\retour.png");
        ImageIcon rechercher = new ImageIcon("C:\\Users\\dhiab\\Desktop\\DSJava\\images\\loupe.png");

        boutons = new JButton[]{new JButton("Rechercher"),new JButton("Retour")};
        for (JButton btn : boutons) {
            btn.setBackground(blck);
            btn.setForeground(whit);
            btn.setHorizontalTextPosition(JButton.RIGHT);
            btn.setFocusable(false);
            btn.setFont(fBanMed);
            btn.addActionListener(this);
        }
        boutons[1].setIcon(retour);
        boutons[0].setIcon(rechercher);

        //array des labels
        labels = new JLabel[]{new JLabel("Administration"),new JLabel("Choix :"),
                            new JLabel("Copyright© None is reserved ")};
        for (JLabel lbl : labels) {
            lbl.setFont(fBanBol);
            lbl.setForeground(whit);
        }
        labels[0].setFont(fBanTitle);

        //array des listes déroulantes
        combos = new JComboBox[]{new JComboBox<>(new String[]{"","Spécialité","Club"}),new JComboBox<>()};
        for (JComboBox cb : combos) {
            cb.setFont(fBanBol);
            cb.setForeground(Color.black);
            cb.addActionListener(this);
        }

        //tableau qui va contenir les résultat des recherches
        String colonnes[] = {"Nom","Prénom","Spécialité","Club"};
        dataien = new DefaultTableModel(colonnes,0);
        rechtab = new JTable(dataien);
        rechtab.getTableHeader().setFont(fBanMed);
        rechtab.getTableHeader().setBackground(blck);
        rechtab.getTableHeader().setForeground(whit);
        rechtab.setRowHeight(30);
        rechtab.setFont(fBanMed);
        rechtab.setForeground(new Color (117, 14, 33));
        rechtab.setSelectionBackground(new Color(190, 215, 84));
        rechtab.setSelectionForeground(new Color(25, 25, 25));
        rechtab.getTableHeader().setOpaque(false);
        sp = new JScrollPane(rechtab);
        ImageIcon corbeille = new ImageIcon("images/supprimer.png");

        /* ajout de possibilité de supprimer les lignes souhaité en faisant une click droite
        un menu va apparaitre qui contient l'option supprimer */
        rechtab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){
                    JPopupMenu rightclickmenu = new JPopupMenu();
                    JMenuItem supprim = new JMenuItem("Supprimer",corbeille);
                    rightclickmenu.add(supprim);
                    rightclickmenu.show(rechtab, e.getX(), e.getY());
                    rows = rechtab.getSelectedRows();
                    if (rows.length<=0){
                        int selection = rechtab.rowAtPoint(e.getPoint());
                        rechtab.setRowSelectionInterval(selection, selection);
                        rows = rechtab.getSelectedRows();
                    }
                    for(int x=0;x<rows.length;x++){
                        System.out.println(rows[x]);
                    }
                    supprim.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent a){
                            int resp = JOptionPane.showConfirmDialog(null, "Etes vous sur de supprimer la selection ?","Confirmation",JOptionPane.YES_NO_OPTION);
                            if(resp==0){
                                Connection connexion2 = etablirConnexion();
                                String req;
                                Object dataToDel[] = new Object[4];
                                while(rows.length > 0){
                                    for(int j=0;j<4;j++){
                                        dataToDel[j] = rechtab.getValueAt(rows[0], j);
                                    }
                                    req = "delete from student where nom = '"+dataToDel[0]+"' and prenom ='"+dataToDel[1]+"' and spec ='"+dataToDel[2]+"' and club ='"+dataToDel[3]+"'";
                                    try{
                                        PreparedStatement pst = connexion2.prepareStatement(req);
                                        pst.executeUpdate();
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }
                                    dataien.removeRow(rows[0]);
                                    rows = rechtab.getSelectedRows();
                                }
                                JOptionPane.showMessageDialog(null, "Suppression effectué avec succées", "Success",JOptionPane.INFORMATION_MESSAGE);
                                try {
                                    connexion2.close();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                       } 
                    });
                }
            }
        });

        //ajouter les elements aux panels
        panels[0].add(labels[0]);
        panels[4].add(labels[1]);panels[4].add(combos[0]);
        panels[5].add(boutons[0]);panels[5].add(combos[1]);
        panels[6].add(panels[4]);panels[6].add(panels[5]);
        panels[3].add(panels[6],BorderLayout.NORTH);panels[3].add(sp,BorderLayout.CENTER);panels[2].add(boutons[1],BorderLayout.WEST);
        panels[1].add(panels[3]);
        panels[2].add(labels[2],BorderLayout.EAST);

        //ajouter les panels au fenétre
        add(panels[0],BorderLayout.NORTH);
        add(panels[1],BorderLayout.CENTER);
        add(panels[2],BorderLayout.SOUTH);

        //apparaitre la fenétre
        setVisible(true);
    }

    //fonction qui crée une connexion avec le serveur mysql
    public Connection etablirConnexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/university","root","");
            return con;
        }   
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    //gérer les cliques sur les boutons
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==boutons[1]){
            this.dispose();
            new MainFrame();
        }
        if(e.getSource()==boutons[0]){
            if (combos[0].getSelectedItem()=="" || combos[1].getSelectedItem()==""){
                JOptionPane.showMessageDialog(null,"Vous devez choisir les critéres de recherches !","Missing search data",JOptionPane.ERROR_MESSAGE);
            }
            else{
                try{
                    Connection connexion1 = etablirConnexion();
                    String requete = "select * from student where spec = '"+combos[1].getSelectedItem()+"'"+" or club = '"+combos[1].getSelectedItem()+"'";
                    PreparedStatement st = connexion1.prepareStatement(requete);
                    ResultSet res = st.executeQuery(requete);
                    dataien.setRowCount(0);
                    //vérifier s'il y a des résultat ou non
                    if (!res.isBeforeFirst()){
                        JOptionPane.showMessageDialog(null,"Aucun résultat correspond a votre recherche","No data",JOptionPane.INFORMATION_MESSAGE);
                    }
                    while (res.next()) {
                        String[] rechdata = new String[]{res.getString("nom"),res.getString("prenom"),res.getString("spec"),res.getString("club")};
                        dataien.insertRow(0, rechdata);
                    }
                    connexion1.close();

                }
                catch(Exception ex){
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Vérifier que le serveur mysql est lancé", "Connexion échoué", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(e.getSource()==combos[0]){
            if(combos[0].getSelectedItem()=="Spécialité"){
                combos[1].removeAllItems();
                for(int i=0;i<Specialité.length;i++){
                    combos[1].addItem(Specialité[i]);
                }
            }
            if(combos[0].getSelectedItem()=="Club"){
                combos[1].removeAllItems();
                for(int i=0;i<club.length;i++){
                    combos[1].addItem(club[i]);
                }
            }
            if(combos[0].getSelectedItem()==""){
                combos[1].removeAllItems();
            }
        }
    }
}
