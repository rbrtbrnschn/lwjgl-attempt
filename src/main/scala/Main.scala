import com.rbrtbrnschn.voxel2.core.{Block, ChunkGenerator}
import com.rbrtbrnschn.voxel2.core.glsl.{Color, GLSLShaders, Mesh, Position, Vertex}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil.NULL
import org.joml.{Matrix4f, Vector3f}
import org.lwjgl.BufferUtils

object CubeDemo {
  def compileShader(typeId: Int, src: String): Int = {
    val shader = glCreateShader(typeId)
    glShaderSource(shader, src)
    glCompileShader(shader)
    if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
      throw new RuntimeException("Shader compile failed: " + glGetShaderInfoLog(shader))
    shader
  }

  def linkProgram(vs: Int, fs: Int): Int = {
    val program = glCreateProgram()
    glAttachShader(program, vs)
    glAttachShader(program, fs)
    glLinkProgram(program)
    if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
      throw new RuntimeException("Program link failed: " + glGetProgramInfoLog(program))
    glDetachShader(program, vs)
    glDetachShader(program, fs)
    glDeleteShader(vs)
    glDeleteShader(fs)
    program
  }


  // Cube definition
  def createCube(block: Block, x: Int, y: Int, z: Int): Mesh = {
    val verts = block.faces.flatten.map { vertex =>
      val newPos = new Vector3f(vertex.position.toVector3f).add(x, y, z)
      vertex.copy(position = Position(newPos.x, newPos.y, newPos.z))
    }
    new Mesh(verts)
  }

  def main(args: Array[String]): Unit = {
    if (!glfwInit()) throw new IllegalStateException("Unable to init GLFW")
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    val width = 800
    val height = 600
    val window = glfwCreateWindow(width, height, "Cube Demo", NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create GLFW window")
    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwShowWindow(window)
    GL.createCapabilities()
    glEnable(GL_DEPTH_TEST)

    val vs = compileShader(GL_VERTEX_SHADER, GLSLShaders.vertexShaderSrc)
    val fs = compileShader(GL_FRAGMENT_SHADER, GLSLShaders.fragmentShaderSrc)
    val program = linkProgram(vs, fs)
    val uMVP = glGetUniformLocation(program, "uMVP")

//    // Create a chunk of grass
//    val cubes = (0 to 15).flatMap(x => {
//      (0 to 15).flatMap(y => {
//        (0 to 15).map(z => {
//          createCube(Block.grass,x,y,z)
//        })
//      })
//    })
    var cubes: List[Mesh] = List.empty

    var lastX = width / 2.0
    var lastY = height / 2.0
    var yaw = -90.0f
    var pitch = 0.0f
    val cameraPos = new Vector3f(0, 1.2f, 3f)
    val cameraFront = new Vector3f(0, 0, -1)
    val cameraUp = new Vector3f(0, 1, 0)

    glfwSetCursorPosCallback(window, (win: Long, xpos: Double, ypos: Double) => {
      val xoffset = (xpos - lastX).toFloat
      val yoffset = (lastY - ypos).toFloat
      lastX = xpos
      lastY = ypos
      val sensitivity = 0.1f
      yaw += xoffset * sensitivity
      pitch += yoffset * sensitivity
      if (pitch > 89) pitch = 89
      if (pitch < -89) pitch = -89
      cameraFront.set(
        Math.cos(Math.toRadians(yaw)).toFloat * Math.cos(Math.toRadians(pitch)).toFloat,
        Math.sin(Math.toRadians(pitch)).toFloat,
        Math.sin(Math.toRadians(yaw)).toFloat * Math.cos(Math.toRadians(pitch)).toFloat
      ).normalize()
    })

    while (!glfwWindowShouldClose(window)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
      glClearColor(0.1f, 0.2f, 0.4f, 1.0f)

      // keyboard input
      val speed = 2.5f * 0.016f
      if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) cameraPos.add(new Vector3f(cameraFront).mul(speed))
      if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) cameraPos.sub(new Vector3f(cameraFront).mul(speed))
      val right = cameraFront.cross(cameraUp, new Vector3f()).normalize()
      if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) cameraPos.sub(new Vector3f(right).mul(speed))
      if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) cameraPos.add(new Vector3f(right).mul(speed))

      // change face colors
      for(i <- 0 until 6) {
        if(glfwGetKey(window, GLFW_KEY_1 + i) == GLFW_PRESS) {
//          val List(r,g,b) = (0 to 2).toList.map(_ => Math.random().toFloat)
//          cubes.foreach(c => c.updateFaceColor(faceIndex = i, r = r, g = g, b = b))
        }
      }
      if(glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS) {
        cubes = cubes ++ ChunkGenerator.generate(Block.grass)
      }

      // MVP
      val proj = new Matrix4f().perspective(Math.toRadians(60).toFloat, width.toFloat/height, 0.1f, 100f)
      val view = new Matrix4f().lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp)
      val model = new Matrix4f()
      val mvp = proj.mul(view, new Matrix4f()).mul(model)

      val stack = stackPush()
      val fb = stack.mallocFloat(16)
      mvp.get(fb)
      glUseProgram(program)
      glUniformMatrix4fv(uMVP, false, fb)
      stack.pop()

      cubes.foreach(_.draw())

      glfwSwapBuffers(window)
      glfwPollEvents()
    }

    glDeleteProgram(program)
    glfwTerminate()
  }
}
