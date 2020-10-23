/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.management;

import java.util.Scanner;

/**
 *
 * @author ashwe
 */

public class BookConsole {
     private BookManager dm;
    private Scanner sc;
    
    public BookConsole() {
        this.sc = new Scanner(System.in);
        this.dm = new BookManager();
        
    }
    
    private int menu(){
            System.out.println("---Delivery Menu---");
            System.out.println("1. Add Book");
            System.out.println("2.Show all Show");
            System.out.println("3. Search Book");
            System.out.println("0. Exit");
            int choice = readInt(0, 3);   
            return choice;
        }
    
    public void start() {
        while(true) {
        int choice = menu();
        switch (choice) {
            case 0: 
                System.exit(0);
                break;
            case 1: 
                addBook();
                break;
            case 2:
                showAll();
                break;
            case 3:
                searchBook();
                break;
            default:
                throw new AssertionError();
        }
                
    }
        
    }

    private int readInt(int min, int max) {
        int choice;
        while(true){
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= min && choice <= max) {
                    break;
                }
            } catch (NumberFormatException e) {
            }
        }
        return choice;
    }

    

    private float readFloat(int min, float max) {
        float price;
        while(true){
            try {
                price = Float.parseFloat(sc.nextLine());
                if(price >= min && price <= max) {
                break;
                
            }
            } catch (Exception e) {
                System.out.println("Invalid value. Try to enter a float value");
            }
        }
        return price;
    }
    private void addDelivery() {
      System.out.println("Enter delivery ID: ");
      int id = readInt(0, Integer.MAX_VALUE);
      System.out.println("Enter Delivery Name");
      String name = sc.nextLine();
      System.out.println("Enter delivery price");
      float price = readFloat(0, Float.MAX_VALUE);
      Book d = new Book(id, name, price);
      this.dm.addBook(d);
      
    }

    private void showAll() {
      System.out.println("--All delivery--");
      System.out.println("ID\tName\tPrice");
      for (int i = 0; i < this.dm.count(); i++){
          Book d = this.dm.getBook(i);
            System.out.println(d.getId()+"\t"+d.getName()+"\t"+d.getPrice());
      }
    }
    
    private void removeDelivery() {
        System.out.println("Enter ID of delivery");
        int id = readInt(0, Integer.MAX_VALUE);
        boolean result = this.dm.removeDelivery(id);
        if(result) {
            System.out.println("Delivery was removed");
        }else{
            System.out.println("Delivery not found");
        }
    }

    private void searchBook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addBook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

