//--------------------------------------------------------
// Name: My Pham
// CruzID: myhpham
// Assignment: PA 3
//
// CS101 Spring 2019
//
// Implementation for a doubly linked list ADT for
// Programming Assignment 3.
//--------------------------------------------------------

class List{
    private class Node {
        //Node Fields
        Object data;
        Node next;
        Node prev;

        //Node Constructor
        Node(Object data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        public String toString() {
            return String.valueOf(data);
        }

        public boolean equals(Object x) {
            boolean eq = false;
            Node that;
            if (x instanceof Node) {
                that = (Node) x;
                eq = (this.data == that.data);
            }
            return eq;
        }
    }

    //List Fields
    private Node front;
    private Node back;
    private Node cursor;
    private int length;
    private int index;

    //List Constructor
    List() {
        front = back = null;
        cursor = null;
        length = 0;
        index = -1;
    }

// Access Functions --------------------------------------------------------

    //returns the number of elements in the list
    int length() {
        return length;
    }

    //If cursor is defined, returns the index of the cursor element
    //otherwise, returns -1
    int index() {
        if (index < 0) {
            index = -1;
        }
        return index;
    }

    //Returns front element
    //Pre: length() > 0
    Object front() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }
        return front.data;
    }

    //Returns back element
    //Pre:length()>0
    Object back() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }
        return back.data;
    }

    //Returns cursor element
    //Pre:length()>0
    //Pre:index()>0
    Object get() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }
        else if (this.index() < 0) {
            throw new RuntimeException("Error: index is not defined");
        }

        return cursor.data;
    }

    //Returns true if and only if this List and L are the same
    //integer sequence. The states of the cursors in the two lists
    //are not used in determining equality.
    public boolean equals(Object x) {
        boolean eq = false;
        List L;
        Node N, M;

        if (x instanceof List) {
            L = (List) x;
            N = this.front;
            M = L.front;
            eq = (this.length == L.length);
            while (eq && N != null) {
                eq = N.equals(M);
                N = N.next;
                M = M.next;
            }
        }
        return eq;
    }

// Manipulation Procedures --------------------------------------------------------

    //Resets this list to its original empty state
    void clear() {
        Node N = this.front;

        while(N != null){
            N = N.next;
            this.deleteFront();
        }
    }

    //If List is non-empty, places the cursor under the front element,
    //otherwise, does nothing
    void moveFront() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }
        index = 0;
        cursor = front;
    }

    //If List is non-empty, place the cursor under the back element,
    //otherwise, does nothing
    void moveBack() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }
        cursor = this.back;
        index = (this.length()-1);
    }

    //If cursor is defined and not at front, moves cursor one step toward
    //front of this List, if cursor is defined and at front, cursor becomes
    //undefined, if cursor is undefined does nothing.
    void movePrev() {
        if (this.index() < 0) {
            index = -1;
        }
        else if (this.index() == 0) {
            index = -1;
        }
        cursor = cursor.prev;
        index--;
    }

    //If cursor is defined and not at back, moves cursor one step
    //toward back of List, if cursor is defined and at back, cursor
    //becomes undefined, if cursor is undefined does nothing
    void moveNext() {
        if (this.index() < 0 || this.cursor == null) {
            //does nothing
        }
        if(this.cursor == this.back || index() == length()-1){
            index = -1;
            cursor = null;
        }
        else{
            cursor = cursor.next;
            index++;
        }
    }

    //Insert new element into List. If List is non-empty, insertion
    //takes place before front element
    void prepend(Object data) {
        Node N = new Node(data);

        if (this.length() <= 0) {
            front = back = N;
        } else {
            front.prev = N;
            N.next = front;
            N.prev = null;
            front = N;
        }
        length++;
        index++;
    }

    //Insert new element into List. If List is non-empty, insertion
    //takes place after back element
    void append(Object data) {
        Node N = new Node(data);
        if (this.length() <= 0) {
            front = back = N;
            back.next = null;
        } else {
            N.prev = back;
            back.next = N;
            back = N;
            back.next = null;

        }
        length++;
    }

    //Insert new element before cursor
    //Pre: length()>0, index()>=0
    //Insert new element before cursor
    //Pre: length()>0, index()>=0
    void insertBefore(Object data) {
        Node N = new Node(data);
        Node curr = null;

        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }
        else if (this.index() < 0) {
            throw new RuntimeException("Error: Cursor is undefined");
        }

        curr = this.cursor;

        if(curr == front){
            this.prepend(data);
        }
        else {
            N.prev = curr.prev;
            N.next = curr;
            curr.prev = N;
            if (N.prev != null)
                N.prev.next = N;
            index++;
            length++;
        }
    }

    //Insert new element after cursor
    //Pre:length()>0, index()>=0
    void insertAfter(Object data) {
        Node N = new Node(data);
        Node curr = null;

        if (this.length() <= 0)
        {
            throw new RuntimeException("Error: List is empty");
        }
        if (this.index() < 0)
        {
            throw new RuntimeException("Error: Cursor is undefined");
        }

        curr = this.cursor;

        if(curr == back)
            this.append(data);
        else {
            N.prev = curr;
            N.next = curr.next;
            curr.next = N;
            length++;
        }
    }

    //Deletes the front element. Pre: length()>0
    void deleteFront() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }

        if (this.length() > 1) {
            front = this.front.next;
            if(this.front != null)
                front.prev = null;
            if(this.index() == 0)
                index = -1;
            else
                index--;
        } else {
            front = back = null;
            index--;
        }
        length--;
    }

    //Deletes the back element
    //Pre:length()>0
    void deleteBack() {
        if (this.length() <= 0) {
            throw new RuntimeException("Error: List is empty");
        }

        if (this.length() > 1) {
            back = this.back.prev;
            if(this.back != null)
                this.back.next = null;
            if(this.index() == (length()-1))
                index = -1;
        }
        else {
            front = back = null;
            index = -1;
        }
        length--;
    }

    //Deletes cursor element, making cursor undefined
    //Pre: length()>0, index()>=0
    void delete() {
        Node N = null;
        Node temp = null;

        if(length() <= 0)
            throw new RuntimeException("Error: List is empty");
        if(index() < 0)
            throw new RuntimeException("Error: Index is undefined");

        temp = this.cursor;

        if(temp == this.front){
            deleteFront();
        }
        else if(temp == this.back){
            deleteBack();
        }
        else if(temp != null){
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
            length--;
        }

        cursor = null;
        index = -1;
    }

// Other Methods --------------------------------------------------------

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Node N = front;
        while (N != null) {
            sb.append(" ");
            sb.append(N.toString());
            N = N.next;
        }
        return new String(sb);
    }

    //Returns a new List representing the same integer sequence as this List.
    //The cursor in the new List is undefined, regardless of the state of the
    //cursor in this List. This List is unchanged
    /*
    List copy() {
        List L = new List();
        Node N = this.front;

        while (N != null) {
            L.append(N.data);
            N = N.next;
        }
        return L;
    }
     */
}