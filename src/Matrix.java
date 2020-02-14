//--------------------------------------------------------
// Name: My Pham
// CruzID: myhpham
// Assignment: PA 3
//
// CS101 Spring 2019
//
// Implementation for a Matrix ADT for
// Programming Assignment 3.
//--------------------------------------------------------

import java.nio.file.NotLinkException;

class Matrix{
    private class Entry{
        //Entry fields
        double data;
        int col;

        //Entry constructor
        Entry(int col, double data){
            this.data = data;
            this.col = col;
        }

        //edit this!!!
        public String toString() {
            String s = "";
            String d = String.format("%.01f", this.data);
            s += "(" + col + ", " + this.data + ")";
            return s;
        }

        public boolean equals(Entry x) {
            boolean eq = false;
            if (this.col == x.col && this.data == x.data) {
                eq = true;
            }
            return eq;
        }
    }

    //Matrix Fields
    private List[] row;
    private int size;
    private int nnz;

    //Matrix constructor
    Matrix(int n){
        size = n;
        nnz = 0;

        if(n < 1)
            throw new RuntimeException("Error: Entry cannot be less than 1");
        row = new List[n+1];
        for(int i = 1; i<n+1; i++){
            List L = new List();
            row[i] = L;
        }
    }

    // Access Functions --------------------------------------------------------

    // Returns n, the number of rows and columns of this Matrix
    int getSize(){
        return size;
    }

    // Returns the number of non-zero entries in this Matrix
    int getNNZ(){
        return nnz;
    }

    public boolean equals(Matrix x){
        boolean eq = false;
        //boolean temp = false;
        Entry E, F;
        int i;

        if(this.getSize() != x.getSize()) {
            eq = false;
        }
        else{
            for(i = 1; i < this.getSize(); i++){
                eq = (this.row[i].length() == x.row[i].length());
                if (eq) {
                    if (this.row[i].length() != 0) {
                        //eq = (this.row[i].equals(x.row[i]));
                        this.row[i].moveFront();
                        x.row[i].moveFront();
                        while (eq && (this.row[i].index() != -1 || x.row[i].index() != -1)) {
                            E = (Entry) this.row[i].get();
                            F = (Entry) x.row[i].get();
                            eq = (E.equals(F));
                            if (this.row[i].index() != -1)
                                this.row[i].moveNext();
                            if (x.row[i].index() != -1)
                                x.row[i].moveNext();
                        }
                        if(!eq)
                            break;
                    }
                }
                else
                    break;
            }
        }

        return eq;
    }

    // Manipulation Procedures --------------------------------------------------------

    //sets this matrix to the 0 state
    void makeZero(){
        for(int i = 1; i <= getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                row[i].clear();
            }
        }
        nnz = 0;
    }

    Matrix copy(){
        Matrix M = new Matrix(getSize());
        int nonZero = this.nnz;

        for(int i = 1; i < getSize(); i++){
            M.row[i] = this.row[i];
        }

        M.nnz = nonZero;

        return M;
    }

    // changes ith row, jth column of this Matrix to x
    // use to fill matrix
    // pre: 1<=i<=getSize(), 1<=j<=getSize()
    void changeEntry(int i, int j, double x) {
        Entry E = new Entry(j, x);
        Entry F;
        //List L = row[i];
        //int index;

        //pre-conditions
        if (i < 1 || i > getSize())
            throw new RuntimeException("Error: wrong row entry");
        if (j < 1 || j > getSize())
            throw new RuntimeException("Error: wrong column entry");
        //pre-conditions

        //fills row with zeroes if length is 0
        if (row[i].length() != 0) {
            if (x != 0) {
                //row[i].moveFront();
                row[i].moveBack();
                F = (Entry) row[i].get();
                if (F.col < j) {
                    row[i].append(E);
                    nnz++;
                } else if (F.col > j) {
                    row[i].moveFront();
                    F = (Entry) row[i].get();
                    if (F.col > j) {
                        row[i].prepend(E);
                        nnz++;
                    } else if (F.col < j) {
                        while (F.col < j) {
                            row[i].moveNext();
                            F = (Entry) row[i].get();
                        }
                        if (F.col == j) {
                            row[i].insertBefore(E);
                            nnz++;
                            row[i].delete();
                            nnz--;
                        } else {
                            row[i].insertBefore(E);
                            nnz++;
                        }
                    }
                    else if (F.col == j) {
                        row[i].insertBefore(E);
                        nnz++;
                        row[i].delete();
                        nnz--;
                    }
                }
            }
            //supposed to replace current value with 0
            //decrement nnz (only if there is an element
            //given column, else, do nothing

            else if (x == 0) {
                row[i].moveFront();
                F = (Entry) row[i].get();
                for(int t = 1; t < row[i].length(); t++){
                    if(F.col == j)
                        break;
                    row[i].moveNext();
                    F = (Entry) row[i].get();
                }
                if (F.col == j) {
                    if (F.data != 0) {
                        //row[i].insertBefore(E);
                        row[i].delete();
                        nnz--;
                    }
                }
            }
        } else {
            if (x != 0) {
                row[i].append(E);
                nnz++;
            }
        }
    }

    //returns a new matrix that is the scalar product of this
    //Matrix with x
    Matrix scalarMult(double x){
        Matrix M = new Matrix(getSize());
        Matrix N = new Matrix(getSize());
        Entry E;
        double d;
        int i, j;
        //int rowx = 1;

        for(i = 1; i < getSize()+1; i++) {
            j = 1;
            if(this.row[i].length() > 0) {
                M.row[i] = this.row[i];
                M.row[i].moveFront();
                while(j <= M.row[i].length()){
                    E = (Entry) M.row[i].get();
                    d = E.data * x;
                    N.changeEntry(i, E.col, d);
                    M.row[i].moveNext();
                    j++;
                }
                //rowx++;
            }
        }

        return N;
    }

    //returns a new matrix tht is the sum of this matrix with M
    //pre: getsize == m.getsize
    Matrix add(Matrix M){
        //Matrix N = this.copy();
        Matrix P = new Matrix(getSize());
        int i = 1;
        int j = 1;

        //precondition
        if(this.getSize() != M.getSize()){
            throw new RuntimeException("Error: matrices are different sizes");
        }
        if(this.equals(M)){
            P = M.scalarMult(2);
        }
        else {
            for (i = 1; i < getSize() + 1; i++) {
                if (M.row[i].length() != 0 || this.row[i].length() != 0) {
                    P.row[i] = listAdd(this.row[i], M.row[i]);
                }
                P.nnz = P.nnz + P.row[i].length();
            }
        }
        return P;
    }

    //helper function for add
    //returns list
    private List listAdd(List M, List N) {
        List S = new List();
        Entry E = new Entry(0, 0);
        Entry F = new Entry(0, 0);
        //Entry G = new Entry(0,0);
        int i = 1;
        int c, d;
        double sum;

        if (M.length() != 0) {
            M.moveFront();
            E = (Entry) M.get();
        }
        if(N.length() != 0){
            N.moveFront();
            F = (Entry) N.get();
        }
        for(i = 1; i < getSize()+1; i++) {
            if(M.index() == -1 && N.index() == -1)
                break;
            //test e col less than f col
            if(E.col < F.col){
                if(M.index() != -1) {
                    if(E.data != 0){
                        S.append(E);
                    }
                    M.moveNext();
                    if (M.index() != -1)
                        E = (Entry) M.get();
                    else if(M.index() == -1){
                        c = E.col+1;
                        E = new Entry(c, 0);
                    }
                    else if (M.length() == 2) {
                        M.moveBack();
                        E = (Entry) M.get();
                    }
                }
                else {
                    c = E.col + 1;
                    E = new Entry(c, 0);
                }
            }
            //else if columns are not equal
            if(E.col > F.col){
                if(N.index() != -1) {
                    if(F.data != 0){
                        S.append(F);
                    }
                    N.moveNext();
                    if (N.index() != -1)
                        F = (Entry) N.get();
                    else if(N.index() == -1){
                        d = F.col+1;
                        F = new Entry(d, 0);
                    }
                    else if (N.length() == 2) {
                        N.moveBack();
                        F = (Entry) N.get();
                    }
                }
                else {
                    d = F.col + 1;
                    F = new Entry(d, 0);
                }
            }
            //test equal columns
            if (E.col == F.col) {
                sum = (E.data + F.data);
                if (sum != 0) {
                    Entry G = new Entry(E.col, sum);
                    S.append(G);
                }
                if (M.index() != -1) {
                    M.moveNext();
                    if (M.index() != -1)
                        E = (Entry) M.get();
                    else if(M.index() == -1){
                        c = E.col+1;
                        E = new Entry(c, 0);
                    }
                    else if (M.length() == 2) {
                        M.moveBack();
                        E = (Entry) M.get();
                    }
                }
                if (N.index() != -1) {
                    N.moveNext();
                    if (N.index() != -1)
                        F = (Entry) N.get();
                    else if(N.index() == -1){
                        d = F.col+1;
                        F = new Entry(d, 0);
                    }
                    else if (N.length() == 2) {
                        N.moveBack();
                        F = (Entry) N.get();
                    }
                }
            }
        }
        return S;
    }

    //returns a new matrix that is the difference of this matrix
    //with M
    //pre:getsize==m.getsize
    Matrix sub(Matrix M){
        //precondition
        if(this.getSize()!=M.getSize()){
            throw new RuntimeException("Error: Matrix sizes do not match");
        }
        Matrix P = new Matrix(getSize());
        int i;

        if(this.equals(M)){
            P = M.scalarMult(0);
        }
        else {
            for (i = 1; i < getSize() + 1; i++) {
                if (M.row[i].length() != 0 || this.row[i].length() != 0) {
                    P.row[i] = listSub(this.row[i], M.row[i]);
                }
                P.nnz = P.nnz + P.row[i].length();
            }
        }
        return P;
    }

    //helper function for sub function
    private List listSub(List M, List N){
        List S = new List();
        Entry E = new Entry(0, 0);
        Entry F = new Entry(0, 0);
        int i;
        int c, d;
        double diff;

        if (M.length() != 0) {
            M.moveFront();
            E = (Entry) M.get();
        }
        if(N.length() != 0){
            N.moveFront();
            F = (Entry) N.get();
        }
        for(i = 1; i < getSize()+1; i++) {
            if(M.index() == -1 && N.index() == -1)
                break;
            //test e col less than f col
            if(E.col < F.col){
                if(M.index() != -1) {
                    if(E.data != 0){
                        S.append(E);
                    }
                    M.moveNext();
                    if (M.index() != -1)
                        E = (Entry) M.get();
                    else if(M.index() == -1){
                        c = E.col+1;
                        E = new Entry(c, 0);
                    }
                    else if (M.length() == 2) {
                        M.moveBack();
                        E = (Entry) M.get();
                    }
                }
                else {
                    c = E.col + 1;
                    E = new Entry(c, 0);
                    //continue;
                }
            }
            //else if columns are not equal
            if(E.col > F.col){
                if(N.index() != -1) {
                    if(F.data != 0){
                        diff = (0.0-F.data);
                        Entry G = new Entry(F.col, diff);
                        S.append(G);
                    }
                    N.moveNext();
                    if (N.index() != -1)
                        F = (Entry) N.get();
                    else if(N.index() == -1){
                        d = F.col+1;
                        F = new Entry(d, 0);
                    }
                    /*
                    else if (N.length() == 2) {
                        N.moveBack();
                        F = (Entry) M.get();
                    }
                    */
                }
                else {
                    d = F.col + 1;
                    F = new Entry(d, 0);
                    //continue;
                }
            }
            //test equal columns
            if (E.col == F.col) {
                if(E.equals(F)){
                    diff = 0;
                }
                else
                    diff = (E.data - F.data);
                if (diff != 0) {
                    Entry G = new Entry(E.col, diff);
                    S.append(G);
                }
                if (M.index() != -1) {
                    M.moveNext();
                    if (M.index() != -1)
                        E = (Entry) M.get();
                    else if(M.index() == -1){
                        c = E.col+1;
                        E = new Entry(c, 0);
                    }
                    else if (M.length() == 2) {
                        M.moveBack();
                        E = (Entry) M.get();
                    }
                }
                if (N.index() != -1) {
                    N.moveNext();
                    if (N.index() != -1)
                        F = (Entry) N.get();
                    else if(N.index() == -1){
                        d = F.col+1;
                        F = new Entry(d, 0);
                    }
                    else if (N.length() == 2) {
                        N.moveBack();
                        F = (Entry) N.get();
                    }
                }
                //continue;
            }
        }
        return S;
    }

    //returns new matrix that is transpose of this Matrix
    //DOES NOT WORK WITH DIFF SIZE MATRIX
    Matrix transpose(){
        Matrix M = new Matrix(getSize());
        //Matrix N = new Matrix(getSize());
        Entry E;
        int i,j;

        //M = this.copy();
        //start at front of matrix
        for(i = 1; i < this.getSize()+1; i++) {
            if (this.row[i].length() != 0) {
                this.row[i].moveFront();
            }
            for (j = 1; j < this.row[i].length()+1; j++) {
                E = (Entry) this.row[i].get();
                M.changeEntry(E.col, i, E.data);
                this.row[i].moveNext();
            }
        }
        return M;
    }

    //returns a new matrix that is the product of this
    //matrix with M
    //pre: getsize()==M.getSize()
    Matrix mult(Matrix M){
        double d;
        int i;
        //precondition
        if(getSize() != M.getSize()){
            throw new RuntimeException("Error: Matrices are not same sizes");
        }

        M = M.transpose();
        Matrix P = new Matrix(getSize());
        for(i = 1; i < M.getSize()+1; i++){
            for(int j = 1; j < this.getSize()+1; j++) {
                d = dot(this.row[i], M.row[j]);
                P.changeEntry(i, j, d);
            }
        }
        return P;
    }

    // Other Methods --------------------------------------------------------

    public String toString() {
        //StringBuffer sb = new StringBuffer();
        String s = "";

        for(int i = 1; i < getSize()+1; i++){
            if(this.row[i].length()!=0){
                s += (i + ": " + this.row[i].toString() + "\n");
            }
            else
                continue;
        }

        return s;
    }

    private static double dot(List P, List Q){
        double d = 0;
        Entry E, F;
        int i = 1;
        int j = 1;

        if(P.length() != 0 && Q.length() != 0) {
            P.moveFront();
            Q.moveFront();
            E = (Entry) P.get();
            F = (Entry) Q.get();

            while(i < P.length()+1 && j < Q.length()+1){
                if(E.col == F.col){
                    d = d+(E.data*F.data);
                    P.moveNext();
                    Q.moveNext();
                    i++;
                    j++;
                }
                else if(E.col < F.col){
                    P.moveNext();
                    i++;
                }
                else if(E.col > F.col){
                    Q.moveNext();
                    j++;
                }
                if(i < P.length()+1)
                    E = (Entry) P.get();
                if(j < Q.length()+1)
                    F = (Entry) Q.get();
            }
        }
        return d;
    }
}