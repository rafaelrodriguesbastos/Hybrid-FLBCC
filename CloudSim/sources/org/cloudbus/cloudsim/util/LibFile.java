/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author brunomoura
 */
public class LibFile {

    private BufferedWriter bw;
    private String x = "";
    private String pathFile;
    private String fileName;

    public LibFile() {

    }

    public LibFile(BufferedWriter bw, String pathFile, String fileName) {
        this.bw = bw;
        this.pathFile = pathFile;
        this.fileName = fileName;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void escreverTxt(String strEntrada, String pathFile, String fileName, String strFileHeader) {
        this.pathFile = pathFile;
        this.fileName = fileName;

        try {
            //nome e  extensão do arquivo q vc vai escrever  
            //bw = new BufferedWriter(new FileWriter("c:\arquivo.txt"));
            bw = new BufferedWriter(new FileWriter(pathFile + fileName));
            //x = "blábláblá";
            this.x = strFileHeader + "\n" + strEntrada + "\n";

            // coloca aki o nome do seu text area, aí jah era!!  
            bw.write(x);

            // não eskeça de fechar o arquivo   
            bw.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void adicionarLinhaTxt(String strEntrada, String pathFile, String fileName) {
        this.pathFile = pathFile;
        this.fileName = fileName;

        try {
            //nome e  extensão do arquivo q vc vai escrever  
            //bw = new BufferedWriter(new FileWriter("c:\arquivo.txt"));
            bw = new BufferedWriter(new FileWriter(pathFile + fileName,true));
            //x = "blábláblá";
            this.x = strEntrada + "\n";

            // coloca aki o nome do seu text area, aí jah era!!  
            bw.write(x);

            // não eskeça de fechar o arquivo   
            bw.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    public String lerTxt(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        String strSaida = "";
        while (true) {
            if (linha != null) {
                System.out.println(linha);
                strSaida = strSaida + linha;

            } else {
                break;
            }
            linha = buffRead.readLine();
        }
        buffRead.close();
        return strSaida;
    }

}
