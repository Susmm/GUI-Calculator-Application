/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculator_app.scientific;

/**
 *
 * @author susmit
 */
public class infix_eval {
    static int getOperatorPrecedence(char op){
        if(op=='+'||op=='-') return 1;
        if(op=='*'||op=='/'||op=='%') return 2;
        if(op=='^') return 3;
        return -1;
    }
    static boolean isOperator(char ch){
        return ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='^'||ch=='%';
    }
    static boolean isOperand(char ch){
        return ch>='0'&&ch<='9';
    }
    static int operate(int op1,int op2,char op){
        switch(op){
            case '+' : return op1+op2;
            case '-' : return op1-op2;
            case '*' : return op1*op2;
            case '/' : return op1/op2;
            case '%' : return op1%op2;
            case '^' : return (int)Math.pow(op1,op2);
            default : return Integer.MAX_VALUE;
        }
    }
    static int evaluate(String exp){
        java.util.Stack<Integer> operand=new java.util.Stack<>();
        java.util.Stack<Character> operator=new java.util.Stack<>();
        
        for(int i=0;i<exp.length();i++){
            char input=exp.charAt(i);
            if(isOperand(input)) operand.push(input-'0');
            if(input=='(') operator.push(input);
            if(input==')'){
                while(!operator.empty()&&operator.peek()!='('){
                    int op1=operand.pop(),op2=operand.pop();
                    operand.push(operate(op2,op1,operator.pop()));
                }
                operator.pop();
            }
            if(isOperator(input)){
                while(!operator.empty()&&
                        getOperatorPrecedence(operator.peek())>=getOperatorPrecedence(input))
                {
                    System.out.println(operator.peek());
                    int op1=operand.pop(),op2=operand.pop();
                    operand.push(operate(op2,op1,operator.pop()));
                    //System.out.println(operand.peek());
                }
                //System.out.println(getOperatorPrecedence('('));
                operator.push(input);
            }
        }
        while(!operator.empty()){
            int op1=operand.pop(),op2=operand.pop();
            //System.out.println(op1+" "+op2);
            operand.push(operate(op2,op1,operator.pop()));
            //System.out.println(operand.peek());
        }
        // EmptyStackException wrong expression
        // ArithmeticException math error
        return operand.pop();
    }
    public static void main(String[] args){
        java.util.Scanner parse=new java.util.Scanner(System.in);
        System.out.println(4*3.0%2);
        //System.out.println(-8%10);
        String expression=parse.nextLine();
        System.out.println(evaluate(expression));
    }
}
