package task2;

import java.util.ArrayList;


public class P34_40_18998_maher_mohammed {

	public static void main(String[] args) {

		DFA dfa = new DFA("1,2;4,5#2,3;6,7#0,1;0,4;3,1;3,4;5,6;5,8;7,6;7,8#8");
		System.out.println(run(dfa,"0101010"));
		System.out.println(run(dfa,"01111"));
		System.out.println(run(dfa,""));
		System.out.println(run(dfa,"111"));
		System.out.println(run(dfa,"0101"));
		
		
		System.out.println();
		
		DFA dfa2 = new DFA("2,3;7,8;9,10#0,1;6,7#1,2;1,4;3,6;4,5;5,6;8,9;8,11;10,9;10,11#11");
		System.out.println(run(dfa2,"110"));
		System.out.println(run(dfa2,"111"));
		System.out.println(run(dfa2,"101000"));
		System.out.println(run(dfa2,"101"));
		System.out.println(run(dfa2,"00"));
	}
	
	
	public static boolean run(DFA dfa, String test) {
		String currentState = dfa.getStartState();
		for (int i = 0; i < test.length(); i++) {
			currentState = dfa.getNextState(currentState,test.charAt(i));
		}
		
		if(dfa.isAccepted(currentState)) {
			return true;
		}
		
		return false;
	}
	

}

class NFA{
	private ArrayList<String> states;
	private ArrayList<String> acceptStates;
	private ArrayList<Transition> e_transitions;
	private ArrayList<Transition> zeroTransitions;
	private ArrayList<Transition> oneTransitions;
	private ArrayList<String> closures;
	
	//every state has its closures at the same index
	
	public NFA(String nfa) {
		this.closures = new ArrayList<String>();
		this.states = new ArrayList<String>();
		this.acceptStates = new ArrayList<String>();
		this.e_transitions = new ArrayList<Transition>();
		this.zeroTransitions = new ArrayList<Transition>();
		this.oneTransitions = new ArrayList<Transition>();
		
		extractFeatures(nfa);
		
		
	}
	

	public void extractFeatures(String nfa) {
		String[] features = nfa.split("#");
		String zero_trans = features[0];
		String one_trans = features[1];
		String e_trans = features[2];
		String accept_states = features[3];
		
		fillStates(zero_trans,one_trans,e_trans,accept_states);
		fillE_Transitions(e_trans);
		fillZeroTransitions(zero_trans);
		fillOneTransitions(one_trans);
		fillAcceptanceStates(accept_states);
		fillClosures();
	}
	
	
	private void fillClosures() {
		for (int i = 0; i < this.states.size(); i++) {
			String state = this.states.get(i);
			String closures = "";
			for (int j = 0; j < this.e_transitions.size(); j++) {
				if(this.e_transitions.get(j).getStart().equals(state))  {
					if(closures.length() == 0) {
						closures = closures + this.e_transitions.get(j).getEnd();						
					}else {
						closures = closures +","+ this.e_transitions.get(j).getEnd();
					}
				}
			}
			this.closures.add(closures);
		}
	
	}

	private void fillStates(String zero_trans, String one_trans, String e_trans, String accept_states) {
		//from zero

		String[] zeroT = zero_trans.split(";");
		for (int i = 0; i < zeroT.length; i++) {
			String[] trans = zeroT[i].split(",");
			if(!this.states.contains(trans[0])) {
				this.states.add(trans[0]);
			}
			
			if(!this.states.contains(trans[1])) {
				this.states.add(trans[1]);
			}
		}
		
		//from one
		String[] oneT = one_trans.split(";");
		for (int i = 0; i < oneT.length; i++) {
			String[] trans = oneT[i].split(",");
			if(!this.states.contains(trans[0])) {
				this.states.add(trans[0]);
			}
			
			if(!this.states.contains(trans[1])) {
				this.states.add(trans[1]);
			}
		}
		
		//from e
		String[] eT = e_trans.split(";");
		for (int i = 0; i < eT.length; i++) {
			String[] trans = eT[i].split(",");
			if(!this.states.contains(trans[0])) {
				this.states.add(trans[0]);
			}
			
			if(!this.states.contains(trans[1])) {
				this.states.add(trans[1]);
			}
		}
		
		
	}

	// accept states of NFA
	private void fillAcceptanceStates(String accept_states) {
		String[] accept = accept_states.split(",");
		for (int i = 0; i < accept.length; i++) {
			this.acceptStates.add(accept[i]);
		}
		
	}

	private void fillOneTransitions(String one_trans) {
		String[] oneT = one_trans.split(";");
		for (int i = 0; i < oneT.length; i++) {
			String[] trans = oneT[i].split(",");
			Transition t = new Transition(trans[0], "1", trans[1]);
			this.oneTransitions.add(t);
			//System.out.println(oneT[i]);
		}
		
	}

	private void fillZeroTransitions(String zero_trans) {
		String[] zeroT = zero_trans.split(";");
		
		for (int i = 0; i < zeroT.length; i++) {
			String[] trans = zeroT[i].split(",");
			Transition t = new Transition(trans[0], "0", trans[1]);
			this.zeroTransitions.add(t);
		}
		
	}

	private void fillE_Transitions(String e_trans) {
		String[] eT = e_trans.split(";");
		
		for (int i = 0; i < eT.length; i++) {
			String[] trans = eT[i].split(",");
			Transition t = new Transition(trans[0], "e", trans[1]);
			this.e_transitions.add(t);
		}
		
	}
	
	
	public void printNFA() {
		
		//states
		System.out.println("States");
		for (int i = 0; i < this.states.size(); i++) {
			System.out.println(this.states.get(i));
		}
		
		// acceptStates
		System.out.println("accept States");
		for (int i = 0; i < this.acceptStates.size(); i++) {
			System.out.println(this.acceptStates.get(i));
		}
		
		// e_t
		System.out.println("e_Transitions");
		for (int i = 0; i < this.e_transitions.size(); i++) {
			System.out.print(this.e_transitions.get(i).getStart());
			System.out.print(this.e_transitions.get(i).getSymbol());
			System.out.print(this.e_transitions.get(i).getEnd());
			System.out.println();
		}
		
		//one_T
		System.out.println("one_Tranisition");
		for (int i = 0; i < this.oneTransitions.size(); i++) {
			System.out.print(this.oneTransitions.get(i).getStart());
			System.out.print(this.oneTransitions.get(i).getSymbol());
			System.out.print(this.oneTransitions.get(i).getEnd());
			System.out.println();
		}
		
		//zeroT
		System.out.println("Zero Transitions");
		
		for (int i = 0; i < this.zeroTransitions.size(); i++) {
			System.out.print(this.zeroTransitions.get(i).getStart());
			System.out.print(this.zeroTransitions.get(i).getSymbol());
			System.out.print(this.zeroTransitions.get(i).getEnd());
			System.out.println();
		}
		
		
		//closures
		
		System.out.println("set of closures");
		for (int i = 0; i < this.closures.size(); i++) {
			System.out.println(this.states.get(i)+" "+this.closures.get(i));
		}
	}
	
	
	public ArrayList<String> getStates() {
		return this.states;
	}
	
	public ArrayList<String> getClosures() {
		return this.closures;
	}
	
	public ArrayList<Transition> getZeroTransitions() {
		return this.zeroTransitions;
	}
	
	public ArrayList<Transition> getOneTransitions() {
		return this.oneTransitions;
	}
	
	public ArrayList<String> getAccepStates() {
		return this.acceptStates;
	}
	
}


class Transition{
	private String start;
	private String symbol;
	private String end;
	
	public Transition(String start, String symbol, String end) {
		this.start = start;
		this.symbol = symbol;
		this.end = end;
	}
	
	public String getStart() {
		return this.start;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	public String getEnd() {
		return this.end;
	}
}


//array of nodes
class DFA{
	private ArrayList<String> states;
	private NFA nfa;
	private ArrayList<Transition> transitions;
	private String startState;
//	private ArrayList<String> acceptStates;
	
	public DFA(String nfaDescription) {
		this.nfa = new NFA(nfaDescription);
		this.states = new ArrayList<String>();
		this.transitions = new ArrayList<Transition>();
//		this.acceptStates = new ArrayList<String>();
		
		this.transitions.add(new Transition("","0",""));
		this.transitions.add(new Transition("","1",""));
		constructDFA();
		
		
	}

	//tmam
	public String getNextState(String currentState , char symbol) {
		for (int i = 0; i < this.transitions.size(); i++) {
			Transition t = this.transitions.get(i);
			if(t.getStart().equals(currentState) && t.getSymbol().equals(symbol+"")) {
				return t.getEnd();
			}
		}
		return null;
	}


	private void constructDFA() {
		//build states
		String startStateOfNFA = "0";
		String startStateOfDFA = buildState(startStateOfNFA);
		this.startState = startStateOfDFA;
		this.states.add(startStateOfDFA);
		for (int i = 0; i < this.states.size(); i++) {
			
			putZeros(this.states.get(i), "");
			putOnes(this.states.get(i),"");
		}
		
	}
	
	//tmam
	
	private void putZeros(String state ,String s) {
		String[] states = state.split(",");
		for (int i = 0; i < states.length; i++) {
			for (int j = 0; j < this.nfa.getZeroTransitions().size(); j++) {
				if(this.nfa.getZeroTransitions().get(j).getStart().equals(states[i])) {
					if(!s.contains(this.nfa.getZeroTransitions().get(j).getEnd())) {
						String toAdd = this.nfa.getZeroTransitions().get(j).getEnd();
						if(s.length() != 0) {
							s = s + "," +toAdd;								
						}else {
							s=s+toAdd;
						}
					}
				}
			}
		}

		String builded = buildState(s);
		if(builded.length() != 0 && !this.states.contains(builded)) {
			this.states.add(builded);	
		}
			String start = state;
			String end = builded;
			Transition t = new Transition(start, "0", end);
			this.transitions.add(t);

	}

	//tmam
	private void putOnes(String state, String s) {
		String[] states = state.split(",");
		for (int i = 0; i < states.length; i++) {
			for (int j = 0; j < this.nfa.getOneTransitions().size(); j++) {
				if(this.nfa.getOneTransitions().get(j).getStart().equals(states[i])) {
					if(!s.contains(this.nfa.getOneTransitions().get(j).getEnd())) {
						String toAdd = this.nfa.getOneTransitions().get(j).getEnd();
						if(s.length() != 0) {
							s = s + "," +toAdd;								
						}else {
							s=s+toAdd;
						}
					}
				}
			}
		
		}
		String builded = buildState(s);
		if(builded.length() != 0 && !this.states.contains(builded)) {
			this.states.add(builded);
			
		}
			String start = state;
			String end = builded;
			Transition t = new Transition(start, "1", end);
			this.transitions.add(t);
	}
	

	// get all closures inside the state
	private String buildState(String state) {
		String[] states = state.split(",");

		for (int i = 0; i < states.length; i++) {
			if(!states[i].equals("")) {
				String closuresOfChar = getClosuresOfState(states[i]);
				String[] closures = closuresOfChar.split(",");
				for (int j = 0; j < closures.length; j++) {
					if((!state.contains(","+closures[j]+",") || !state.contains("," + closures[j])) && !closures[j].equals("")) {
						state = state +"," + closures[j];
						states = state.split(",");
					}
				}
			}
			
			
			
		}
		return state;
	}

	private String getClosuresOfState(String state) {
//		System.out.println("state "+state);
		int indexOfState = this.nfa.getStates().indexOf(state);
//		System.out.println(indexOfState);
		return this.nfa.getClosures().get(indexOfState);
	}
	
	
	public void printDFA() {
		System.out.println("States of DFA");
		for (int i = 0; i < this.states.size(); i++) {
			System.out.println(this.states.get(i));
		}
		
		System.out.println("Transitions");
		
		for (int i = 0; i < this.transitions.size(); i++) {
			System.out.print(this.transitions.get(i).getStart() + " ");
			System.out.print(this.transitions.get(i).getSymbol() + " ");
			System.out.print(this.transitions.get(i).getEnd());
			System.out.println();
		}
	}
	
	public boolean isAccepted(String state) {
		String[] states = state.split(",");
		for (int i = 0; i < states.length; i++) {
			if(this.nfa.getAccepStates().contains(states[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getStartState() {
		return this.startState;
	}
	
}

