package io.cloudboost;

import org.junit.Test;

public class InitTest {
	@Test(timeout=1000)
	public void shouldInitApp() throws CloudException{
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
}
