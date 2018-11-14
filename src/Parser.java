import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;

class TreeNode {
    String curNode;
    TreeNode parent;
    ArrayList<TreeNode> children;

    public TreeNode(String curNode, TreeNode parent) {
        this.curNode = curNode;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode node) {
        this.children.add(node);
    }
}

public class Parser {
    public ArrayList<TokenInformation> tokens;
    public int curIndex = 0;

    public Parser(ArrayList<TokenInformation> tokens) {
        this.tokens = tokens;
    }

    public TreeNode E(TreeNode node) {
        TreeNode curNode = new TreeNode("E",node);
        System.out.println("E->TE'");
        TreeNode TNode = T(curNode);
        if (TNode != null) {
            curNode.addChild(TNode);
            TreeNode E2Node = E2(curNode);
            if (E2Node != null) {
                curNode.addChild(E2Node);
                return curNode;
            }
            return null;
        }
        return null;
    }

    public TreeNode E() {
        TreeNode curNode = new TreeNode("E",null);
        System.out.println("E->TE'");
        TreeNode TNode = T(curNode);
        if (TNode != null) {
            curNode.addChild(TNode);
            TreeNode E2Node = E2(curNode);
            if (E2Node != null) {
                curNode.addChild(E2Node);
                return curNode;
            }
            return null;
        }
        return null;
    }

    public TreeNode E2(TreeNode node) {
        TreeNode curNode = new TreeNode("E'",node);
        if (tokens.get(curIndex).type == TokenType.PLUS || tokens.get(curIndex).type == TokenType.MINUS) {
            if (tokens.get(curIndex).type == TokenType.PLUS) {
                System.out.println("E'->+TE'");
                TreeNode plusNode = new TreeNode("+", curNode);
                curNode.addChild(plusNode);
            } else {
                TreeNode plusNode = new TreeNode("-", curNode);
                curNode.addChild(plusNode);
                System.out.println("E'->-TE'");
            }
            readNextToken();
            TreeNode TNode = T(node);
            if (TNode != null) {
                curNode.addChild(TNode);
                TreeNode E2Node = E2(node);
                if (E2Node != null) {
                    curNode.addChild(E2Node);
                    return curNode;
                }
                return null;
            }
            return null;
        }
        System.out.println("E'->e");
        TreeNode nullNode = new TreeNode("e", curNode);
        curNode.addChild(nullNode);
        return curNode;
    }

    public TreeNode T(TreeNode node) {
        TreeNode curNode = new TreeNode("T",node);
        System.out.println("T->FT'");
        TreeNode FNode = F(curNode);
        if (FNode != null) {
            curNode.addChild(FNode);
            TreeNode T2Node = T2(curNode);
            if (T2Node != null) {
                curNode.addChild(T2Node);
                return curNode;
            }
            return null;
        }
        return null;
    }

    public TreeNode T2(TreeNode node) {
        TreeNode curNode = new TreeNode("T'",node);
        if (tokens.get(curIndex).type == TokenType.MULTIPLE || tokens.get(curIndex).type == TokenType.DIVIDE) {
            if (tokens.get(curIndex).type == TokenType.MULTIPLE) {
                System.out.println("T'->*FT'");
                TreeNode plusNode = new TreeNode("*", curNode);
                curNode.addChild(plusNode);
            } else {
                System.out.println("T'->/FT'");
                TreeNode plusNode = new TreeNode("/", curNode);
                curNode.addChild(plusNode);
            }
            if (readNextToken()) {
                TreeNode FNode = F(curNode);
                if (FNode != null) {
                    curNode.addChild(FNode);
                    TreeNode T2Node = T(curNode);
                    if (T2Node != null) {
                        curNode.addChild(T2Node);
                        return curNode;
                    }
                    return null;
                }
                return null;

            }
            return null;
        }
        System.out.println("T'->e");
        TreeNode nullNode = new TreeNode("e", curNode);
        curNode.addChild(nullNode);
        return curNode;
    }

    public TreeNode F(TreeNode node) {
        TreeNode curNode = new TreeNode("F",node);
        if (tokens.get(curIndex).type == TokenType.LEFTBRACKET) {
            System.out.println("F->(E)");
            TreeNode leftBracketNode = new TreeNode("(", curNode);
            curNode.addChild(leftBracketNode);
            if (readNextToken()) {
                TreeNode ENode = E(curNode);
                if (ENode != null) {
                    curNode.addChild(ENode);
                    if (tokens.get(curIndex).type == TokenType.RIGHTBRACKET) {
                        TreeNode rightBracketNode = new TreeNode(")", curNode);
                        curNode.addChild(rightBracketNode);
                        if (readNextToken())
                            return curNode;
                        else {
                            curIndex--;
                            return curNode;
                        }
                    }
                }
                return null;
            }
        } else if (tokens.get(curIndex).type == TokenType.IDENTIFIER || tokens.get(curIndex).type == TokenType.INT
                || tokens.get(curIndex).type == TokenType.DOUBLE) {
            System.out.println("F->i");
            TreeNode iNode = new TreeNode("i", curNode);
            curNode.addChild(iNode);
            readNextToken();
            return curNode;
        }

        return null;
    }

    public boolean readNextToken() {
        if (curIndex < tokens.size() - 1) {
            curIndex++;
            return true;
        } else {
            //curIndex++;
            return false;
        }
    }

    public TreeNode parser() {
        TreeNode root =  E();
        return root;
    }

    

/*
public static void main(String[] args) {

    }
 */

}
