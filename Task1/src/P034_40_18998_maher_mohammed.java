import java.util.ArrayList;

public class P034_40_18998_maher_mohammed {

	public static void main(String[] args) {		
		System.out.println("First DFA");
		System.out.println();
		DFA dfa1 = new DFA("0,1,2;1,3,4;2,4,3;3,1,2;4,4,4#3");
		boolean run1 = run(dfa1,"00110011");
		System.out.println(run1);
		
		
		boolean run2 = run(dfa1,"00001100");
		System.out.println(run2);
		
		
		boolean run3 = run(dfa1,"10101");
		System.out.println(run3);
		
		
		boolean run4 = run(dfa1,"00");
		System.out.println(run4);
		
		
		boolean run5 = run(dfa1,"01");
		System.out.println(run5);
		
		
		System.out.println();
		System.out.println();
		System.out.println("Second DFA");
		System.out.println();
		
		
		DFA dfa2 = new DFA("0,1,2;1,3,2;2,1,4;3,3,5;4,6,4;5,3,7;6,7,4;7,7,7#7");
		boolean run1_2 = run(dfa2,"0111010");
		System.out.println(run1_2);
		
		
		boolean run2_2 = run(dfa2,"00");
		System.out.println(run2_2);
		
		
		boolean run3_2 = run(dfa2,"0011");
		System.out.println(run3_2);
		
		
		boolean run4_2 = run(dfa2,"1100");
		System.out.println(run4_2);
		
		
		boolean run5_2 = run(dfa2,"0001011110");
		System.out.println(run5_2);

	}
	
	
	public static boolean run(DFA dfa, String binaryString) {
		String currentState = dfa.getStartState();
		for (int i = 0; i < binaryString.length(); i++) {
			
			currentState = dfa.getEndState(currentState ,binaryString.charAt(i));
			
		}
		
		if(dfa.isAcceptanceState(currentState)) {
			return true;
		}
		return false;
	}

}

class DFA{
	private String description;
	private ArrayList<Character> setOfStates;
	private ArrayList<Character> setOfAcceptanceStates;
	private String startState = "0";
	private ArrayList<Transition> transitions;
	
	
	public DFA(String description) {
		this.description = description;
		this.setOfStates = new ArrayList<Character>();
		this.setOfAcceptanceStates = new ArrayList<Character>();
		this.transitions = new ArrayList<Transition>();
		extractFeaturesOfDfa(description);
	}
	
	
	public boolean isAcceptanceState(String currentState) {
		if(this.getSetOfAcceptanceStates().contains(currentState.charAt(0))) {
			return true;
		}
		return false;
	}


	private void extractFeaturesOfDfa(String description) {
		String[] p_and_s = this.description.split("#");		//p in index0 and s in index 1
		String p = p_and_s[0];
		String s = p_and_s[1];
		String[] trans = p.split(";");
		
		//extract the set of states
		for (int i = 0; i < p.length(); i++) {
			if(p.charAt(i) != ',' && p.charAt(i) != ';') {
				if(!setOfStates.contains(p.charAt(i))) {
					setOfStates.add(p.charAt(i));
				}
			}
		}
		
		//extract the set of acceptance states
		
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != ',') {
				if(!setOfAcceptanceStates.contains(s.charAt(i))) {
					setOfAcceptanceStates.add(s.charAt(i));
				}
			}
		}
		
		
		//extract the transitions
		for (int i = 0; i < trans.length; i++) {
			String[] transs = trans[i].split(",");
			Transition t1 = new Transition(transs[0], transs[1], "0");
			Transition t2 = new Transition(transs[0], transs[2], "1");
			this.transitions.add(t1);
			this.transitions.add(t2);
		}
		
		
	}
	
	public void printDFA() {
		System.out.println("the initial state");
		System.out.println(this.startState);
		
		
		System.out.println("set of states");
		for (int i = 0; i < this.getSetOfStates().size(); i++) {
			System.out.println(this.getSetOfStates().get(i));
		}
		
		System.out.println("set of acceptance states");
		for (int i = 0; i < this.getSetOfAcceptanceStates().size(); i++) {
			System.out.println(this.getSetOfAcceptanceStates().get(i));
		}
		
		System.out.println("transitions");
		for (int i = 0; i < this.getTransitions().size(); i++) {
			System.out.println(this.getTransitions().get(i).getStart() + this.getTransitions().get(i).getSymbol() + this.getTransitions().get(i).getEnd());
		}
	}
	
	
	public ArrayList<Character> getSetOfStates() {
		return this.setOfStates;
	}
	
	public ArrayList<Character> getSetOfAcceptanceStates() {
		return this.setOfAcceptanceStates;
	}
	
	public ArrayList<Transition> getTransitions() {
		return this.transitions;
	}
	
	public String getStartState() {
		return this.startState;
	}
	
	public String getEndState(String startState, Character transition) {
		
		for (int i = 0; i < this.getTransitions().size(); i++) {
			if(this.getTransitions().get(i).getStart().equals(startState) && this.getTransitions().get(i).getSymbol().equals(transition.toString())) {
				return this.getTransitions().get(i).getEnd();
			}
		}
		return "";
	}
}


class Transition{
	String start;
	String end;
	String symbol;
	
	public Transition(String start, String end, String symbol) {
		this.start = start;
		this.end = end;
		this.symbol = symbol;
	}
	
	public String getStart() {
		return this.start;
	}
	
	public String getEnd() {
		return this.end;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
}
