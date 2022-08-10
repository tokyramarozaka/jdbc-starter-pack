package menu;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Scanner;

public class MainMenu {
    MenuHandler handler = new MenuHandler();
    public MainMenu(){
        while(true){
            this.display();
            int choice = this.prompt();

            if(choice == 5){
                break;
            }else{
                handler.process(choice);
            };
        }
    }

    private int prompt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice : ");
        int answer = scanner.nextInt();

        return answer;
    }

    private void display(){
        System.out.println("""
                TO-DO LIST - Choose an action 
                
                1 : CREATE
                2 : READ 
                3 : UPDATE
                4 : DELETE
                5 : QUIT
                """);
    }

    public static void main(String[] args) {
        MainMenu app = new MainMenu();
    }
}
