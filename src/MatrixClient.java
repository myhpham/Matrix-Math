//-----------------------------------------------------------------------------
//  MatrixClient.java
//  A test client for the Matrix ADT
//-----------------------------------------------------------------------------

public class MatrixClient{
    public static void main(String[] args){
        int i, j, n=3;
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
        A.changeEntry(2, 1, 2);
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
        B = A.copy();
        if (B.getNNZ() != 1) System.out.println("failed copy test 2");

        /*
        System.out.println(A.getNNZ());
        System.out.println(A);

        System.out.println(B.getNNZ());
        System.out.println(B);

        Matrix C = A.scalarMult(1.5);
        System.out.println(C.getNNZ());
        System.out.println(C);

        Matrix D = A.add(A);
        System.out.println(D.getNNZ());
        System.out.println(D);

        Matrix E = A.sub(A);
        System.out.println(E.getNNZ());
        System.out.println(E);

        Matrix F = B.transpose();
        System.out.println(F.getNNZ());
        System.out.println(F);

        Matrix G = B.mult(B);
        System.out.println(G.getNNZ());
        System.out.println(G);
         */
    }
}