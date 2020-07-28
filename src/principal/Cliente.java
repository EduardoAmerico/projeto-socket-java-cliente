package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextField;


import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

public class Cliente {

	private static Socket socket;

	private JFrame frame;
	private JTextField tfSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cliente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Digite sua senha para ser criptografada");
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		lblNewLabel.setBounds(25, 11, 374, 51);
		frame.getContentPane().add(lblNewLabel);

		tfSenha = new JTextField();
		tfSenha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
		tfSenha.setBounds(131, 75, 268, 20);
		frame.getContentPane().add(tfSenha);
		tfSenha.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		lblSenha.setBounds(71, 73, 50, 20);
		frame.getContentPane().add(lblSenha);

		JLabel lblCriptografada = new JLabel("Criptografada:");
		lblCriptografada.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		lblCriptografada.setBounds(25, 109, 96, 20);
		frame.getContentPane().add(lblCriptografada);
		
		JTextArea tfCripto = new JTextArea();
		tfCripto.setLineWrap(true);
		tfCripto.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfCripto.setBounds(131, 109, 268, 92);
		frame.getContentPane().add(tfCripto);

		JButton btnNewButton = new JButton("Criptografar");
		btnNewButton.setBounds(286, 212, 114, 23);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String senha = tfSenha.getText();
				String senhaAux = senha;
				if(tfSenha.getText().length() > 3) {
					 senha = iniciaCliente(tfSenha.getText());
					 
					 if(senha != null) {
						 if(senha.equals(senhaAux)) {
							 tfCripto.removeAll();
							 JOptionPane.showMessageDialog(null, "Ouve um problema e a senha não pode ser alterada!!!");
						 }else {
							 tfCripto.setText(senha);
						 }
					 }
					  
					 
				}else {
					JOptionPane.showMessageDialog(null, "Senha muito pequena!! /n Por favor digite uma senha maior");
				}
				
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		
	}

	private static String iniciaCliente(String senha) {
		String senhaNova = null;
		try {

			socket = new Socket("localhost", 55555);
			System.out.println("Cliente cinseguiu se conectar no servidor na porta: 5555");
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream imput = new ObjectInputStream(socket.getInputStream());

			String msg = senha;
			output.writeUTF(msg);
			output.flush();

			senhaNova = imput.readUTF();
			System.out.println("Senha criptografada é: " + senhaNova);

			output.close();
			imput.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro!!, não conseguiu se conectar ao servidor");
			System.out.println("Erro: " + e.getMessage());
		}
		return senhaNova;
	}
}
