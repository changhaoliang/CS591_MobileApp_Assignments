import java.util.ArrayList;
import java.util.HashMap;

public class Round {
	
	private String word;
	private String hint;
	private HashMap<Character,ArrayList<Integer>> charMap = new HashMap<>();
	
	public Round(String word, String hint) {
		this.word = word;
		this.hint = hint;
		for(int i = 0; i < word.length(); i++) {
			Character ch = word.charAt(i);
			ArrayList<Integer> position = this.charMap.getOrDefault(ch,new ArrayList<Integer>());
			position.add(i);
			this.charMap.put(ch, position);
			
		}
	}
	
	public boolean check(Character ch) {
		return charMap.containsKey(ch);
	}
	
	public ArrayList<Integer> getPosition(Character ch){
		return charMap.get(ch);
	}
	
	public String getWord() {
		return this.word;
	}
	
	public String getHint() {
		return this.hint;
	}
}
