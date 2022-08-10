package repository;

import model.ToDo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ToDoRepository {
    private DataBaseConnection db = new DataBaseConnection();

    public void add(ToDo toDo) { // id, description, completed
        PreparedStatement pStm = null;
        try {
            pStm = db
                    .getConnection()
                    .prepareStatement("INSERT INTO todo (name, completed) VALUES (?,false)");
            pStm.setString(1, toDo.getName());
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } // Add finally to close the statements and prepared statements potentially the connection if you want to be optimal.
    }

    public void update(int toDoId, ToDo newToDo){
        try {
            PreparedStatement pStm = db
                    .getConnection()
                    .prepareStatement("UPDATE todo SET name=?,completed=? WHERE id=?");

            pStm.setString(1,newToDo.getName());
            pStm.setBoolean(2,newToDo.isCompleted());
            pStm.setInt(3,toDoId);

            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int toDoId){
        try {
            PreparedStatement pStm = db.getConnection()
                    .prepareStatement("DELETE FROM todo WHERE id=?");
            pStm.setInt(1,toDoId);

            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ToDo> findAll(){
        List<ToDo> results = new ArrayList<>();
        try {
            PreparedStatement pStm = db
                    .getConnection()
                    .prepareStatement("SELECT * FROM todo");

            ResultSet res = pStm.executeQuery();

            while(res.next()){
                results.add(new ToDo(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getBoolean("completed")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
