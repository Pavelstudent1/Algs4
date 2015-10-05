package forMetrix;

import java.util.Collection;

public interface Consumer<T> {
	
	void consume(Collection<T> data);
}
