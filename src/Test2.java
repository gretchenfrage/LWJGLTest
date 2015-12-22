import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Test2 {
	
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;
	private long windowID;
	
	public void run() {
		try {
			//setup stuff
			init();
			
			//enter the rendering loop
			loop();
			
			//non resource related disposal
			dispose();
		} finally {
			//resource cleanup
			cleanup();
		}
	}
	
	public void init() {
		//initialize GLFW
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");
		
		//configure the OpenGL context with window hints
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		//create window
		windowID = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "LWJGL Test", NULL, NULL);
		if (windowID == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		//center the window
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowID, (vidmode.width() - WINDOW_WIDTH) / 2, (vidmode.height() - WINDOW_HEIGHT) / 2);
		
		//make the OpenGL context current
        glfwMakeContextCurrent(windowID);
        
        //setup OpenGL's GLCapabilities instance in parallel
        //to the current GLFW context
        GL.createCapabilities();
        
        // enable v-sync
        glfwSwapInterval(1);
		
		//now that the window is fully built, show it
		glfwShowWindow(windowID);
	}
	
	public void update(float delta) {
		
	}
	
	public void loop() {
		//to keep track of time change between ticks
		float last = 0;
		float now;
		float delta;
		
		//continuously render and update
		while (glfwWindowShouldClose(windowID) == GL_FALSE) {
			//update time keeping
			now = (float) glfwGetTime();
			delta = now - last;
			last = now;
			
			//clear the buffer, I don't understand what's happening with the bit operator
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			//update with how much time has passed since the last tick
			update(delta);
			
			//bring the rendered buffer to the front
			glfwSwapBuffers(windowID);
			
			//trigger the window event callbacks
			glfwPollEvents();
		}
	}

	public void dispose() {
		//destroy the window
		glfwDestroyWindow(windowID);
	}
	
	public void cleanup() {
		//terminate GLFW
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Test2().run();
	}
	
}