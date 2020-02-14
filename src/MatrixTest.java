//--------------------------------------------------------
// Name: My Pham
// CruzID: myhpham
// Assignment: PA 3
//
// CS101 Spring 2019
//
// Test program for Matrix ADT.
// Note: Mostly from MatrixClient.java
//--------------------------------------------------------

public class MatrixTest{
    public static void main(String[] args){
        //int i, j,
        int n=3;
        Matrix A = new Matrix(n);
        Matrix B = new Matrix(n);

        //A.makeZero();
        //B.makeZero();
        /*
        A.changeEntry(1,1,1); B.changeEntry(1,1,1);
        A.changeEntry(1,2,2); B.changeEntry(1,3,1);
        A.changeEntry(1,3,3); B.changeEntry(1,2,0);
        A.changeEntry(2,1,4); //B.changeEntry(2,1,0);
        A.changeEntry(2,2,5); //B.changeEntry(2,2,1);
        A.changeEntry(2,3,6); //B.changeEntry(2,3,0);
        A.changeEntry(3,1,7); B.changeEntry(3,1,1);
        A.changeEntry(3,2,8); B.changeEntry(3,2,1);
        A.changeEntry(3,3,9); B.changeEntry(3,3,1);
         */

        //test changeEntry_nnz
        A = new Matrix(10);
        A.changeEntry(2, 4, 2);
        A.changeEntry(3, 1, 5);
        A.changeEntry(1, 2, 2);
        A.changeEntry(1, 3, 5);
        A.changeEntry(1, 1, 4);
        A.changeEntry(2, 2, 2);
        A.changeEntry(2, 5, 0);
        A.changeEntry(2, 3, 0);
        A.changeEntry(3, 3, 5);
        if (A.getNNZ() != 7) System.out.println("failed changeEntry test 1");
        A.changeEntry(1, 3, 0);
        A.changeEntry(3, 1, 0);
        A.changeEntry(3, 3, 0);
        if (A.getNNZ() != 4) System.out.println("failed changeEntry test 2");
        A.changeEntry(7, 6, 42);
        A.changeEntry(10, 1, 24);
        if (A.getNNZ() != 6) System.out.println("failed changeEntry test 3");
        A.changeEntry(7, 6, 0);
        if (A.getNNZ() != 5) System.out.println("failed changeEntry test 4");
        A.makeZero();
        A.changeEntry(5, 5, 5);
        if (A.getNNZ() != 1) System.out.println("failed changeEntry test 5");

        A.makeZero();

        //test copy
        A = new Matrix(10);
        B = A.copy();
        if (B.getNNZ() != 0) System.out.println("failed copy test 1");
        A.changeEntry(1, 1, 1);
        A.changeEntry(1, 3, 5);
        A.changeEntry(1, 2, 4);
        A.changeEntry(2, 2, 2);
        A.changeEntry(2, 5, 0);
        B = A.copy();
        if (B.getNNZ() != 4) System.out.println("failed copy test 2");

        A = new Matrix(4);
        B = A.transpose();
        if (B.getNNZ() != 0) System.out.println("failed transpose test 1");
        A.changeEntry(1, 1, 4);
        A.changeEntry(1, 2, 2);
        A.changeEntry(1, 3, 6);
        A.changeEntry(2, 1, 2);
        A.changeEntry(3, 1, 0);
        A.changeEntry(2, 2, 2);
        A.changeEntry(3, 3, 0);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 3, 1);
        B = A.transpose();
        if (B.getNNZ() != A.getNNZ()) System.out.println("failed transpose test 2");

        A = new Matrix(10);
        A.changeEntry(1, 1, 4);
        A.changeEntry(1, 2, 2);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 1, 2);
        A.changeEntry(3, 1, 0);
        A.changeEntry(2, 2, 3);
        A.changeEntry(3, 3, 0);
        A.changeEntry(4, 1, 1);
        A.changeEntry(4, 2, 5);
        A.changeEntry(4, 3, 3);
        A.changeEntry(7, 1, 1);
        B = A.scalarMult(-20);
        if (B.getNNZ() != A.getNNZ()) System.out.println("failed scalar mult test 1");

        A = new Matrix(10);
        B = new Matrix(10);
        A.changeEntry(1, 1, 1);
        A.changeEntry(1, 2, 1);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 1, 1);
        A.changeEntry(2, 2, 2);
        A.changeEntry(3, 1, 1);
        A.changeEntry(3, 2, 3);
        A.changeEntry(3, 3, 1);
        Matrix C = A.mult(A);
        if (C.getNNZ() != 7) System.out.println("failed mult test 1");
        A.changeEntry(1, 1, 1);
        A.changeEntry(1, 2, 2);
        A.changeEntry(1, 3, 3);
        A.changeEntry(2, 1, 4);
        A.changeEntry(2, 2, 5);
        A.changeEntry(2, 3, 6);
        A.changeEntry(3, 1, 7);
        A.changeEntry(3, 2, 8);
        A.changeEntry(3, 3, 9);
        B.changeEntry(1, 1, 1);
        B.changeEntry(2, 2, 1);
        C = A.mult(B);
        if (C.getNNZ() != 6) System.out.println("failed mult test 2");

        //add test
        A = new Matrix(10);
        B = new Matrix(10);
        A.changeEntry(1, 1, 4);
        A.changeEntry(1, 2, 2);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 1, 2);
        A.changeEntry(3, 1, 0);
        A.changeEntry(2, 2, 2);
        A.changeEntry(3, 3, 0);
        C = A.add(A);
        if (C.getNNZ() != 4 || A.getNNZ() != 4) System.out.println("failed add test 1");
        C.makeZero();
        B.changeEntry(1, 1, -4);
        B.changeEntry(1, 2, 0);
        B.changeEntry(2, 1, 0);
        B.changeEntry(2, 2, -2);
        B.changeEntry(2, 4, 2);
        B.changeEntry(3, 1, 0);
        B.changeEntry(3, 2, 2);
        B.changeEntry(7, 8, 5);
        C = A.add(B);
        if (C.getNNZ() != 5) System.out.println("failed add test 2");

        //subtract test
        A = new Matrix(10);
        B = new Matrix(10);
        A.changeEntry(1, 1, -4);
        A.changeEntry(1, 2, -2);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 5, 4);
        A.changeEntry(2, 1, -2);
        A.changeEntry(3, 1, 2);
        A.changeEntry(2, 2, -2);
        A.changeEntry(3, 3, 0);
        C = A.sub(A);
        if (C.getNNZ() != 0 || A.getNNZ() != 6) System.out.println("failed sub test 1");
        B.changeEntry(1, 1, -4);
        B.changeEntry(1, 2, 0);
        B.changeEntry(2, 1, 0);
        B.changeEntry(2, 2, -2);
        B.changeEntry(2, 4, 2);
        B.changeEntry(3, 1, 2);
        B.changeEntry(3, 2, 2);
        B.changeEntry(7, 8, 5);
        C = A.sub(B);
        if (C.getNNZ() != 6) System.out.println("failed sub test 2");

        A = new Matrix(10);
        B = new Matrix(15);
        if (A.equals(B)) System.out.println("fail equals test 1");
        B = new Matrix(10);
        if (!A.equals(B)) System.out.println("fail equals test 2");
        A.changeEntry(5, 5, 5);
        B.changeEntry(5, 5, 5);
        A.makeZero();
        B.makeZero();
        if (!A.equals(B)) System.out.println("fail equals test 3");

        //non empty equals
        A = new Matrix(10);
        B = new Matrix(15);
        A.changeEntry(1, 1, 1);
        B.changeEntry(1, 1, 1);
        if (A.equals(B)) System.out.println("fail NE equals test 1");
        B = new Matrix(10);
        A.changeEntry(1, 1, 1);
        A.changeEntry(1, 3, 1);
        B.changeEntry(1, 1, 1);
        B.changeEntry(1, 3, 1);
        if (!A.equals(B)) System.out.println("fail NE equals test 2");
        A.changeEntry(1, 3, 0);
        if (A.equals(B)) System.out.println("fail NE equals test 3");
        A.changeEntry(1, 1, 0);
        B.makeZero();
        A.changeEntry(10, 10, 10);
        B.changeEntry(10, 10, 10);
        if (!A.equals(B)) System.out.println("fail NE equals test 4");
        A = new Matrix(5);
        B = new Matrix(5);
        int valcount = 1;
        for (int j = 1; j <= 5; j++) {
            for (int k = 1; k <= 5; k++) {
                // hint: this is 1-10000 left-to-right row-by-row
                A.changeEntry(j, k, valcount++);
            }
            B.changeEntry(j, j, 1); // hint: this is the identity matrix
        }
        C = A.scalarMult(2);
        Matrix D = A.add(A);
        if (!C.equals(D)) System.out.println("fail NE equals test 5");
        C = A.scalarMult(-2);
        D = A.sub(A).sub(A).sub(A);
        if (!C.equals(D)) System.out.println("fail NE equals test 6");
        C = A.mult(B);
        if (!C.equals(A)) System.out.println("fail NE equals test 7");
    }
}