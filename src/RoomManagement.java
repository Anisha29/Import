import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class room extends JFrame
{
	Connection con;
	PreparedStatement stmt;
	JTextField txtrent,txtSeat;
	JComboBox ROOM;
	JButton Save,New,Delete,Modify,Close;
	JRadioButton Ac,nonAc;
   JLabel RM,prm,ps,pr;
   ButtonGroup grp;
   
   
	void showUi(JComponent comp,int x,int y,int w,int h)
	{
		comp.setBounds(x,y,w,h);
		add(comp);
		
	}
	
	
	void addd()
	{
		setLayout(null);
		RM=new JLabel("Room Management");
		showUi(RM,10,10,200,100);
		prm=new JLabel("Room");
		showUi(prm,30,100,50,30);
		ROOM=new JComboBox();
		showUi(ROOM,100,100,100,30);
		ROOM.setEditable(true);
		pr=new JLabel("RENT");
		showUi(pr,30,160,50,30);
		txtrent=new JTextField("");
		showUi(txtrent,100,160,100,30);
		ps=new JLabel("SEATS");
		showUi(ps,30,210,50,30);
		txtSeat=new JTextField("");
		showUi(txtSeat,100,210,100,30);
		
		Save=new JButton("SAVE");
		showUi(Save,250,80,100,30);
		
		Save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try
				{
					dosave();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		New=new JButton("NEW");
		showUi(New,250,140,100,30);
		New.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ROOM.setSelectedItem("");
				txtrent.setText("");
				txtSeat.setText("");
				grp.clearSelection();
			}
		});
		
		Delete=new JButton("DELETE");
		showUi(Delete,250,200,100,30);
		
		Delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					dodelete();
					
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		Modify=new JButton("MODIFY");
		showUi(Modify,250,260,100,30);
		Close=new JButton("CLOSE");
		showUi(Close,250,320,100,30);
		Close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				
			}
		} );
		
		grp=new ButtonGroup();
		
		Ac=new JRadioButton("AC");
		showUi(Ac,30, 300, 100, 30);
		
		nonAc=new JRadioButton("NON AC");
		showUi(nonAc,30, 360, 100, 30);
		
		grp.add(Ac);
		grp.add(nonAc);
		
		
		
		setSize(700,700);
		setVisible(true);
		
		
	}
	void dosave()throws Exception
	{
		String r="";
		if(Ac.isSelected())
		{
			r="Ac";
		}
		else
		{
			if(nonAc.isSelected())
			{
				r="nonAc";
			}
		}
		
		stmt=con.prepareStatement("insert into Room(RoomNo,Seat,Type,Charges,RSeats) values(?,?,?,?,0)");
		stmt.setInt(1,Integer.parseInt(ROOM.getSelectedItem().toString()));
		stmt.setInt(2,Integer.parseInt(txtSeat.getText()));
		
		stmt.setString(3,r);
		stmt.setInt(4,Integer.parseInt(txtrent.getText()));
		
		int resp=stmt.executeUpdate();
		if(resp!=0)
		{
			JOptionPane.showMessageDialog(null,"Record Saved...");
			
		}
		
	}
	void fillRoom() throws Exception
	{
	
		System.out.println(ROOM.getSelectedItem());
		stmt=con.prepareStatement("select distinct ROOM from room ");

	
		ROOM.addItem("select"); 
		 ResultSet resp=stmt.executeQuery();
		while(resp.next())
		{
			ROOM.addItem(resp.getString("Room"));
		}
	}
	
	void dodelete()throws Exception
	{
		stmt=con.prepareStatement("delete from room where RoomNo=?");
		stmt.setString(1,ROOM.getSelectedItem().toString());
		int resp=stmt.executeUpdate();
		if(resp!=0)
		{
			JOptionPane.showMessageDialog(null,"Record deleted...");
			
		}
	}
	
	void doupdate()throws Exception
	{
		stmt=con.prepareStatement("update room set RoomNo=?,Seat=?,Charges=?");
		stmt.setInt(1,Integer.parseInt(ROOM.getSelectedItem().toString()));
		stmt.setInt(2,Integer.parseInt(txtSeat.getText()));
		stmt.setInt(3,Integer.parseInt(txtrent.getText()));
		
		int resp=stmt.executeUpdate();
		if(resp!=0)
		{
			JOptionPane.showMessageDialog(null,"Record updated...");
			
		}
		
	}
	
	
	



room()
{
	addd();
	try{
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver Loaded");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pg record","root","sst");
		System.out.println("Connection established");
	
}catch(Exception ex){
	ex.printStackTrace();
}
}
}

public class RoomManagement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
new room();

	}

}
