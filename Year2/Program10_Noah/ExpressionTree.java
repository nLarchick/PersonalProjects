import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.MalformedInputException;
import java.util.*;


/**
 *
 */
public class ExpressionTree extends ZTree {

    public Queue<String> parse(String expression) {
        Queue<String> infix = new LinkedList<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, "(?<=[-+*()%/])|(?=[-+*()%/])", true);
        while(tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            if(!token.trim().isEmpty()) {
                infix.add(token.trim());
            }
        }
        return infix;
    }

    public List<String> convert(Queue<String> infix) { //DONE!
        List<String> postfix = new ArrayList<>();
        Deque<String> operator = new ArrayDeque<>(); // used as a stack

        while (!infix.isEmpty()){ // While there are tokens to be read

            //System.out.println("The Stack looks like: " + operator.toString());
            //System.out.println("The Queue looks like: " + postfix.toString() + "\n");

            String token = infix.remove(); // read in a token
            if (isInteger(token)){ // if the token is a number
                postfix.add(token); // put it into the postfix queue
            } else if (isOperator(token)){ // if the token is an operator
                while (!operator.isEmpty() && !operator.peek().equals("(") && precedence(operator.peek()) <= precedence(token)){ // while there is an op other than a '(' and op has >= presidence
                    postfix.add(operator.pop()); // pop o2 from stack into postfix
                }
                operator.push(token); // push o1 onto stack
            } else if (token.equals("(")){ // if the token is a '('
                operator.push(token); //Push it onto the stack
            } else if (token.equals(")")){ // if the token is a ')'
                while (!operator.peek().equals("(")){ // while the top of the stack is NOT a '('
                    postfix.add(operator.pop()); // pop the top of the stack onto the postfix
                }
                operator.pop(); // pop top of stack into postfix
            }
        }

        while (!operator.isEmpty()){ // While there are tokens in the operator stack
            postfix.add(operator.pop()); // pop the operator from the stack into the postfix
        }

        return postfix;
    }

    @Override
    public void build(List<String> postfix) {
        Collections.reverse(postfix);
        for (String token : postfix){buildRecursive(root, token);}
    }

    /**
     * Builds an expression tree from the postfix representation returned from the convert method.
     * To build the correct tree, pull tokens from {@code List<String> postfix}, and places
     * them at the next available node in the tree.
     * Here is the exact algorithm:
     * <ol>
     *     <li> If the tree root is null, create a new node containing the token,
     *          assign it to the root, and return {@code true}.
     *     <li> If the right child of the current node is null, create a new node
     *          with the token, place it in the right child, and return {@code true}.
     *     <li> If the right child of the current node is an operator, make a recursive
     *          call passing the right child and token, and return true if successful,
     *          otherwise continue.
     *     <li> If the left child of the current node is null, create a new node with
     *          the token, place it in the left child, and return {@code true}.
     *     <li> If the left child of the current node is an operator, make a recursive
     *          call passing the left child and token, and return {@code true} if successful,
     *          otherwise continue.
     *     <li> If none of the above code returns {@code true}, then the code has failed to add
     *          the token to the tree, so return {@code false}.
     * </ol>
     *
     * Our implementation of the recursive method is ~19 lines of code
     * @param current the current Node being checked
     * @param token the token to add
     * @return {@code true}, if successful
     */
    public boolean buildRecursive(Node current, String token) {
        if (root == null){ // root is null
            Node toadd = new Node(token);// create a new node containing token
            root = toadd;// assign it to the root
            return true;
        }
        if (current.right == null){
            Node toadd = new Node(token);
            current.right = toadd;
            return true;
        }
        if (isOperator(current.right.token)){
            if (buildRecursive(current.right, token)){
                return true;
            } else {
                if (current.left == null){
                    Node toadd = new Node(token);
                    current.left = toadd;
                    return true;
                }
                if (isOperator(current.left.token)){
                    return buildRecursive(current.left, token);
                }
            }
        }
        if (current.left == null){
            Node toadd = new Node(token);
            current.left = toadd;
            return true;
        }
        if (isOperator(current.left.token)){
            return buildRecursive(current.left, token);
        }
        return false;
    }

    @Override
    public String prefix() {
        return prefixRecursive(root);
    }

    /**
     * Concatenates the tokens in the expression tree returned from the
     * {@link #build(List)} method in <b>prefix</b> order.
     * <p>
     * Accumulate the operator first, then the string from the left
     * and right subtrees. Add an extra space after each token.
     * <p>
     * Our implementation of this method is ~2-6 lines of code.
     * @param current the root node
     * @return the tokens in prefix order
     */
    public String prefixRecursive(Node current) { //Some recursive method... [middle, left, right]
        // YOUR CODE HERE
        if (current == null){
            return "";
        } else {
            return current.token + " " + prefixRecursive(current.left) + prefixRecursive(current.right);
        }
    }

    @Override
    public String infix() {
        return infixRecursive(root);
    }

    /**
     * Concatenates the tokens in the expression tree returned from the
     * {@link #build(List)} method in <b>infix</b> order.
     * <ol>
     *     <li> Accumulate the string from the left subtree
     *     <li> Add the operator
     *     <li> Accumulate the string from the right subtree
     * </ol>
     * This method should add parentheses to maintain the correct evaluation order,
     * add a left parentheses before traversing the left subtree, and a right
     * parentheses after traversing the right subtree.
     * Do not add any space to the expression string.
     * <p>
     * Our implementation of this method is ~2-6 lines of code.
     * @param current
     * @return the tokens in infix order
     */
    public String infixRecursive(Node current) { //Some recursive method... [left, middle, right]
        // YOUR CODE HERE
        if (current == null){
            return "";
        }
        if (isOperator(current.token)){
            return "(" + infixRecursive(current.left) + current.token + infixRecursive(current.right) + ")"; //IDK about the parenthesis...
        } else {
            return infixRecursive(current.left) + current.token + infixRecursive(current.right); //IDK about the parenthesis...
        }
    }

    @Override
    public String postfix() {
        return postfixRecursive(root);
    }

    /**
     * Concatenates the tokens in the expression tree returned from the
     * {@link #build(List)} method in <b>postfix</b> order.
     * First accumulate the string from the left and right subtrees, then add the
     * operator. Add an extra space after each token.
     * <p>
     * Our implementation of this method is ~2-6 lines of code.
     * @param current reference to the current node (starts with root)
     * @return a String representing the tree in postfix order
     */
    public String postfixRecursive(Node current) { //Some recursive method... [left, right, middle]
        // YOUR CODE HERE
        if (current == null){
            return "";
        }
        return (postfixRecursive(current.left) + postfixRecursive(current.right) + current.token + " ");
    }

    public int evaluate() {
        return evaluateRecursive(this.root);
    }

    /**
     * Traverses the expression tree and produces the correct answer, which should be an integer.
     * To evaluate, call the recursive version of the method to get the result from the left
     * subtree, do the same for the right subtree, then combine these two results using the
     * operator. A case statement based on the operator is needed to perform the mathematical operation.
     * <p>
     * Our implementation uses one helper method (~7 lines of code) and is, itself, ~2 lines of code.
     * @param current
     * @return
     */
    public int evaluateRecursive(Node current) { // Another recursive,
        // YOUR CODE HERE
        if (current == null){
            System.out.println("THAT BIH IS EMPTY!"); // Do nothing, the bih is empty
            return -1;
        } else if (isInteger(current.token)){ // current is a #
            return Integer.parseInt(current.token);// return that number
        } else if (isOperator(current.token)){ // current is a operator
            if (current.token.equals("+")){ // current == '+'
                return (evaluateRecursive(current.left) + evaluateRecursive(current.right));
            } else if (current.token.equals("-")){ // current == '-'
                return (evaluateRecursive(current.left) - evaluateRecursive(current.right));
            } else if (current.token.equals("*")){ // current == '*'
                return (evaluateRecursive(current.left) * evaluateRecursive(current.right));
            } else if (current.token.equals("/")){ // current == '/'
                return (evaluateRecursive(current.left) / evaluateRecursive(current.right));
            } else if (current.token.equals("%")){ // current == '%'
                return (evaluateRecursive(current.left) % evaluateRecursive(current.right));
            }
        }
        System.out.println("SOMETHING WHENT WRONG!");
        return -1;
    }
    
            // Test code for -----------------------------------------------------[TESTING]--------------------------------------------->
    public static void main(String[] args) {

        // Instantiate student code
        ExpressionTree eTree = new ExpressionTree();

        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();

        System.out.println("Original Expression: " + expression);

        // Verify parse
        Queue<String> infix = eTree.parse(expression);
        System.out.println("Infix Tokens: " + infix.toString());

        // Verify convert
        List<String> postfix = eTree.convert(infix);
        System.out.println("Postfix Tokens: " + postfix.toString());

        // Verify build
        eTree.build(postfix);
        
        System.out.println("Build: complete");

        // Verify prefix
        System.out.println("Prefix: " + eTree.prefix());

        // Verify infix
        System.out.println("Infix: " + eTree.infix());

        // Verify postfix
        System.out.println("Postfix: " + eTree.postfix());

        // Verify evaluate
        System.out.println("Evaluate: " + eTree.evaluate());

        // Verify display
        System.out.println("Display: complete");
    }

   

}
