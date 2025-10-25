package com.mycompany.nomadia;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class Nomadia {
    public static void main(String[] args) {
        Connection conn = ConexionDB.getConnection();

        if (conn == null) {
            System.out.println("No se pudo conectar a la base de datos. Saliendo.");
            return;
        }

        GestorUsuario gestorUsuario = new GestorUsuario(conn);
        GestorPropiedad gestorPropiedad = new GestorPropiedad(conn);
        GestorResenia gestorResenia = new GestorResenia(conn);
        GestorReserva gestorReserva = new GestorReserva(conn);

        Scanner read = new Scanner(System.in);
        int opc1 = 0;
        int opc2 = 0;
        int opc3 = 0;
        int opc4 = 0;

        do {
            System.out.println("\n===== Menú Nomadia =====");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Propiedades");
            System.out.println("3. Gestionar Reservas");
            System.out.println("4. Gestionar Reseñas");
            System.out.println("5. Salir");
            opc1 = Leer.leerInt(read, "Seleccione una opción: ");

            switch (opc1) {

                case 1:
                    do {
                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Agregar Usuario");
                        System.out.println("2. Actualizar Usuario");
                        System.out.println("3. Eliminar Usuario");
                        System.out.println("4. Mostrar Usuarios");
                        System.out.println("5. Volver");
                        opc2 = Leer.leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1:
                                do {
                                    System.out.println("\n¿Qué desea agregar?");
                                    System.out.println("1. Agregar Inquilino");
                                    System.out.println("2. Agregar Anfitrión");
                                    System.out.println("3. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            Usuario.crearUsuario(read, gestorUsuario, "Inquilino");
                                            break;

                                        case 2:
                                            Usuario.crearUsuario(read, gestorUsuario, "Anfitrion");
                                            break;

                                        case 3:
                                            System.out.println("\nVolviendo a gestionar usuarios...");
                                            break;

                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 3);
                                break;

                            case 2:
                                int id = Leer.leerInt(read, "\nIngrese el ID del usuario que desea modificar: ");

                                if (!gestorUsuario.existeUsuario(id)) {
                                    System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de usuarios.");
                                    break;
                                }

                                System.out.println("\nDatos actuales del usuario:");
                                gestorUsuario.mostrarUsuarioPorId(id);

                                String tipoUsuario = gestorUsuario.obtenerTipoUsuario(id);

                                do {
                                    String opc3TextoEspecial = "3. (Sin acciones de tipo disponibles)";
                                    String accionEspecial = "ninguna";

                                    if ("Inquilino".equals(tipoUsuario)) {
                                        opc3TextoEspecial = "3. Ascender Inquilino a Inquilino Premium";
                                        accionEspecial = "ascender";
                                    } else if ("InquilinoPremium".equals(tipoUsuario)) {
                                        opc3TextoEspecial = "3. Descender Inquilino Premium a Inquilino";
                                        accionEspecial = "descender";
                                    }

                                    System.out.println("\n¿Qué desea actualizar ?");
                                    System.out.println("1. Actualizar email");
                                    System.out.println("2. Actualizar teléfono");
                                    System.out.println(opc3TextoEspecial);
                                    System.out.println("4. Volver");
                                    
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            Usuario.actualizarUsuario(read, gestorUsuario, "email");
                                            break;

                                        case 2:
                                            Usuario.actualizarUsuario(read, gestorUsuario, "telefono");
                                            break;

                                        case 3:
                                            if ("ascender".equals(accionEspecial)) {
                                                gestorUsuario.actualizarTipoUsuario(id, "InquilinoPremium");
                                                tipoUsuario = "InquilinoPremium";
                                                
                                            } else if ("descender".equals(accionEspecial)) {
                                                gestorUsuario.actualizarTipoUsuario(id, "Inquilino");
                                                tipoUsuario = "Inquilino";
                                                
                                            } else {
                                                System.out.println("\nOpción no válida para este tipo de usuario.");
                                            }
                                            break;

                                        case 4:
                                            System.out.println("\nVolviendo a gestionar usuarios...");
                                            break;

                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                    }

                                } while (opc3 != 4);
                                break;

                            case 3:
                                Usuario.eliminarUsuario(read, gestorUsuario);
                                break;

                            case 4:
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar Usuarios");
                                    System.out.println("2. Mostrar Inquilinos");
                                    System.out.println("3. Mostrar Anfitriones");
                                    System.out.println("4. Mostrar por ID");
                                    System.out.println("5. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorUsuario.mostrarUsuarios();
                                            break;

                                        case 2:
                                            gestorUsuario.mostrarUsuariosPorTipo("Inquilino");
                                            break;

                                        case 3:
                                            gestorUsuario.mostrarUsuariosPorTipo("Anfitrion");
                                            break;

                                        case 4:
                                            int idMostrar = Leer.leerInt(read, "\nIngrese el ID del usuario que desea mostrar: ");
                                            gestorUsuario.mostrarUsuarioPorId(idMostrar);
                                            break;

                                        case 5:
                                            System.out.println("\nVolviendo a gestionar usuarios...");
                                            break;

                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 5);
                                break;

                            case 5:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }
                    } while (opc2 != 5);

                    break;
                case 2:
                    do {
                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Agregar Propiedad");
                        System.out.println("2. Actualizar Propiedad");
                        System.out.println("3. Eliminar Propiedad");
                        System.out.println("4. Mostrar Propiedad");
                        System.out.println("5. Volver");
                        opc2 = Leer.leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1:
                                do {
                                    System.out.println("\n¿Qué tipo de propiedad desea agregar?");
                                    System.out.println("1. Casa");
                                    System.out.println("2. Departamento");
                                    System.out.println("3. Hotel");
                                    System.out.println("4. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            Propiedad.agregarPropiedad(read, gestorPropiedad, gestorUsuario, "Casa");
                                            break;
                                        case 2: 
                                            Propiedad.agregarPropiedad(read, gestorPropiedad, gestorUsuario, "Departamento");
                                            break;
                                        case 3:
                                            Propiedad.agregarPropiedad(read, gestorPropiedad, gestorUsuario, "Hotel");
                                            break;
                                        case 4:
                                            System.out.println("\nVolviendo a gestionar propiedades...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 4);
                                break;

                            case 2:
                                
                                int id = Leer.leerInt(read,"\nIngrese el ID de la propiedad que desea modificar: ");

                                if (!gestorPropiedad.existePropiedad(id)) {
                                    System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de usuarios.");
                                    break;
                                }

                                System.out.println("\nDatos actuales del usuario:");
                                gestorPropiedad.mostrarPropiedadPorId(id);
                                do {
                                    System.out.println("\n¿Qué desea actualizar?");
                                    System.out.println("1. Precio por noche");
                                    System.out.println("2. Parking");
                                    System.out.println("3. PetFriendly");

                                    if (gestorPropiedad.esTipoPropiedad(id, "Casa")) {
                                        System.out.println("4. Parrilla");
                                        System.out.println("5. Patio");
                                    } else if (gestorPropiedad.esTipoPropiedad(id, "Departamento")) {
                                        System.out.println("4. Balcón");
                                        System.out.println("5. Zona Común");
                                    } else if (gestorPropiedad.esTipoPropiedad(id, "Hotel")) {
                                        System.out.println("4. Check-In/Check-Out");
                                        System.out.println("5. Estrellas");
                                    }
                                    System.out.println("6. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "double", "precio");
                                            break;
                                        case 2:
                                            Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "boolean", "parking");
                                            break;
                                        case 3:
                                            Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "boolean", "petFriendly");
                                            break;
                                        case 4:
                                            if (gestorPropiedad.esTipoPropiedad(id, "Casa")) {
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "boolean", "parrilla");
                                            } else if (gestorPropiedad.esTipoPropiedad(id, "Departamento")) {
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "boolean", "balcon");
                                            } else if (gestorPropiedad.esTipoPropiedad(id, "Hotel")) {
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "String", "checkIn");
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "String", "checkOut");
                                            }
                                            break;
                                        case 5:
                                            if (gestorPropiedad.esTipoPropiedad(id, "Casa")) {
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "boolean", "patio");
                                            } else if (gestorPropiedad.esTipoPropiedad(id, "Departamento")) {
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "boolean", "zonaComun");
                                            } else if (gestorPropiedad.esTipoPropiedad(id, "Hotel")) {
                                                Propiedad.actualizarPropiedad(read, gestorPropiedad, id, "int", "estrellas");
                                            }
                                            break;
                                        case 6:
                                            System.out.println("\nVolviendo a gestionar propiedades...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 6);
                                break;

                            case 3:
                                Propiedad.eliminarPropiedad(read, gestorPropiedad);
                                break;

                            case 4:
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar Todas");
                                    System.out.println("2. Por anfitrión");
                                    System.out.println("3. Por tipo (Casa/Departamento/Hotel)");
                                    System.out.println("4. Por ID");
                                    System.out.println("5. Por ubicación");
                                    System.out.println("6. Por precio máximo");
                                    System.out.println("7. Por capacidad mínima");
                                    System.out.println("8. Por un adicional");
                                    System.out.println("9. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorPropiedad.mostrarPropiedades();
                                            break;
                                        case 2:
                                            int anfitrionId = Leer.leerInt(read, "Ingrese anfitrión ID: ");
                                            if (!gestorUsuario.existeUsuario(anfitrionId)) {
                                                System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de reservas.");
                                                break;
                                            }
                                            if (!gestorUsuario.esTipoUsuario(anfitrionId, "Anfitrion")) {
                                                System.out.println("El ID del Usuario no es anfitrión");
                                                break;
                                            }
                                            gestorPropiedad.mostrarPropiedadesPorAnfitrion(anfitrionId);
                                            break;
                                        case 3:
                                            System.out.print("¿Qué tipo desea mostrar?");
                                            System.out.println("1. Casa");
                                            System.out.println("2. Departamento");
                                            System.out.println("3. Hotel");
                                            System.out.println("4. Volver");
                                            opc4 = Leer.leerInt(read, "Seleccione una opción: ");
                                            String tipo = null;

                                            switch (opc4) {
                                                case 1:
                                                    tipo = "Casa";
                                                    break;
                                                case 2:
                                                    tipo = "Departamento";
                                                    break;
                                                case 3:
                                                    tipo = "Hotel";
                                                    break;
                                                case 4:
                                                    System.out.println("\nVolviendo a mostrar propiedades...");
                                                    break;
                                                default:
                                                    System.out.println("Opción no válida");
                                                    break;
                                            }
                                            if (tipo != null)
                                                gestorPropiedad.mostrarPropiedadesPorTipo(tipo);
                                            break;
                                        case 4:
                                            int idMostrar = Leer.leerInt(read, "Ingrese ID de la propiedad: ");
                                            gestorPropiedad.mostrarPropiedadPorId(idMostrar);
                                            break;
                                        case 5:
                                            System.out.print("Ingrese ubicación (texto o parte): ");
                                            String ubic = read.nextLine();
                                            gestorPropiedad.mostrarPropiedadesPorUbicacion(ubic);
                                            break;
                                        case 6:
                                            double precioMax = Leer.leerDouble(read, "Ingrese precio máximo: ");
                                            gestorPropiedad.mostrarPropiedadesPorPrecioMaximo(precioMax);
                                            break;
                                        case 7:
                                            int capacidad = Leer.leerInt(read, "Ingrese capacidad mínima (personas): ");
                                            gestorPropiedad.mostrarPropiedadesPorCapacidad(capacidad);
                                            break;
                                        case 8:
                                            System.out.println("Seleccione el adicional por el que desea filtrar:");
                                            System.out.println("1. Parking");
                                            System.out.println("2. PetFriendly");
                                            System.out.println("3. Parrilla");
                                            System.out.println("4. Patio");
                                            System.out.println("5. Balcón");
                                            System.out.println("6. Zona Común");
                                            System.out.println("7. Volver");

                                            opc4 = Leer.leerInt(read, "Seleccione una opción: ");
                                            String campo = null;

                                            switch (opc4) {
                                                case 1:
                                                    campo = "parking";
                                                    break;
                                                case 2:
                                                    campo = "petFriendly";
                                                    break;
                                                case 3:
                                                    campo = "parrilla";
                                                    break;
                                                case 4:
                                                    campo = "patio";
                                                    break;
                                                case 5:
                                                    campo = "balcon";
                                                    break;
                                                case 6:
                                                    campo = "zonaComun";
                                                    break;
                                                case 7:
                                                    System.out.println("\nVolviendo a mostrar propiedades...");
                                                    break;
                                                default:
                                                    System.out.println("Opción no válida. Intente de nuevo.");
                                                    break;
                                            }
                                            if (campo != null)
                                                gestorPropiedad.mostrarPropiedadesPorBoolean(campo);
                                            break;
                                        case 9:
                                            System.out.println("\nVolviendo a gestionar propiedades...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }

                                } while (opc3 != 9);
                                break;

                            case 5:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }

                    } while (opc2 != 5);
                    break;
                case 3:
                    do {
                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Agregar Reserva");
                        System.out.println("2. Actualizar Reserva");
                        System.out.println("3. Marcar Reserva como pagada");
                        System.out.println("4. Eliminar Reserva");
                        System.out.println("5. Mostrar Reservas");
                        System.out.println("6. Volver");
                        opc2 = Leer.leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1:
                                Reserva.crearReserva(read, gestorReserva, gestorPropiedad, gestorUsuario);
                                break;

                            case 2:
                                int id = Leer.leerInt(read, "\nIngrese el ID de la reserva que desea modificar: ");
                                if (!gestorReserva.existeReserva(id)) {
                                    System.out.println("No se encontró la reserva con el ID proporcionado. Volviendo al menú de reservas.");
                                    break;
                                }
                                System.out.println("Datos actuales:");
                                gestorReserva.mostrarReservasPorId(id);

                                do {
                                    System.out.println("\n¿Qué desea modificar?");
                                    System.out.println("1. Fechas (inicio y/o fin)");
                                    System.out.println("2. Cantidad de personas");
                                    System.out.println("3. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");
                                    
                                    int propiedadId = gestorReserva.obtenerPropiedadId(id);

                                    switch (opc3) {
                                        case 1:
                                            LocalDate nuevaFechaInicio = Leer.leerFecha(read, "Nueva fecha inicio (YYYY-MM-DD): ");
                                            LocalDate nuevaFechaFin = Reserva.esFechaValida(read, nuevaFechaInicio);

                                            double precioNoche = gestorPropiedad.obtenerPrecioNoche(propiedadId);
                                            if (precioNoche <= 0) {
                                                System.out.println("Error: No se pudo obtener el precio por noche o es inválido. No se actualizó la reserva.");
                                                break;
                                            }

                                            double precioFinal = Reserva.calcularPrecio(precioNoche, nuevaFechaInicio, nuevaFechaFin);

                                            gestorReserva.actualizarReserva(id, nuevaFechaInicio, nuevaFechaFin, precioFinal);
                                            break;

                                        case 2:
                                            int nuevaCant = Reserva.leerCantidadPersonasValida(read, gestorPropiedad, propiedadId);

                                            gestorReserva.actualizarReserva(id, nuevaCant);
                                            break;
                                        case 3:
                                            System.out.println("Volviendo...");
                                            break;

                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }

                                } while (opc3 != 3);
                                break;

                            case 3:
                                int idPagar = Leer.leerInt(read, "\nIngrese el ID de la reserva a marcar como pagada: ");
                                
                                if(!gestorReserva.existeReserva(idPagar)){
                                    System.out.println("Error: No se encontró ninguna reserva con el ID " + idPagar);
                                    break;
                                }

                                Reserva reservaAPagar = gestorReserva.buscarReserva(idPagar);

                                if (reservaAPagar.isPagado()) {
                                    System.out.println("Esta reserva ya se encuentra pagada.");
                                    break;
                                }

                                double monto = reservaAPagar.getPrecioFinal();
                                System.out.println("El monto total a pagar es: $" + String.format("%.2f", monto));
                                
                                System.out.println("Seleccione el método de pago:");
                                System.out.println("1. Efectivo");
                                System.out.println("2. Tarjeta");
                                System.out.println("3. Transferencia");
                                System.out.println("4. Cancelar");
                                opc3 = Leer.leerInt(read, "Opción: ");

                                Pago metodoDePago = null;

                                switch (opc3) {
                                    case 1:
                                        metodoDePago = new PagoEfectivo();
                                        break;
                                    case 2:
                                        metodoDePago = new PagoTarjeta();
                                        break;
                                    case 3:
                                        metodoDePago = new PagoTransferencia();
                                        break;
                                    case 4:
                                        System.out.println("Pago cancelado.");
                                        break;
                                    default:
                                        System.out.println("Opción de pago no válida.");
                                        break;
                                }

                                if (metodoDePago != null) {
                                    metodoDePago.procesarPago(monto, read); 
                                    
                                    gestorReserva.marcarReservaComoPagada(idPagar);
                                    System.out.println("Reserva ID " + idPagar + " marcada como pagada.");
                                }
                                break;

                            case 4:
                                Reserva.eliminarReserva(read, gestorReserva);
                                break;

                            case 5:
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar todas");
                                    System.out.println("2. Mostrar por ID");
                                    System.out.println("3. Mostrar por Inquilino");
                                    System.out.println("4. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorReserva.mostrarReservas();
                                            break;
                                        case 2: {
                                            int idMostrar = Leer.leerInt(read, "Ingrese ID de la reserva: ");
                                            if (!gestorPropiedad.existePropiedad(idMostrar)) {
                                                System.out.println("No se encontró la reserva con el ID proporcionado. Volviendo...");
                                                break;
                                            }
                                            gestorReserva.mostrarReservasPorId(idMostrar);
                                            break;
                                        }
                                        case 3: {
                                            int inqId = Leer.leerInt(read, "Ingrese ID del inquilino: ");
                                            if (!gestorUsuario.existeUsuario(inqId)) {
                                                System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de reservas.");
                                                break;
                                            }
                                            if (gestorUsuario.esTipoUsuario(inqId, "Anfitrion")) {
                                                System.out.println("El ID del Usuario no es inquilino");
                                                break;
                                            }
                                            gestorReserva.mostrarReservasPorInquilino(inqId);
                                            break;
                                        }
                                        case 4:
                                            System.out.println("\nVolviendo al menú de Reservas...");
                                            break;
                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                            break;
                                    }
                                } while (opc3 != 4);
                                break;

                            case 6:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }
                    } while (opc2 != 6);
                    break;
                case 4:
                    do {
                        System.out.println("\n¿Qué desea hacer con Reseñas?");
                        System.out.println("1. Agregar Reseña");
                        System.out.println("2. Actualizar Reseña");
                        System.out.println("3. Eliminar Reseña");
                        System.out.println("4. Mostrar Reseñas");
                        System.out.println("5. Volver");
                        opc2 = Leer.leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1: {
                                System.out.println("\n== Agregar Reseña ==");
                                int propiedadId = Leer.leerInt(read, "Propiedad ID: ");
                                if (!gestorPropiedad.existePropiedad(propiedadId)) {
                                    System.out.println(
                                            "No se encontró la propiedad con el ID proporcionado. Volviendo al menú de reseñas.");
                                    break;
                                }

                                int inquilinoId = Leer.leerInt(read, "Inquilino ID: ");
                                if (!gestorUsuario.existeUsuario(inquilinoId)) {
                                    System.out.println(
                                            "No se encontró el usuario con el ID proporcionado. Volviendo al menú de reseñas.");
                                    break;
                                }
                                if (gestorUsuario.esTipoUsuario(inquilinoId, "Anfitrion")) {
                                    System.out.println("El id del Usuario no es inquilino");
                                    break;
                                }

                                System.out.print("Comentario: ");
                                String comentario = read.nextLine();
                                int puntaje = Leer.leerInt(read, "Puntaje (enteros): ");

                                Resenia resenia = new Resenia(propiedadId, inquilinoId, comentario, puntaje);
                                gestorResenia.agregarResenia(resenia);
                                break;
                            }

                            case 2: {
                                int id = Leer.leerInt(read, "\nIngrese el ID de la reseña que desea modificar: ");
                                if (!gestorResenia.existeResenia(id)) {
                                    System.out.println(
                                            "No se encontró la reseña con el ID proporcionado. Volviendo al menú de reseñas.");
                                    break;
                                }

                                System.out.println("Ingrese los nuevos datos:");
                                System.out.print("Nuevo comentario: ");
                                String comentarioNuevo = read.nextLine();
                                int puntajeNuevo = Leer.leerInt(read, "Nuevo puntaje (enteros): ");

                                Resenia actualizada = new Resenia(0, 0, comentarioNuevo, puntajeNuevo);
                                gestorResenia.actualizarResenia(actualizada, id);
                                break;
                            }

                            case 3: {
                                int idEliminar = Leer.leerInt(read, "\nIngrese el ID de la reseña que desea eliminar: ");
                                gestorResenia.eliminarResenia(idEliminar);
                                break;
                            }

                            case 4: {
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar todas");
                                    System.out.println("2. Mostrar por Propiedad");
                                    System.out.println("3. Mostrar por Inquilino");
                                    System.out.println("4. Volver");
                                    opc3 = Leer.leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorResenia.mostrarResenias();
                                            break;
                                        case 2: {
                                            int propiedadId = Leer.leerInt(read, "Ingrese ID de la propiedad: ");
                                            if (gestorPropiedad.existePropiedad(propiedadId)) {
                                                gestorResenia.mostrarReseniasPorPropiedad(propiedadId);
                                            } else {
                                                System.out
                                                        .println("No se encontró la propiedad con el ID proporcionado");
                                            }
                                            break;
                                        }
                                        case 3: {
                                            int inquilinoId = Leer.leerInt(read, "Ingrese ID del inquilino: ");
                                            gestorResenia.mostrarReseniasPorInquilino(inquilinoId);
                                            if (gestorUsuario.existeUsuario(inquilinoId)) {
                                                gestorResenia.mostrarReseniasPorInquilino(inquilinoId);
                                            } else {
                                                System.out.println("No se encontró el usuario con el ID proporcionado");
                                            }
                                            break;
                                        }
                                        case 4:
                                            System.out.println("Volviendo al menú de Reseñas...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 4);
                                break;
                            }

                            case 5:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }
                    } while (opc2 != 5);
                    break;
                case 5:
                    System.out.println("\nSaliendo del programa...");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente de nuevo.");
            }
        } while (opc1 != 5);
    }
}