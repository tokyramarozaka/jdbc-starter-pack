package menu;

import model.ToDo;
import repository.ToDoRepository;

import java.sql.SQLOutput;
import java.util.Scanner;

public class MenuHandler {
    private Scanner scanner = new Scanner(System.in);
    private static ToDoRepository repository = new ToDoRepository();

    public void process(int choice){
        switch (choice){
            case 1 -> createPrompt();
            case 2 -> select();
            case 3 -> update();
            case 4 -> delete();
            case 5 -> System.out.println("Quitting...");
        }
    }

    private void update() {
        System.out.print("Enter the todo id : ");
        int toDoId = Integer.valueOf(this.scanner.nextLine());

        System.out.print("Enter its new name (leave blank to not change it) :");
        String name = this.scanner.nextLine();

        System.out.print("Set it to complete ? (y/o) :");
        String completed = this.scanner.nextLine();

        repository.update(toDoId,new ToDo(toDoId,name,completed.equals("y") ? true : false));
    }

    private void delete() {
        System.out.print("Enter the id of the to do : ");
        int id = scanner.nextInt();

        repository.delete(id);

        System.out.println("To do has been deleted");
    }

    private void select() {
        System.out.println("-------------------------------------------");
        repository.findAll().forEach(System.out::println);
        System.out.println("-------------------------------------------");
    }

    private void createPrompt() {
        System.out.print("Enter the name of your todo : ");
        String name = this.scanner.nextLine();

        repository.add(new ToDo(0,name, false));

        System.out.println("To do has been added");
    }
}
