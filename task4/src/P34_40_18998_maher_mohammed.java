
import java.util.ArrayList;
import java.util.Stack;

public class P34_40_18998_maher_mohammed {

	public static void main(String[] args) {
		FDFA f = new FDFA("0,0,1,A;1,2,1,B;2,3,3,C;3,4,1,D;4,5,2,E;5,4,1,F#4,5");
		run(f,"0010");
		System.out.println();

		run(f,"101001");
		System.out.println();

		run(f,"1010010001");
		System.out.println();

		run(f,"101010");
		System.out.println();

		run(f,"001101");
		
		System.out.println();
		System.out.println();
		FDFA f2 = new FDFA("0,1,0,A;1,1,2,B;2,2,3,C;3,3,4,D;4,4,4,E#1,3");
		run(f2,"1010");
		System.out.println();

		run(f2,"0111");
		System.out.println();

		run(f2,"01");
		System.out.println();

		run(f2,"01110");
		System.out.println();

		run(f2,"10101");
		System.out.println();

	}
	
	
	public static void run(FDFA f , String s) {
		int indexOfLastAcceptState = 0;
		State lastAcceptState = null;
		State currentState = f.getStartState();
		
		for (int i = 0; i < s.length(); i++) {
			
			currentState = f.getNextState(currentState , Integer.parseInt("" + s.charAt(i)));			
			if(currentState.getIsAccepted()) {
				lastAcceptState = currentState;
				indexOfLastAcceptState = i;
			}
			
			//check if we reached the last index
			if(i == s.length()-1) {
				//check if the current state is accepted
				//if it is accepted so print its action
				if(currentState.getIsAccepted()) {
					//print the action and finish
					System.out.print(currentState.getAction());
					return;
				}
				else {
					//print the action of the last accepted state if it is not null
					if (lastAcceptState != null) {
						System.out.print(lastAcceptState.getAction());
					}
					else { 
						if(lastAcceptState == null) {
							System.out.print(currentState.getAction());
							return;
						}
					}
					
					//if it is not accepted 
					//make the i equals the indexOfLastAcceptState and it will be increased by the loop
					i = indexOfLastAcceptState;
					// make the current state the next of last accepted one
					//int symbol = Integer.parseInt("" + s.charAt(i+1));
					currentState = f.getStartState();
					//make last accepted state null
					lastAcceptState = null;
				}
//				System.out.println(indexOfLastAcceptState);
			}
		}
	}

}


class FDFA{
	private ArrayList<State> states;
	private ArrayList<Integer> intStates;
	private State startState;
	private ArrayList<Transition> transitions;
	//private ArrayList<State> acceptStates;
	
	public FDFA(String description) {
		//initializations
		this.states = new ArrayList<State>();
		this.intStates = new ArrayList<Integer>();
		this.transitions = new ArrayList<Transition>();
		
		String[] p_and_s = description.split("#");
		String p = p_and_s[0];
		String s = p_and_s[1];
		
		String[] statesAndTransitions = p.split(";");
		String[] acceptStatess = s.split(",");
		
		constructStates(statesAndTransitions);
		constructAcceptStates(acceptStatess);
	}

	public State getNextState(State currentState, int symbol) {
		int returnedStateInt = -1;
		for (int i = 0; i < this.transitions.size(); i++) {
			Transition t = transitions.get(i);
			if(t.getStart() == currentState.getMyState() && t.getSymbol() == symbol) {
				returnedStateInt = t.getEnd();
			}
		}
		//get the state itself
		
		for (int i = 0; i < this.states.size(); i++) {
			State s = this.states.get(i);
			if(s.getMyState() == returnedStateInt) {
				return s;
			}
		}
		
		return null;
	}

	private void constructAcceptStates(String[] acceptStatess) {
		for (int i = 0; i < acceptStatess.length; i++) {
			//search in states and change isAccepted
			int s = Integer.parseInt(acceptStatess[i]);
			for (int j = 0; j < this.states.size(); j++) {
				if(this.states.get(j).getMyState() == s) {
					this.states.get(j).setIsAccepted(true);
				}
			}
		}
		
	}

	private void constructStates(String[] statesAndTransitions) {
		for (int i = 0; i < statesAndTransitions.length; i++) {
			String[] s = statesAndTransitions[i].split(",");
			int state = Integer.parseInt(s[0]);
			String action = s[3];
			State start = new State(action, state);
			
			if(!this.intStates.contains(start.getMyState())) {
				this.states.add(start);
				this.intStates.add(start.getMyState());
			}
			
			
			//start state construction
			if(state == 0) {
				this.startState = start;
			}
			
			// make transitions
			int endZ = Integer.parseInt(s[1]);
			int endO = Integer.parseInt(s[2]);
			
			Transition t0 = new Transition(state, 0, endZ);
			Transition t1 = new Transition(state, 1, endO);
			this.transitions.add(t0);
			this.transitions.add(t1);
			
		}
		
	}
	
	public State getStartState() {
		return this.startState;
	}
	
	
	public void printFDFA() {
		//states
		System.out.println("states");
		for (int i = 0; i < this.states.size(); i++) {
			System.out.print(this.states.get(i).getMyState());
			System.out.print(this.states.get(i).getIsAccepted());
			System.out.println();
		}
		
		//transitions
		System.out.println("transitions");
		for (int i = 0; i < this.transitions.size(); i++) {
			Transition t = this.transitions.get(i);
			System.out.print(t.getStart()+" ");
			System.out.print(t.getSymbol()+" ");
			System.out.print(t.getEnd());
			System.out.println();
		}
	}
}


class State{
	private String action;
	private int myState;
	private boolean isAccepted = false;
	
	public State(String action, int myState) {
		this.action = action;
		this.myState = myState;
		//this.isAccepted = isAccepted;
	}
	
	public int getMyState() {
		return this.myState;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public void setMyState(int s) {
		this.myState = s;
		
	}
	
	public void setIsAccepted(boolean accepted) {
		this.isAccepted = accepted;
		
	}
	
	public boolean getIsAccepted() {
		return this.isAccepted;
	}
	
}


class Transition{
	private int start;
	private int end;
	private int symbol;
	
	public Transition(int start, int symbol, int end) {
		this.start = start;
		this.symbol = symbol;
		this.end = end;
	}
	
	public int getStart() {
		return this.start;
	}
	
	public int getSymbol() {
		return this.symbol;
	}
	
	public int getEnd() {
		return this.end;
	}
}

