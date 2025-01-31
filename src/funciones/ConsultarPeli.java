package funciones;

import java.util.Scanner;
import Clases.Pelicula;
import Clases.Videoclub;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import funciones.Menus;
import conectorBD.conectorBD;
import java.sql.Statement;

public class ConsultarPeli {
	public static Scanner scanner = new Scanner(System.in);

	public static void MostarPelisPorGenero(String genero, Videoclub videoclub) throws SQLException {
		System.out.println("\n--- Mostrar Peliculas por genero ---");

		// Hace la consulta para filtrar las pelis por genero
		String query = "SELECT * FROM pelicula WHERE genero = ?";
		try (PreparedStatement preparedStatement = conectorBD.conexion.prepareStatement(query)) {
			preparedStatement.setString(1, genero);
			ResultSet resultSet = preparedStatement.executeQuery();
			// Si no se se encuentra el genero muestra el mensaje
			if (!resultSet.isBeforeFirst()) {
				System.out.println("No se encontraron peliculas con ese genero: " + genero);
			} else {
				// Si el genero concuerda muestra las peliculas
				while (resultSet.next()) {
					System.out.println("Nombre: " + resultSet.getString("nombre") + ", Codigo: "
							+ resultSet.getInt("codigo") + ", Autor: " + resultSet.getString("autor") + ", Precio: "
							+ resultSet.getInt("precio") + ", Genero: " + resultSet.getString("genero"));

				}
			}
		}

		System.out.println("1. volver al menu principal");
		int opcion = scanner.nextInt();
		switch (opcion) {

		case 1:
			Menus.menuSecundario(scanner, videoclub);
			break;
		}
	}

	public static void MostarPelisPorPrecio(double precio, Videoclub videoclub) throws SQLException {
		System.out.println("\n--- Mostrar peliculas por precio inferior al introducido ---");

		String query = "SELECT * FROM pelicula WHERE precio < ?";
		try (PreparedStatement preparedStatement = conectorBD.conexion.prepareStatement(query)) {
			preparedStatement.setDouble(1, precio);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				System.out.println("No se encontraron peliculas con precio inferior: " + precio);
			} else {
				while (resultSet.next()) {
					System.out.println("Nombre: " + resultSet.getString("nombre") + ", Codigo: "
							+ resultSet.getInt("codigo") + ", Autor: " + resultSet.getString("autor") + ", Precio: "
							+ resultSet.getInt("precio") + ", Genero: " + resultSet.getString("genero"));

				}
			}
		}

		System.out.println("1. volver al menu principal");
		int opcion = scanner.nextInt();
		switch (opcion) {

		case 1:
			Menus.menuSecundario(scanner, videoclub);
			break;
		}
	}

	public static void realizarReserva(int codigo, String dni, Videoclub videoclub) throws SQLException {
		// Consulta para verificar si la película existe
		String queryCheck = "SELECT COUNT(*) FROM pelicula WHERE codigo = ?";
		String queryInsert = "INSERT INTO reserva (codigo, fechaReserva, fechaEntrega, dni, codigoPelicula) VALUES (DEFAULT, ?, NULL, ?, ?)";

		// Comprobar si la película ya existe
		try (PreparedStatement checkStmt = conectorBD.conexion.prepareStatement(queryCheck)) {

			checkStmt.setInt(1, codigo);

			try (ResultSet resultSet = checkStmt.executeQuery()) {
				if (resultSet.next() && resultSet.getInt(1) == 0) {
					System.out.println("La película con código " + codigo
							+ " no existe en la base de datos y no se insertará la reserva.");
					return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e; // Re-lanzamos la excepción para que el llamador pueda manejarla

		}
		// Insertar la reserva si la película existe
		try (PreparedStatement preparedStatement = conectorBD.conexion.prepareStatement(queryInsert)) {
			Timestamp timestampActual = Timestamp.valueOf(LocalDateTime.now());
			preparedStatement.setTimestamp(1, timestampActual);
			preparedStatement.setString(2, dni);
			preparedStatement.setInt(3, codigo);

			int filasAfectadas = preparedStatement.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Reserva realizada con éxito.");
			} else {
				System.out.println("No se pudo realizar la reserva.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void MostrarPeliculaPorNombre(String nombre, Videoclub videoclub) throws SQLException {
		System.out.println("\n--- Mostrar Peliculas por nombre ---");
		System.out.print("Ingresa el genero: ");

		String query = "SELECT * FROM pelicula WHERE nombre = ?";
		try (PreparedStatement preparedStatement = conectorBD.conexion.prepareStatement(query)) {
			preparedStatement.setString(1, nombre);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				System.out.println("No se encontro la pelicula con ese nombre: " + nombre);
			} else {
				while (resultSet.next()) {
					System.out.println("Nombre: " + resultSet.getString("nombre") + ", Codigo: "
							+ resultSet.getInt("codigo") + ", Autor: " + resultSet.getString("autor") + ", Precio: "
							+ resultSet.getInt("precio") + ", Genero: " + resultSet.getString("genero"));

				}
			}
		}
		System.out.println("1. volver al menu principal");
		int opcion = scanner.nextInt();
		switch (opcion) {

		case 1:
			Menus.menuSecundario(scanner, videoclub);
			break;
		}
	}

	public static void MostrarTodasPeliculas(Object object, Videoclub videoclub) throws SQLException {
		// Introduce el genero por el que quieres que te clasifique
		System.out.println("\n--- Mostrar Peliculas por nombre ---");
		System.out.print("Ingresa el genero: ");

		// HAce la consulta para filtrar
		String query = "SELECT * FROM pelicula";
		try (PreparedStatement preparedStatement = conectorBD.conexion.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				System.out.println("Hubo un error");
			} else {
				while (resultSet.next()) {
					// Muestra las peliculas con el genero pedido
					System.out.println("Todas las peliculas");
					System.out.println("Nombre: " + resultSet.getString("nombre") + ", Codigo: "
							+ resultSet.getInt("codigo") + ", Autor: " + resultSet.getString("autor") + ", Precio: "
							+ resultSet.getInt("precio") + ", Genero: " + resultSet.getString("genero"));

				}
			}
		}
		System.out.println("1. volver al menu principal");
		int opcion = scanner.nextInt();
		switch (opcion) {

		case 1:
			Menus.menuSecundario(scanner, videoclub);
			break;
		}
	}

}