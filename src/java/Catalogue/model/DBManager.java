package Catalogue.model;

import uts.isd.model.dao.*;
import Catalogue.model.Movie;
import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    
    private Statement st;

    public DBManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    public Movie findMovieByTitle (String title) throws SQLException {
        String state = "SELECT m.*, g.genretitle FROM MOVIE m JOIN MOVIE_GENRE mg "
                + "ON (m.MOVIEID = mg.MOVIEID) JOIN GENRE g ON "
                + "(mg.GENREID = g.GENREID) WHERE m.MOVIETITLE = '"+title+"'";
        try {
            ResultSet rs = st.executeQuery(state);
            while(rs.next()) {
                Movie movie = new Movie(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getString(5)
                
                );
            
                return movie; 
            }
        } catch (SQLException ex) {
        }

        
        return new Movie(0, "Movie not in our collection", 0.0, 0, null); 
    }
    
    public List<Movie> findMovieByGenre(String genre) throws SQLException {
                String state = "SELECT m.*, g.genre FROM MOVIE m JOIN MOVIE_GENRE MG"
                + "ON (m.MOVIEID = mg.MOVIEID) JOIN GENRE g ON"
                + "mg.GENREID = g.GENREID) WHERE g.GENRETITLE = '"+genre+"'";
        
        ResultSet rs = st.executeQuery(state);
        List<Movie> output = new ArrayList<Movie>();
        while(rs.next()) {
            Movie movie = new Movie(
                rs.getInt("MOVIEID"),
                rs.getString("MOVIETITLE"),
                rs.getDouble("PRICE"),
                rs.getInt("AMOUNT"),
                rs.getString("GENRE")
            );
            output.add(movie);
        }
        return output;
    }
    
    public void updateMovie(Movie changes) throws SQLException {
        String statement = "UPDATE MOVIE SET TITLE = '" + changes.getTitle() +
                "', price = " + changes.getPrice() + ", amount = " + changes.getAmount()
                + "WHERE movieID = " + changes.getID();
        st.executeUpdate(statement);
        
        statement = "UPDATE MOVIE_GENRE SET genreID = (SELECT genreID FROM GENRE"
                + "g WHERE g.genreTitle = '" + changes.getGenre() + "')"
                + "WHERE movieID = " + changes.getID(); 
        st.executeUpdate(statement);
    }

    public void deleteMovie(int movieID) throws SQLException {
        String statement = "DELETE FROM MOVIE WHERE movieID = " + movieID;
        
        st.executeUpdate(statement);
        
        statement = "DELETE FROM MOVIE_GENRE WHERE movieID = " + movieID;
        
        st.executeUpdate(statement);
    }
    
    public void addMovie(Movie movie) throws SQLException {
        
        String statement = "SELECT MAX(MovieID) FROM Movie";
        ResultSet rs = st.executeQuery(statement);
        rs.next();
        int movieID = rs.getInt("MOVIEID") + 1;
        
        statement = "INSERT INTO Movie VALUES (" + movieID + ", '" + movie.getTitle()
                + "' " + movie.getPrice() + ", " + movie.getAmount() +")";
        
        st.executeUpdate(statement);
        
        statement = "INSERT INTO MOVIE_GENRE VALUES (" + movieID +", "
                + "(SELECT GENRE_ID FROM GENRE g WHERE g.GENRE = '"
                + movie.getGenre() +"'))";
        st.executeUpdate(statement);
    }
}

/*
   public User findUser(String ID, String password) throws SQLException {
        //setup the select sql query string
        String searchQueryString = "select * from Users where ID='" + ID + "' AND password='" + password + "'";
        //execute this query using the statement field
       //add the results to a ResultSet
         ResultSet rs = st.executeQuery(searchQueryString);
        //search the ResultSet for a student using the parameters
         boolean hasUser = rs.next();
         User studentFromDB = null;
                 
         if(hasUser){
         
             String sID = rs.getString("ID");
             String sPassword = rs.getString("password"); 
             String sEmail = rs.getString("email");
             String sName = rs.getString("name");
             String sDOB = rs.getString("dob");
             String sFavcolor = rs.getString("favoritecolor");
             
             studentFromDB = new User (sID, sEmail, sName, sPassword, sDOB, sFavcolor);
         }
        
         rs.close();
        // st.close();
         
         return studentFromDB;
         
    }

    //Check if a student exist in the database
    public boolean checkUser(String ID, String password) throws SQLException {
       //setup the select sql query string
        //execute this query using the statement field
        //add the results to a ResultSet
        //search the ResultSet for a student using the parameters
        //verify if the student exists
        return false;
    }

    //Add a student-data into the database
    public void addUser(String ID, String email, String name, String password, String dob, String favcol) throws SQLException {        
        //code for add-operation
        
         String createQueryString = "insert into Users" + " values ('" + ID + "', '" + email + "', '" + name + "', '" + password + "', '" + dob + "', '" + favcol + "')";
         boolean recrodCreated = st.executeUpdate(createQueryString) > 0;
         
         if (recrodCreated){
         System.out.println("record created");
         }
         else {
         System.out.println("record not created");
         }
             
    }

    //update a student details in the database
    public void updateUser(String ID, String email, String name, String password, String dob, String favcol) throws SQLException {
        //code for update-operation
        
        String updateQueryString = "update Users set email = '" + email + "', name= '" + name + "', password = '"  + password + "', dob = '" + dob + "', favoritecolor = '" + favcol + "' where ID='" + ID + "'";
        boolean recrodUpdated = st.executeUpdate(updateQueryString) > 0;
         
         if (recrodUpdated){
         System.out.println("record updated");
         }
         else {
         System.out.println("record not updated");
         }
       
    }
    
    //delete a student from the database
    public void deleteUser(String ID) throws SQLException{
        //code for delete-operation
        
        String deleteQueryString = "delete from Users where ID= '" + ID + "' ";
        boolean recrodDeleted = st.executeUpdate(deleteQueryString) > 0;
         
         if (recrodDeleted){
         System.out.println("record deleted");
         }
         else {
         System.out.println("record not deleted");
         }
    }
} */
