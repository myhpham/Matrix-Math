//--------------------------------------------------------
// Name: My Pham
// CruzID: myhpham
// Assignment: PA 3
//
// CS101 Spring 2019
//
// Implementation for Sparse/main class for
// Programming Assignment 3.
//--------------------------------------------------------

import java.io.*;
import java.util.Scanner;

class Sparse{
    public static void main(String[] args) throws IOException{
        Scanner in = null;
        PrintWriter out = null;
        String line = null;
        String[] token = null;
        int size = 0;
        int aSize = 0;
        int bSize = 0;

        int i = 0;
        int n = 0;
        int lineNumber = 0;
        int row = 0;
        int col = 0;
        double data = 0;

        /*
        if(args.length != 2){
            System.err.println("Usage: FileIO infile outfile");
            System.exit(1);
        }

        in = new Scanner(new File(args[0]));
        out = new PrintWriter(new FileWriter(args[1]));
        */

        File file = new File("/Users/mypham/Desktop/Java/PA3/src/in3.txt");
        in = new Scanner(file);
        out = new PrintWriter("out3.txt");

        //gets sizes of matrices
        line = in.nextLine();               // add extra space so split works right
        token = line.split("\\s+");  // split line around white space
        size = Integer.parseInt(token[0]);
        aSize = Integer.parseInt(token[1]);     //num of entries for matrix A
        bSize = Integer.parseInt(token[2]);     //num of entries for matrix B
        line = in.nextLine();

        Matrix A = new Matrix(size);
        Matrix B = new Matrix(size);
        Matrix C = new Matrix(size);

        while( in.hasNextLine() ){
            for(i = 0; i < aSize; i++){
                line = in.nextLine();               // add extra space so split works right
                token = line.split("\\s+");  // split line around white space

                row = Integer.parseInt(token[0]);
                col = Integer.parseInt(token[1]);
                data = Double.parseDouble(token[2]);

                A.changeEntry(row, col, data);
            }
            line = in.nextLine();
            token = line.split("\\s+");

            for(i = 0; i < bSize; i++){
                line = in.nextLine();               // add extra space so split works right
                token = line.split("\\s+");  // split line around white space

                row = Integer.parseInt(token[0]);
                col = Integer.parseInt(token[1]);
                data = Double.parseDouble(token[2]);

                B.changeEntry(row, col, data);
            }
        }

        //print matrix a
        out.println("A has " + A.getNNZ() + " non-zero entries:");
        out.println(A.toString());

        //print matrix b
        out.println("B has " + B.getNNZ() + " non-zero entries:");
        out.println(B.toString());

        //print (1.5)*A
        out.println("(1.5)*A =");
        C = A.scalarMult(1.5);
        out.println(C.toString());

        //print A+B
        out.println("A+B =");
        C = A.add(B);
        out.println(C.toString());

        //print A+A
        out.println("A+A =");
        C = A.add(A);
        out.println(C.toString());

        //print B-A
        out.println("B-A =");
        C = B.sub(A);
        out.println(C.toString());

        //print A-A
        out.println("A-A =");
        C = A.sub(A);
        out.println(C.toString());

        //print Transpose A
        out.println("Transpose(A) =");
        C = A.transpose();
        out.println(C.toString());

        //print A*B
        out.println("A*B =");
        C = A.mult(B);
        out.println(C.toString());

        //print B*B
        out.println("B*B =");
        C = B.mult(B);
        out.println(C.toString());

        //close files
        in.close();
        out.close();
    }
}