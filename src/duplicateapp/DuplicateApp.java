/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicateapp;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.*;


/**
 *
 * @author Andrei
 */
public class DuplicateApp {

    static Connection con = null;
    String str;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //SearchJFrame frame = new SearchJFrame();
        //frame.setVisible(true);
//        DuplicateJFrame frame = new DuplicateJFrame();
//        frame.setVisible(true);
        SearchJFrame frame = new SearchJFrame();
        frame.setVisible(true);
        
        DuplicateApp bd = new DuplicateApp();
        bd.Connect1();
    }
    
    public static String select() throws SQLException {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select logname FROM reguser;");
            String str = "Hello World";
            rs.close();
            stmt.close();
            return str;
        }
        
         
    public HashMap getFullName() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select fname, iname FROM reguser;");
        HashMap<String, String> fhash = new HashMap<>();
        
        while(rs.next()){
                fhash.put(rs.getString(1), rs.getString(2));
        }
        
        rs.close();
        stmt.close();
            
        return fhash;
    }
    
    public HashMap getEmail() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select email, fname FROM reguser;");
        HashMap<String, String> fhash = new HashMap<>();
        
        while(rs.next()){
                fhash.put(rs.getString(1), rs.getString(2));
        }
        
        rs.close();
        stmt.close();
            
        return fhash;
    }
    
    public HashMap getLogin() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select logname, email FROM reguser;");
        HashMap<String, String> fhash = new HashMap<>();
        
        while(rs.next()){
                fhash.put(rs.getString(1), rs.getString(2));
        }
        
        rs.close();
        stmt.close();
            
        return fhash;
    }
    
    public HashMap getOrganizated() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select org, fname FROM reguser;");
        HashMap<String, String> fhash = new HashMap<>();
        
        while(rs.next()){
                fhash.put(rs.getString(1), rs.getString(2));
        }
        
        rs.close();
        stmt.close();
            
        return fhash;
    }
    
    public HashMap getCombinedSearch(String fnameStr, String emailStr, String orgStr) throws SQLException {
        //Statement stmt = con.createStatement();
        //ResultSet rs = stmt.executeQuery("select logname FROM reguser WHERE fname like ? and org like ? and email like ?;");
        String str = "select fname, logname FROM reguser WHERE fname LIKE ? and email LIKE ? and org LIKE ?;";
        PreparedStatement ps = con.prepareStatement(str);
        ps.setString(1,"%" + fnameStr + "%");
        ps.setString(2,"%" + emailStr + "%");
        ps.setString(3,"%" + orgStr + "%");
        ResultSet rs = ps.executeQuery();
        HashMap<String, String> fhash = new HashMap<>();
        
        while(rs.next()){
                fhash.put(rs.getString(1), rs.getString(2));
        }
        
        rs.close();
            
        return fhash;
    }
        
        public  HashMap getDuplicateToFName() throws SQLException, IOException{
             
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select uid, fname FROM reguser;");
            
            HashMap<Integer, String> fhash = new HashMap<>();
            HashMap<String, ArrayList<Integer>> duplicateHash  = new HashMap<>();
            HashMap<String, ArrayList<Integer>> result  = new HashMap<>();
 
            while(rs.next()){
                fhash.put(rs.getInt(1), rs.getString(2));
            }

            arrDuplicates(fhash, duplicateHash);
            HashMapStepByStepPassage(duplicateHash, result);
   
            rs.close();
            stmt.close();
            
            return result;
        }
        
        public  HashMap getDuplicateToEmail() throws SQLException, IOException{
             
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select uid, email FROM reguser;");
            
            HashMap<Integer, String> fhash = new HashMap<>();
            HashMap<String, ArrayList<Integer>> duplicateHash  = new HashMap<>();
            HashMap<String, ArrayList<Integer>> result  = new HashMap<>();
 
            while(rs.next()){
                fhash.put(rs.getInt(1), rs.getString(2));
            }

            arrDuplicates(fhash, duplicateHash);
            HashMapStepByStepPassage(duplicateHash, result);
   
            rs.close();
            stmt.close();
            
            return result;
        }
        
        public void HashMapStepByStepPassage(HashMap<String,ArrayList<Integer>> duplicateHash,
                                            HashMap<String, ArrayList<Integer>> result){
            for(Map.Entry<String, ArrayList<Integer>> item : duplicateHash.entrySet()){
                if(item.getValue().size() > 1){
                    result.put(item.getKey(), item.getValue());
                }
            }    
        }
         
        public void arrDuplicates(HashMap<Integer, String> fhash,
                                    HashMap<String,ArrayList<Integer>> duplicateHash){
            Set<Integer> keys = fhash.keySet();
            for(Integer k: keys){
                String fname = fhash.get(k);
                ArrayList<Integer> ai = duplicateHash.get(fname);
                if(ai == null){
                    ai = new ArrayList<>();
                } 
                ai.add(k);
                duplicateHash.put(fname,ai);
            }
        }
        
         public void Connect1(){
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/nir";
                String login = "andrey";
                String password = "a091098b";
                con = DriverManager.getConnection(url, login, password);
                //con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
