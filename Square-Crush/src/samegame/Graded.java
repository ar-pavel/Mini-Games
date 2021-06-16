package samegame;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Graded {
	public String description();
	public int marks();
}
