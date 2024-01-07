package com.mycompany.calculator_app.scientific;

public class evalII {
    static final java.util.Set<String> specialOps=new java.util.HashSet<>(java.util.Arrays.asList(new String[]{"sin"})); 
    static int getOperatorPrecedence(char op){	//priority of ^,xth root > aPb,aCb > *,/,%   
        if(op=='+'||op=='-') return 1;	//sqrt,inverse,sin,tan.cos,sinh,tanh,cosh,fact are special functions of immediate priority
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
    static double operate(double op1,double op2,char op){
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
    static double operate(String operation,double op){
    	switch(operation){
    		case "sin" : return Math.sin(op);
                case "cos" : return Math.cos(op);
                case "tan" : return Math.tan(op);
                default : return Integer.MAX_VALUE;
        }
    }
    static String proper=""; static int i=0;
    static double calculate(String exp) throws RuntimeException {
        java.util.Stack<Double> operand=new java.util.Stack<>();
        java.util.Stack<Character> operator=new java.util.Stack<>();
        java.util.Stack<String> SPF=new java.util.Stack<>();
        
        if(isOperator(exp.charAt(0))) operand.push(0.0);
        for(int i=0;i<exp.length();i++){
            char input=exp.charAt(i);
            if(isOperand(input)){
            	double num=0,frac=1; boolean dot=false;
            	do{ 	input=exp.charAt(i); 
            		if(input=='.'){ 
                                if(dot) throw new RuntimeException(); dot=true; i++; continue;
                       }
                       if(!isOperand(input)){ break; }
            		if(dot){
                                frac/=(double)10;
                                num+=frac*(input-'0');
                       }
                       else
            			num=10*num+(input-'0'); 
            		i++; 
            	} while(i<exp.length());
            	i--; while(!SPF.empty()&&!"(".equals(SPF.peek())){ num=operate(SPF.pop(),num); } 
                operand.push(num);
                //System.out.println(operand.peek());
            }
            else if(input=='('){ 
            	operator.push(input); if(isOperator(exp.charAt(i+1))) operand.push(0.0); if(!SPF.empty()) SPF.push("("); 
            }
            else if(input==')'){
                while(!operator.empty()&&operator.peek()!='('){
                    double op1=operand.pop(),op2=operand.pop();
                    operand.push(operate(op2,op1,operator.pop()));
                }
                operator.pop(); double oprnd=operand.pop(); if(!SPF.empty()) SPF.pop();
                while(!SPF.empty()&&!"(".equals(SPF.peek())){ oprnd=operate(SPF.pop(),oprnd); } operand.push(oprnd); 
            }
            else if(isOperator(input)){
                while(!operator.empty()&&
                        getOperatorPrecedence(operator.peek())>=getOperatorPrecedence(input))
                {
                    //System.out.println(operator.peek());
                    double op1=operand.pop(),op2=operand.pop();
                    operand.push(operate(op2,op1,operator.pop()));
                    //System.out.println(operand.peek());
                }
                //System.out.println(getOperatorPrecedence('('));
                operator.push(input);
            }
            else{
            	StringBuilder special=new StringBuilder();
            	do{
            		input=exp.charAt(i);
            		special.append(input);
                        i++; if(specialOps.contains(special.toString())) break;
            	}
            	while(exp.charAt(i)>='a'&&exp.charAt(i)<='z');
            	SPF.push(special.toString());
                //System.out.println(SPF.peek());
            	//if(exp.charAt(i)=='(') SPF.push("(");
            	i--;
            }
        }
        while(!operator.empty()){
            double op1=operand.pop(),op2=operand.pop();
            //System.out.println(op1+" "+op2);
            operand.push(operate(op2,op1,operator.pop()));
            //System.out.println(operand.peek());
        }
        // EmptyStackException wrong expression
        // ArithmeticException math error
        return operand.pop();
    }
    static void format(String exp){
		
		int parenth=0;
		
		for(;i<exp.length();i++){
			char c=exp.charAt(i);

			if((c=='+'||c=='-')&&(i>0&&isOperator(exp.charAt(i-1)))){ proper+='('+""+c; parenth++; continue; }
		       	if(c=='('){ proper+=c; i++; format(exp); continue; }
			if(!isOperand(c)){ while(parenth>0){ proper+=')'; parenth--; } }
			proper+=c; 
			if(c==')') break;
		}
		while(parenth>0){ proper+=')'; parenth--; }
    }
    static boolean validate(String exp){
		int parenth=0;
                for(int i=0;i<exp.length();i++){
                        if(exp.charAt(i)=='('){ if(i>0&&(isOperand(exp.charAt(i-1))||exp.charAt(i-1)==')')) return false; parenth++; }
                        else if(exp.charAt(i)==')'){
                                if(i==0||isOperator(exp.charAt(i-1))||exp.charAt(i-1)=='('){ //System.out.println(exp.charAt(i-1)+"y1"); 
                                return false; }
                                parenth--;  }
                        else if(exp.charAt(i)=='+'||exp.charAt(i)=='-'){ if(i==exp.length()-1){ //System.out.println("y2"); 
                            return false; } }
                        else if(exp.charAt(i)=='*'||exp.charAt(i)=='/'){
                                if(i==0||i==exp.length()-1||isOperator(exp.charAt(i-1))||exp.charAt(i-1)=='('){ //System.out.println("y3"); 
                                    return false; }
                        }
                }
                return parenth==0;
    }
    static double evaluate(String exp) throws RuntimeException {
		format(exp);
		i=0;
		System.out.println(proper);
		if(!validate(proper)) throw new RuntimeException(); 
		//return 20;
		System.out.println("valid");
		//for(int i=0;i<proper.length();i++) System.out.print((int)proper.charAt(i)+" "); System.out.println();
		return calculate(proper);
    }
    public static void main(String[] args){
    	System.out.println(4.0/3%2);
        java.util.Scanner parse=new java.util.Scanner(System.in);
        //System.out.println(4*3.0%2);
        //System.out.println(-8%10);
        String exp=parse.nextLine();
        //String exp="2*((17--15))/5-2";
        try{
		System.out.println(evaluate(exp));
	}
	catch(Exception e){ System.out.println("Wrong expression!"+e); }
        //String expression=parse.nextLine();
        //System.out.println(evaluate(expression));
    }
}
