package compression;

/**
 * Implementation of a doubly linked list that contains an additional pointer to
 * the "current" node. This enables get/insert/delete operations relative to the
 * current position.
 *
 * There is also a notion of having gone "off" the list (e.g., by going next from
 * the last (right of the tail) or back from the first element (left of the head)).
 */

public class CurDoublyLinkedList<E> extends DoublyLinkedList<E> {

	protected DoublyLinkedList<E>.Node current; // pointer to current node

	private boolean off_left; // current has been shifted off left edge (left from head of doubly linked list)
	private boolean off_right; // current has been shifted off right edge (right from tail of doubly linked list)

	/**
	 * @post: constructs an empty list, current points to null, off states are false
	 */
	public CurDoublyLinkedList() {
		current = null;
		off_left = false;
		off_right = false;
	}

	/**
	 * set current to the first element of list (head)
	 *
	 * @pre: list is non-empty
	 *
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to head"
	 * 
	 * @post: current set to first node of list (head), off states are false
	 *
	 */
	public void first() {
		if(this.size()==0) {
			throw new IllegalStateException("Empty list cannot move current to head");
		}
		
		current = first;
		off_left = false;
		off_right = false;
	}

	/**
	 * set current to last element of list (tail)
	 *
	 * @pre: list is non-empty
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to tail"
	 * 
	 * 
	 * @post: current set to last node of list (tail), off states are false
	 *
	 */
	public void last() {
		if(this.size()==0) {
			throw new IllegalStateException("Empty list cannot move current to head");
		}
		
		current = last;
		off_left = false;
		off_right = false;
	}

	/**
	 * Move current pointer one node to the right
	 *
	 * @pre: list is non-empty and off right state is false
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to the right"
	 * 
	 *       throws IllegalStateException if off right state is true with message
	 *       "Current is already off right, cannot move it further"
	 * 
	 * @post: if the off left state is true, then current points to head and off
	 *        left becomes false. Else move current pointer one node to the right.
	 *        If already at tail, current points to null and off right state becomes
	 *        true.
	 *
	 */
	public void next() {
		//exceptions for empty list and pointer already null
		if(this.size()==0) {
			throw new IllegalStateException("Empty list cannot move current to the right");
		}else if (off_right) {
			throw new IllegalStateException("Current is already off right, cannot move it further");
		}
		
		//moving pointer to the right
		if(off_left) {
			first();
			off_left = false;
		}else if(current == last){
			current = null;
			off_right = true;
		}else{
			current = current.next;
		}
	}

	/**
	 * Move current pointer one node to the left
	 *
	 * @pre: list is non-empty and off left state is false
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to the left"
	 * 
	 *       throws IllegalStateException if off left state is true with message
	 *       "Current is already off left, cannot move it further"
	 * 
	 * @post: if the off right state is true, then current points to tail and off
	 *        right becomes false. Else move current pointer one node to the left.
	 *        If already at head, current points to null and off left state becomes
	 *        true.
	 */
	public void back() {
		//Exceptions
		if(this.size()==0) {
			throw new IllegalStateException("Empty list cannot move current to the left");
		}else if (off_left) {
			throw new IllegalStateException("Current is already off left, cannot move it further");
		}
		
		//move pointer to the left
		if(off_right) {
			last();
			off_right = false;
		}else if(current == first) {
			current = null;
			off_left = true;
		}else {
			current = current.prev;
		}
	}

	/**
	 * Check whether current pointer is off the right side of the list (right of the
	 * tail)
	 * 
	 * @return whether current is off right side of list
	 */
	public boolean isOffRight() {
		return off_right;
	}

	/**
	 * Check whether current pointer is off the left side of the list (left of the
	 * head)
	 * 
	 * @return whether current is off left side of list
	 */
	public boolean isOffLeft() {		
		return off_left;
	}

	/**
	 * Check whether current pointer is off the right side of the list (right of the
	 * tail) or off the left side of the list (left of the head)
	 * 
	 * @return whether current is either off left or off right side of list
	 */
	public boolean isOff() {
		if(isOffLeft()||isOffRight()) {
			return true;
		}
		
		return false;
	}

	/**
	 * Returns the value of the node that current points to
	 *
	 * @pre: list is non-empty and current is not off list
	 *
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 *       throws IllegalStateException if current is off list with message
	 *       "Current is off list"
	 * 
	 */
	public E currentValue() {
		//Exceptions for empty list and bad current
		if(size()==0) {
			throw new IllegalStateException("Empty list, current points to null");
		}else if(isOff()) {
			throw new IllegalStateException("Current is off list");
		}
		
		return current.item;
	}

	/**
	 * Create a new node with specified value and make it the new head. Move current
	 * pointer to point to the newly-created node.
	 *
	 * @pre: Given value for new node to be created is not null
	 * 
	 *       throws IllegalArgumentException if given value is null "Cannot create a
	 *       node that contains the null value"
	 *
	 * @post: creates a new node with specified element and makes it the new head.
	 *        Upon creation, current now points to the newly-created node. Off left
	 *        and off right states are set to false.
	 * 
	 */
	public void addFirst(E newFirst) {
		//Exception
		if(newFirst == null) {
			throw new IllegalArgumentException("Cannot create a node that contains the null value");
		}
		
		super.addFirst(newFirst);
		
		current = this.first;
	}

	/**
	 * Create a new node with specified value and make it the new tail. Move current
	 * pointer to point to the newly-created node.
	 *
	 * @pre: Given value for new node to be created is not null
	 * 
	 *       throws IllegalArgumentException if given value is null "Cannot create a
	 *       node that contains the null value"
	 *
	 * @post: creates a new node with specified element and makes it the new tail.
	 *        Upon creation, current now points to the newly-created node. Off left
	 *        and off right states are set to false.
	 * 
	 */
	public void addLast(E newLast) {
		//Exception Handling
		if(newLast == null) {
			throw new IllegalArgumentException("Cannot create a node that contains the null value");
		}
		
		super.addLast(newLast);
		
		current = this.last;
	}

	/**
	 * Remove the head node and return its value. Current now points to the new
	 * head.
	 *
	 * @pre: list is non-empty.
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 *
	 * @post: removes the head node and returns its value. Current now points either
	 *        to the new head if the list has at least one node (and therefore the
	 *        off left and right state should be false), or points to null with the
	 *        off left state becoming true.
	 * 
	 */
	public E removeFirst() {
		if(this.size()==0) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		
		if(this.size()==1){
			current = null;
			off_left = true;
		}else{
			current = this.first.next;
			off_left = false;
			off_right = false;
		}
		
		return super.removeFirst();
	}

	/**
	 * Remove the tail node and return its value. Current now points to null and off
	 * right becomes true (off left is false)
	 * 
	 * @pre: list is non-empty.
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 *
	 * @post: removes the tail node and returns its value. Current now points to
	 *        null with the off right state becoming true (and off left becoming
	 *        false).
	 * 
	 */
	public E removeLast() {
		if(this.size()==0) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		
		current = null;
		off_right = true;
		off_left = false;
		
		return super.removeLast();
	}

	/**
	 * Return value of the head and point current to head
	 *
	 * @pre: list is non-empty
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 * @post: points current to first element of list (head). Off states are false
	 *
	 */
	public E getFirst() {
		if(this.size()==0) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		
		off_left = false;
		off_right = false;
		
		current = this.first;
		
		return super.first.item;
	}

	/**
	 * Return value of the tail and point current to tail
	 *
	 * @pre: list is non-empty
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 * @post: points current to last element of list (tail). Off states are false
	 *
	 */
	public E getLast() {
		if(this.size()==0) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		
		off_left = false;
		off_right = false;
		
		current = this.last;
		
		return super.last.item;
	}

	/**
	 * Create a new node with specified value immediately after the current node.
	 * Move current pointer to point to the newly-created node.
	 *
	 * @pre: Given value for new node to be created is not null, list is non-empty,
	 *       and current is not off list
	 * 
	 *       throws IllegalArgumentException if given value is null "Cannot create a
	 *       node that contains the null value"
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 *       throws IllegalStateException if current is off list with message
	 *       "Current is off list"
	 *
	 * @post: creates a new node with specified element and adds it right after the
	 *        node that current points to. Upon creation, current now points to the
	 *        newly-created node.
	 * 
	 */
	public void addAfterCurrent(E value) {
		//Exception Handling
		if(value==null) {
			throw new IllegalArgumentException("Cannot create a node that contains the null value");
		}else if(this.size()==0) {
			throw new IllegalArgumentException("Empty list, current points to null");
		}else if(isOff()) {
			throw new IllegalArgumentException("Current is off list");
		}
		
		//adds new node after current
		if(super.getIndex(current.item)+1==this.size()) {
			super.addLast(value);
		}else {
			super.add(super.getIndex(current.item)+1, value);
		}
		
		
		current = current.next;
	}

	/**
	 * Removes the node that current points to. Current now points to the successor.
	 *
	 * @pre: List is non-empty and current is not off list
	 * 
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 *       throws IllegalStateException if current is off list with message
	 *       "Current is off list"
	 *
	 * @post: Removes the node that current points to and moves current to its
	 *        successor
	 */
	public void removeCurrent() {
		//Exceptions
		if(this.size()==0) {
			throw new IllegalStateException("Empty list, current points to null");
		}else if(isOff()) {
			throw new IllegalStateException("Current is off list");
		}
		
		//Finds index for current
		Node finger = first;
				
		int index = 0;
		while(finger!=current) {
			finger = finger.next;
			index++;
		}
		
		current = current.next;
		
		super.remove(index);
	}

	/**
	 * Clear doubly linked list and reset current pointer and off states
	 */
	public void clear() {
		super.clear();
		
		current = null;
		off_left = false;
		off_right = false;
	}

	/**
	 * @return readable representation of table
	 * 
	 *         Shows contents of underlying list rather than all elements of the
	 *         table.
	 */
	public String toString() { // do not change
		return super.toString() + "\nCurrent is " + current;
	}

	/**
	 * An alternative representation of the object
	 * 
	 * @return a string representation of the list, with each element on a new line.
	 */
	public String otherString() { // do not change
		DoublyLinkedList<E>.Node finger = first;
		StringBuilder ans = new StringBuilder("CurDoublyLinkedList:\n");
		while (finger != null) {
			ans.append(finger + "\n");
			finger = finger.next;
		}
		return ans.toString();
	}
}
