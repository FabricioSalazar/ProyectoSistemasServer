/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*Que ondas putos!*/

package com.ucr.proyecto.main;

import com.ucr.proyecto.domain.Server;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Server(5700).start();

    }
}
