package funciones;
import java.sql.SQLException;
import java.util.Scanner;

import conectorBD.conectorBD;

public class Menus {
	private  static Scanner sc= new Scanner(System.in);


	 public static void menuInicial() throws SQLException {
		int opcion;
		
		conectorBD.conectar();
		

		
		System.out.println("Bienvenido a GlobalCinesa elige una opcion:");
		System.out.println("1. Registrarse");
		System.out.println("2. Iniciar sesion");
		opcion=sc.nextInt();
		
		switch (opcion) {
		
		case 1:
			registrarse();
			break;
		case 2:
			iniciarSesion();
			break;
		}
	}
		
	public static void registrarse() {
		
		System.out.println("Pon tu nombre: ");
		
		System.out.println("Pon tu email:");
		
		System.out.println("Pon la contraseña: ");
		
		
		
	}
	
	public static void iniciarSesion() {

		System.out.println("Pon tu email:");
		
		System.out.println("pon la contraseña: ");
		
		
	}
		
		
		
	}
	
	

