

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.event.*;
import  javax.swing.table.*;

public class Testing123 extends JFrame {

    Connection connection;
    Container container, content;
    public static final long serialVersionUID = 3001301003546347753L;
    public ResultSet rs;
    public JButton jbuttonDelete,buttons,  arrangeComponents,jbuttonSave,  jbuttonUpdate,  jbuttonExit,
            jbuttonSearch,  jbuttonInventory,  jbuttonPurchase,  jbuttonViewBlog, 
            jbuttonAuthenticate,  jbuttonPayCreditCard,  jbuttonShipped,  jbuttonViewComments;
    public JTextField jtxtFName, query,Address,  jtxtFEmail,jtxtFPhonNum,jtxtFAddress;
    public JLabel jlabelFirstName, msgline, jlabelLastName,  jlabelAddress,  jlabelPhonNum,  jlabelEmail;
    public JFrame queryFrame, g,createFrame,  arrange,gui,  viewUser,User,  addUser,  applicationFrame;
    private ResultSetTableModelE_sales e_sales;
    private Introduce some;
    public Testing123() {

        try {
           //createGUI();
          this.some = new Testing123.Introduce();
          some.setVisible(true);
           this.e_sales = new ResultSetTableModelE_sales();
            this.queryFrame = new QueryFrame(e_sales);

           
            // Set the size of the QueryFrame, then pop it up
          // 
			this.queryFrame.setSize(1000, 600);
           this.queryFrame.setVisible(true);
                 
                 
        //new_user();
        // do the rest of functionality here
        //..
       //..
        } catch (Exception ex) {
            ex.printStackTrace();
                    }

    }

    public class ResultSetTableModelE_sales extends Introduce {
        
        ResultSetTableModelE_sales() throws ClassNotFoundException, SQLException {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection
            ("jdbc:sqlserver://localhost;database=MosheBlog;integratedSecurity=true;");
        }

        ResultSetTableModel getResultSetTableModel(String query) throws SQLException {

            if (connection == null) {
                throw new IllegalStateException("Connection already closed.");
            }

            Statement statement =
                    connection.createStatement(rs.TYPE_SCROLL_SENSITIVE,
                    rs.CONCUR_UPDATABLE);

             rs = statement.executeQuery(query);
            return new ResultSetTableModel(rs);
        }
    }

    class ResultSetTableModel implements TableModel {

        ResultSet results;             // The ResultSet to interpret
        ResultSetMetaData metadata;    // Additional information about the results
        int numcols, numrows;          // How many rows and columns in the table

        ResultSetTableModel(ResultSet results) throws SQLException {
            this.results = results;                 // Save the results
            metadata = results.getMetaData();       // Get metadata on them
            numcols = metadata.getColumnCount();    // How many columns?
            results.last();                         // Move to last row
            numrows = results.getRow();             // How many rows?
        }

        public void close() {
            try {
                results.getStatement().close();
            } catch (SQLException e) {
            }
        }

        public String getColumnName(int column) {
            try {
                return metadata.getColumnLabel(column + 1);
            } catch (SQLException e) {
                return e.toString();
            }
        }

        public Object getValueAt(int row, int column) {
            try {
                results.absolute(row + 1);                // Go to the specified row
                Object o = results.getObject(column + 1); // Get value of the column
                if (o == null) {
                    return null;
                } else {
                    return o.toString();               // Convert it to a string
                }
            } catch (SQLException e) {
                return e.toString();
            }
        }

        public int getColumnCount() {
            return numcols;
        }

        public boolean isCellEditable(int row, int column) {
            return true;
        }

        public void setValueAt(Object value, int row, int column) {
        }

        public void addTableModelListener(TableModelListener l) {
        }

        public void removeTableModelListener(TableModelListener l) {
        }

        public ResultSet getResultSetTableModel() {
            return rs;
        }
       

        public int getRowCount() {
            return numrows;
        }

        public Class getColumnClass(int column) {
            return String.class;
        }
    }

    class User extends JFrame {

        String title,
                first_Name,
                last_Name,
                email,
                phone_Num,
                password,
                st_Address,
                city_Address,
                zip_Address,
                state_Address,
                country_Address;

        public void setCity_Address(String City_Address) {
            this.city_Address = City_Address;
        }

        public void setEmail(String Email) {
            this.email = Email;
        }

        public void setFirst_Name(String First_Name) {
            this.first_Name = First_Name;
        }

        public void setLast_Name(String Last_Name) {
            this.last_Name = Last_Name;
        }

        public void setPassword(String Password) {
            this.password = Password;
        }

        public void setPhone_Num(String Phone_Num) {
            this.phone_Num = Phone_Num;
        }

        public void setSt_Address(String St_Address) {
            this.st_Address = St_Address;
        }

        public void setState_Address(String State_Address) {
            this.state_Address = State_Address;
        }

        public void setTitle(String Title) {
            this.title = Title;
        }

        public void setZip_Address(String Zip_Address) {
            this.zip_Address = Zip_Address;
        }

        public void setCountry_Address(String Country_Address) {
            this.country_Address = Country_Address;
        }

        public String getCity_Address() {
            return city_Address;
        }

        public String getEmail() {
            return email;
        }

        public String getFirst_Name() {
            return first_Name;
        }

        public String getLast_Name() {
            return last_Name;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone_Num() {
            return phone_Num;
        }

        public String getSt_Address() {
            return st_Address;
        }

        public String getState_Address() {
            return state_Address;
        }

        public String getTitle() {
            return title;
        }

        public String getCountry_Address() {
            return country_Address;
        }

        public String getZip_Address() {
            return zip_Address;
        }
    }

    public class QueryFrame extends JFrame implements ActionListener {

        ResultSetTableModelE_sales e_sales;   // A e_sales to obtain our table data
                            // A field to enter a query in
        JTable table, b;                         // The table for displaying data
        JLabel msgline;                       // For displaying messages
        ActionListener actionListener;


        @SuppressWarnings("LeakingThisInConstructor")
        public QueryFrame(ResultSetTableModelE_sales f) {

          
            super("QueryFrame");  // Set window title
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            
            
            this.e_sales = f;
            query = new JTextField();     // Lets the User enter a query
            table = new JTable();         // Displays the table
            msgline = new JLabel();       // Displays messages
             container = getContentPane();
          container.add(query, BorderLayout.NORTH);
            container.add(new JScrollPane(table), BorderLayout.CENTER);
            container.add(msgline, BorderLayout.SOUTH);//contentPane.add(new arrange);
            
					   container.setLayout(new GridBagLayout());
                        jlabelFirstName = new JLabel("First Name");
                        jlabelLastName = new JLabel("Last Name");
                        jlabelAddress = new JLabel("Address");
                        jlabelPhonNum = new JLabel("Phone");
                        jlabelEmail = new JLabel("Email");
                        jtxtFName = new JTextField(20);
                        jtxtFAddress = new JTextField(40);
                        jtxtFPhonNum = new JTextField(20);
                        jtxtFEmail = new JTextField(20);
                        jbuttonSave = new JButton("Save");jbuttonSave.addActionListener(this);
                        jbuttonPurchase = new JButton("Purchase"); jbuttonPurchase.addActionListener(this);
                       jbuttonPayCreditCard = new JButton("Credit Card Payment");
                        jbuttonUpdate = new JButton("Update");
                        jbuttonAuthenticate = new JButton("Authenticate");
                        jbuttonInventory = new JButton("Inventory");
                        jbuttonViewBlog = new JButton("ViewBlog");
                        jbuttonExit = new JButton("Exit");
                        jbuttonShipped=new JButton("Shipping");
                        jbuttonViewComments= new JButton("Comments");
                        jbuttonSearch=new JButton("Search");
                        jbuttonDelete=new JButton("Delete");
                        /*add all initialized components to the container*/
                        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
                        gridBagConstraintsx01.gridx = 0;
                        gridBagConstraintsx01.gridy = 0;
                        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
                        container.add(jlabelFirstName, gridBagConstraintsx01);

                        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
                        gridBagConstraintsx02.gridx = 1;
                        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx02.gridy = 0;
                        gridBagConstraintsx02.gridwidth = 2;
                        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
                        container.add(jtxtFName, gridBagConstraintsx02);

                        GridBagConstraints gridBagConstraintsx03 = new GridBagConstraints();
                        gridBagConstraintsx03.gridx = 0;
                        gridBagConstraintsx03.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx03.gridy = 1;
                        container.add(jlabelAddress, gridBagConstraintsx03);

                        GridBagConstraints gridBagConstraintsx04 = new GridBagConstraints();
                        gridBagConstraintsx04.gridx = 1;
                        gridBagConstraintsx04.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx04.gridy = 1;
                        gridBagConstraintsx04.gridwidth = 2;
                        gridBagConstraintsx04.fill = GridBagConstraints.BOTH;
                        container.add(jtxtFAddress, gridBagConstraintsx04);

                        GridBagConstraints gridBagConstraintsx05 = new GridBagConstraints();
                        gridBagConstraintsx05.gridx = 0;
                        gridBagConstraintsx05.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx05.gridy = 2;
                        container.add(jlabelPhonNum, gridBagConstraintsx05);

                        GridBagConstraints gridBagConstraintsx06 = new GridBagConstraints();
                        gridBagConstraintsx06.gridx = 1;
                        gridBagConstraintsx06.gridy = 2;
                        gridBagConstraintsx06.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx06.gridwidth = 2;
                        gridBagConstraintsx06.fill = GridBagConstraints.BOTH;
                        container.add(jtxtFPhonNum, gridBagConstraintsx06);

                        GridBagConstraints gridBagConstraintsx07 = new GridBagConstraints();
                        gridBagConstraintsx07.gridx = 0;
                        gridBagConstraintsx07.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx07.gridy = 3;
                        container.add(jlabelEmail, gridBagConstraintsx07);

                        GridBagConstraints gridBagConstraintsx08 = new GridBagConstraints();
                        gridBagConstraintsx08.gridx = 1;
                        gridBagConstraintsx08.gridy = 3;
                        gridBagConstraintsx08.gridwidth = 2;
                        gridBagConstraintsx08.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx08.fill = GridBagConstraints.BOTH;
                        container.add(jtxtFEmail, gridBagConstraintsx08);


                        GridBagConstraints gridBagConstraintsx09 = new GridBagConstraints();
                        gridBagConstraintsx09.gridx = 0;
                        gridBagConstraintsx09.gridy = 4;
                        gridBagConstraintsx09.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonSave, gridBagConstraintsx09);


                        GridBagConstraints gridBagConstraintsx10 = new GridBagConstraints();
                        gridBagConstraintsx10.gridx = 1;
                        gridBagConstraintsx10.gridy = 4;
                        gridBagConstraintsx10.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonInventory, gridBagConstraintsx10);


                        GridBagConstraints gridBagConstraintsx11 = new GridBagConstraints();
                        gridBagConstraintsx11.gridx = 2;
                        gridBagConstraintsx11.gridy = 4;
                        gridBagConstraintsx11.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonPurchase, gridBagConstraintsx11);


                        GridBagConstraints gridBagConstraintsx12 = new GridBagConstraints();
                        gridBagConstraintsx12.gridx =3;
                        gridBagConstraintsx12.gridy = 4;
                        gridBagConstraintsx12.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonViewBlog, gridBagConstraintsx12);


                        GridBagConstraints gridBagConstraintsx13 = new GridBagConstraints();
                        gridBagConstraintsx13.gridx = 0;
                        gridBagConstraintsx13.gridy = 5;
                        gridBagConstraintsx13.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonAuthenticate, gridBagConstraintsx13);


                        GridBagConstraints gridBagConstraintsx14 = new GridBagConstraints();
                        gridBagConstraintsx14.gridx = 1;
                        gridBagConstraintsx14.gridy = 5;
                        gridBagConstraintsx14.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonPayCreditCard, gridBagConstraintsx14);


                        GridBagConstraints gridBagConstraintsx15 = new GridBagConstraints();
                        gridBagConstraintsx15.gridx = 2;
                        gridBagConstraintsx15.insets = new Insets(5, 5, 5, 5);
                        gridBagConstraintsx15.gridy =5;
                        container.add(jbuttonShipped, gridBagConstraintsx15);


                        GridBagConstraints gridBagConstraintsx16 = new GridBagConstraints();
                        gridBagConstraintsx16.gridx = 3;
                        gridBagConstraintsx16.gridy = 5;
                        gridBagConstraintsx16.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonViewComments, gridBagConstraintsx16);

                        GridBagConstraints gridBagConstraintsx17 = new GridBagConstraints();
                        gridBagConstraintsx17.gridx = 0;
                        gridBagConstraintsx17.gridy = 6;
                        gridBagConstraintsx17.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonUpdate, gridBagConstraintsx17);


                        GridBagConstraints gridBagConstraintsx18 = new GridBagConstraints();
                        gridBagConstraintsx18.gridx = 1;
                        gridBagConstraintsx18.gridy = 6;
                        gridBagConstraintsx18.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonDelete, gridBagConstraintsx18);


                        GridBagConstraints gridBagConstraintsx19 = new GridBagConstraints();
                        gridBagConstraintsx19.gridx = 2;
                        gridBagConstraintsx19.gridy = 6;
                        gridBagConstraintsx19.insets = new Insets(5, 5, 5, 5);
                        container.add(jbuttonSearch, gridBagConstraintsx19);

                       }
                    
               
                
                //arrangeComponents arrange = new arrangeComponents();
              
                 
           //   }
           
           
           
           
           
            
                          void saveUser() {
                        //displayQueryResults(query.addUser());
                        }

                        void searchUser() {
                        }

                        void deleteUser() {
                         }

                        void updateUser() { System.out.println("hello");
						displayQueryResults("select * from saleslt.customer;");
                        }

                        void makePurchaseUser() {
                        }

                        void displayInventoryForUser() {displayQueryResults("select * from saleslt.product;");
                        }

                        void viewBlog() {
                        }

                        void LoginAuthentication() {viewUser();
                        }

                        void payCreditCardUser() {
                        }

                        void murchendiseShippedUser() {
                        }

                       void viewCommentsUser() {
                        }

                        void reviewPurchasesUser() {
                        }
                        void    LoginAuthenticateUser() {
                        }
                        
                       
                       
                    ActionListener me = new ActionListener() {
                         public void actionPerformed(ActionEvent e) {
                      //query.addActionListener(actionListener);
                        /*    jbuttonViewComments.addActionListener( this);
                        jbuttonExit.addActionListener(this);
                        jbuttonShipped.addActionListener(this);
                        jbuttonSave.addActionListener(this);
                        jbuttonSave.addActionListener(this);
                        jbuttonDelete.addActionListener(this);
                        jbuttonUpdate.addActionListener(this);
                        jbuttonSearch.addActionListener(this);
                        jbuttonPurchase.addActionListener(this);
                        jbuttonInventory.addActionListener(this);
                        jbuttonViewBlog.addActionListener(this);
                        jbuttonAuthenticate.addActionListener(this);*/
                       
                         //displayQueryResults(query.getText());
                         if (e.getSource() == jbuttonSave) {System.out.println("jbuttonsave");
						displayQueryResults(query.getText());

                        } else if (e.getSource() == jbuttonDelete) {
						deleteUser();

			} else if (e.getSource() == jbuttonUpdate) {displayQueryResults("select * from saleslt.customer;");
                                    updateUser();
                                    

			} else if (e.getSource() == jbuttonSearch) {displayQueryResults("select * from saleslt.customer;");
					searchUser();
			} else if (e.getSource() == jbuttonPurchase) {
						makePurchaseUser();
			} else if (e.getSource() == jbuttonInventory) {
						displayInventoryForUser();
			} else if (e.getSource() == jbuttonViewBlog) {displayQueryResults("select * from saleslt.customer;");
						viewBlog();
			} else if (e.getSource() == jbuttonAuthenticate) {
						LoginAuthenticateUser();

			} else if (e.getSource() == jbuttonPayCreditCard) {
						payCreditCardUser();

			} else if (e.getSource() == jbuttonShipped) {
						murchendiseShippedUser();
			} else if (e.getSource() == jbuttonViewComments) {//displayQuery
						viewCommentsUser();

			} else if (e.getSource() == jbuttonAuthenticate) {
						reviewPurchasesUser();

			}  else if (e.getSource() == jbuttonExit) {
						System.exit(0);
			}
                        
                        
                        
                        container.setSize(500,500);
                        container.setVisible(true);
                    } 
               
            
                    };  
             
            public void displayQueryResults( String q) {
            msgline.setText("Contacting database...");
             System.out.println("display QueryResults");
            System.out.println("hheeeyy");
             System.out.println(q);

            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    try {
                        table.setModel(e_sales.getResultSetTableModel(q));
                        msgline.setText(" ");
                        } catch (SQLException ex) {
                        msgline.setText(" ");
                        JOptionPane.showMessageDialog(QueryFrame.this,
                                new String[] // Display a 2-line message
                                {ex.getClass().getName() + ": ",
                                    ex.getMessage()
                                });
                        }
                   }
            });
           
        
       }

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
      }   
    
		public class Introduce extends JFrame{

                     private JTextField item1;
                     private JTextField item2;
                     //private JTextField item3;
                     private JPasswordField passwordField;
                     public Introduce(){
                     	super("please login");
                     	setLayout(new FlowLayout());

                     	item1= new JTextField(10);
                     	add(item1);


                     	item2= new JTextField("login");
                     	add(item2);


                     	passwordField= new JPasswordField("mypass");
                     	add(passwordField);

                     	theEvent handle= new theEvent();
                     	item1.addActionListener(handle);
                     	item2.addActionListener(handle);
                     	passwordField.addActionListener(handle);
                     }
                         class theEvent implements ActionListener{
                            public void actionPerformed(ActionEvent event){
                            String string ="";
                           

         			if (event.getSource()==item1)
                                    string = String.format("field 1: %s", event.getActionCommand());
         			else if (event.getSource()==item2)
         				string = String.format("field 2: %s", event.getActionCommand());
         			else if (event.getSource()==passwordField)
         				string = String.format("password field is : %s", event.getActionCommand());
         			JOptionPane.showMessageDialog(null, string);
         		}
                    }
                }

                public class createGUI extends Introduce{
                    createGUI() {
                     Introduce intro = new Introduce();//JFrame("intro");
                     intro.setVisible(true);
                     intro.setSize(500,600);

                     JFrame.setDefaultLookAndFeelDecorated(true);
                     intro.setDefaultCloseOperation(intro.EXIT_ON_CLOSE);
                     JLabel label = new JLabel("WELCOME TO OUR ESALES SITE\n" +
                     "if you are a new user please create yourself an account\n" +
                     "else please log in");
                    createGUI gui= new createGUI();
                    	JPanel panel= new JPanel();
                    	panel.add(label);
                    	intro.add(panel);
                        
                        /*Create a frame, get its contentpane and set layout*/
                    applicationFrame = new  JFrame("intro");
                        container = applicationFrame.getContentPane();
                        container.setLayout(new GridBagLayout());
                        //Arrange components on contentPane and set Action Listeners to each JButton

                        
                        applicationFrame.setSize(240, 300);
                        //applicationFrame.setResizable(false);
                        applicationFrame.setVisible(true);

                    }
                }
                 
          public void querydUser() {
            User user;
                try {
            @SuppressWarnings("LocalVariableHidesMemberVariable")
            String query = "select saleslt.customer where   name=?, address=?, phn=?, email=?," + " password=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, (user.getTitle() + user.getFirst_Name() + user.getLast_Name()));
            ps.setString(2, (user.getSt_Address() + user.getCity_Address() + user.getState_Address() + user.getZip_Address() + user.getCountry_Address()));
            ps.setString(3, user.getPhone_Num());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();
           QueryFrame.displayQueryResults(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
            
        public void deleteUser(User user) {
        try {
            String query = "Drop customer set name=?, address=?, phn=?, email=?," + " password=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, (user.getTitle() + user.getFirst_Name() + user.getLast_Name()));
            ps.setString(2, (user.getSt_Address() + user.getCity_Address() + user.getState_Address() + user.getZip_Address() + user.getCountry_Address()));
            ps.setString(3, user.getPhone_Num());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
                
             
       public void addUser(User user) {
        try {
            String query = "Update customer set name=?, address=?, phn=?, email=?," + " password=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, (user.getTitle() + user.getFirst_Name() + user.getLast_Name()));
            ps.setString(2, (user.getSt_Address() + user.getCity_Address() + user.getState_Address() + user.getZip_Address() + user.getCountry_Address()));
            ps.setString(3, user.getPhone_Num());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public   void viewUser() {
      User user;
      try {
            String query = "Select custumer=? from moshedbc.custumer.lastname=? and firstname=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, (user.getTitle() + user.getFirst_Name() + user.getLast_Name()));
            ps.setString(2, user.getLast_Name());
            ps.setString(3, user.getFirst_Name());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        Testing123 b = new Testing123();
    }
}
    

