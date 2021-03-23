import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

public class Pair<T> extends AbstractMap.SimpleEntry<T, T>{
	public Pair(T a, T b){
		super(a, b);
	}
}
