/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility;

/**
 *
 * @author Piero
 */
public class User {
    public String user_name;
    public String password;
    public String name;
    public String surname;
    public String phone;
    public String admin;
    
    public boolean isAdmin()
    {
        if (admin.equals("1")) {
            return true;
        } else {
            return false;
        }
    }
    
}
