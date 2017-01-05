/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.List;
import Shared.Model.*;
import java.util.ArrayList;
/**
 *
 * @author Rajtek
 */
public class ClientModel {
    private Player player;
    private List<Table> tablesList;
    private final List<ModelListener> listeners = new ArrayList<>();
    
   
    public List<Table> getTablesList() {
        return tablesList;
    }

    public void setTablesList(List<Table> tablesList) {
        this.tablesList = tablesList;
        propertyTablesListChanged();
    }

    public void setPlayer(Player player) {
        this.player = player;
        propertyPlayerChanged();
    }

    public Player getPlayer() {
        return player;
    }
    
    public void addListener(ModelListener toAdd){
       listeners.add(toAdd);
   }
   
   private void propertyPlayerChanged(){
       for (ModelListener s : listeners){
           s.propertyPlayerChanged();
       }
   }
   
   private void propertyTablesListChanged(){
       for (ModelListener s : listeners){
           s.propertyTablesListChanged();
       }
   }
    
}
