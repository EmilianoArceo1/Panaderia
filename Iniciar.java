import java.util.Scanner;

public class Iniciar {

    public static void main(String[] args) {
        System.out.println("Qué vista desea abrir?");
        System.out.println("1- Vista admin");
        System.out.println("2- Vista empleado");
        System.out.println("3- Vista login");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                new AdminFrame().setVisible(true);
                break;
            case 2:
                new EmpleadoFrame().setVisible(true);
                break;
            case 3:
                new LoginFrame().setVisible(true);
                break;
            default:
                System.out.println("Opción no válida");
        }
        scanner.close();
    }
}