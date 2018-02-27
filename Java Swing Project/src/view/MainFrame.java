package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import controller.AllOperations;
import model.Student;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame {

	private JFrame frmManagementSystem;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldSession;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmManagementSystem = new JFrame();
		frmManagementSystem.setTitle("Management System");
		frmManagementSystem.setBounds(100, 100, 725, 479);
		frmManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManagementSystem.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 689, 418);
		frmManagementSystem.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 193, 396);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblStudentId = new JLabel("Student ID");
		lblStudentId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStudentId.setBounds(10, 11, 173, 26);
		panel_1.add(lblStudentId);

		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldID.setBounds(10, 48, 173, 26);
		panel_1.add(textFieldID);
		textFieldID.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(10, 85, 173, 26);
		panel_1.add(lblName);

		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldName.setBounds(10, 122, 173, 26);
		panel_1.add(textFieldName);
		textFieldName.setColumns(10);

		JLabel lblSession = new JLabel("Session");
		lblSession.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSession.setBounds(10, 159, 173, 26);
		panel_1.add(lblSession);

		textFieldSession = new JTextField();
		textFieldSession.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldSession.setBounds(10, 196, 173, 26);
		panel_1.add(textFieldSession);
		textFieldSession.setColumns(10);

		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id = textFieldID.getText().trim();
				String name = textFieldName.getText().trim();
				String session = textFieldSession.getText().trim();
				
				Student student = new Student();
				student.setId(id);
				student.setName(name);
				student.setSession(session);
				
				AllOperations operations = new AllOperations();
				operations.writeToFile(student, true);
				
				JOptionPane.showMessageDialog(null, "Information saved");
				textFieldID.setText("");
				textFieldName.setText("");
				textFieldSession.setText("");
			}
		});
		btnAddStudent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAddStudent.setBounds(10, 233, 173, 23);
		panel_1.add(btnAddStudent);

		JButton btnViewAllStudents = new JButton("View All Students");
		btnViewAllStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Student> list = new ArrayList();
				
				AllOperations operations = new AllOperations();
				list = operations.readFromFile();
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				
				for (Student student : list) {
					model.addRow(new Object[] {student.getId(), student.getName(), student.getSession()});
				}
			}
		});
		btnViewAllStudents.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewAllStudents.setBounds(10, 267, 173, 23);
		panel_1.add(btnViewAllStudents);

		JButton btnUpdateStudent = new JButton("Update Student");
		btnUpdateStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getRowCount() == 0 || table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Select a row first", "Inane error", JOptionPane.ERROR_MESSAGE);
				} else {
					String id = textFieldID.getText().trim();
					String name = textFieldName.getText().trim();
					String session = textFieldSession.getText().trim();
					
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setValueAt(id, table.getSelectedRow(), 0);
					model.setValueAt(name, table.getSelectedRow(), 1);
					model.setValueAt(session, table.getSelectedRow(), 2);
					
					ArrayList<Student> list = new ArrayList();
					for (int i = 0; i < table.getRowCount(); i++) {
						id = (String) model.getValueAt(i, 0);
						name = (String) model.getValueAt(i, 1);
						session = (String) model.getValueAt(i, 2);
						
						Student student = new Student();
						student.setId(id);
						student.setName(name);
						student.setSession(session);
						
						list.add(student);
					}
					
					AllOperations allOperations = new AllOperations();
					allOperations.updateIntoFile(list, true);
				}
			}
		});
		btnUpdateStudent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdateStudent.setBounds(10, 301, 173, 23);
		panel_1.add(btnUpdateStudent);

		JButton btnDeleteStudent = new JButton("Delete Student");
		btnDeleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getRowCount() == 0 || table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Select a row first", "Inane error", JOptionPane.ERROR_MESSAGE);
				} else {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.removeRow(table.getSelectedRow());
					
					ArrayList<Student> list = new ArrayList();
					for (int i = 0; i < table.getRowCount(); i++) {
						String id = (String) model.getValueAt(i, 0);
						String name = (String) model.getValueAt(i, 1);
						String session = (String) model.getValueAt(i, 2);
						
						Student student = new Student();
						student.setId(id);
						student.setName(name);
						student.setSession(session);
						
						list.add(student);
					}
					
					AllOperations allOperations = new AllOperations();
					allOperations.updateIntoFile(list, true);
				}
			}
		});
		btnDeleteStudent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeleteStudent.setBounds(10, 335, 173, 23);
		panel_1.add(btnDeleteStudent);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExit.setBounds(10, 369, 173, 23);
		panel_1.add(btnExit);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(213, 11, 466, 396);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 446, 374);
		panel_2.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String id = model.getValueAt(table.getSelectedRow(), 0).toString();
				String name = model.getValueAt(table.getSelectedRow(), 1).toString();
				String session = model.getValueAt(table.getSelectedRow(), 2).toString();
				
				textFieldID.setText(id);
				textFieldName.setText(name);
				textFieldSession.setText(session);
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Session" }));
		scrollPane.setViewportView(table);
	}
}
