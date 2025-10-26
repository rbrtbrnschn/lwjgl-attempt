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
  val red = new Vector3f(1, 0, 0)
  val green = new Vector3f(0, 1, 0)
  val blue = new Vector3f(0, 0, 1)
  val yellow = new Vector3f(1, 1, 0)
  val cyan = new Vector3f(0, 1, 1)
  val magenta = new Vector3f(1, 0, 1)

  val faces = Array(
    // front
    Array(
      Vertex(new Vector3f(-0.5f,-0.5f, 0.5f), red),
      Vertex(new Vector3f( 0.5f,-0.5f, 0.5f), red),
      Vertex(new Vector3f( 0.5f, 0.5f, 0.5f), red),
      Vertex(new Vector3f(-0.5f,-0.5f, 0.5f), red),
      Vertex(new Vector3f( 0.5f, 0.5f, 0.5f), red),
      Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), red)
    ),
    // back
    Array(
      Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), green),
      Vertex(new Vector3f( 0.5f,-0.5f,-0.5f), green),
      Vertex(new Vector3f( 0.5f, 0.5f,-0.5f), green),
      Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), green),
      Vertex(new Vector3f( 0.5f, 0.5f,-0.5f), green),
      Vertex(new Vector3f(-0.5f, 0.5f,-0.5f), green)
    ),
    // left
    Array(
      Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), blue),
      Vertex(new Vector3f(-0.5f,-0.5f, 0.5f), blue),
      Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), blue),
      Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), blue),
      Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), blue),
      Vertex(new Vector3f(-0.5f, 0.5f,-0.5f), blue)
    ),
    // right
    Array(
      Vertex(new Vector3f(0.5f,-0.5f,-0.5f), yellow),
      Vertex(new Vector3f(0.5f,-0.5f, 0.5f), yellow),
      Vertex(new Vector3f(0.5f, 0.5f, 0.5f), yellow),
      Vertex(new Vector3f(0.5f,-0.5f,-0.5f), yellow),
      Vertex(new Vector3f(0.5f, 0.5f, 0.5f), yellow),
      Vertex(new Vector3f(0.5f, 0.5f,-0.5f), yellow)
    ),
    // top
    Array(
      Vertex(new Vector3f(-0.5f,0.5f,-0.5f), cyan),
      Vertex(new Vector3f(-0.5f,0.5f, 0.5f), cyan),
      Vertex(new Vector3f( 0.5f,0.5f, 0.5f), cyan),
      Vertex(new Vector3f(-0.5f,0.5f,-0.5f), cyan),
      Vertex(new Vector3f( 0.5f,0.5f, 0.5f), cyan),
      Vertex(new Vector3f( 0.5f,0.5f,-0.5f), cyan)
    ),
    // bottom
    Array(
      Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), magenta),
      Vertex(new Vector3f(-0.5f,-0.5f, 0.5f), magenta),
      Vertex(new Vector3f( 0.5f,-0.5f, 0.5f), magenta),
      Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), magenta),
      Vertex(new Vector3f( 0.5f,-0.5f, 0.5f), magenta),
      Vertex(new Vector3f( 0.5f,-0.5f,-0.5f), magenta)
    )
  )


  case class Vertex(pos: Vector3f, color: Vector3f)

  class Mesh(val vertices: Array[Vertex], val indices: Array[Int]) {
    val vao: Int = glGenVertexArrays()
    val vbo: Int = glGenBuffers()
    val ebo: Int = glGenBuffers()
    // Create two VBOs

    // Flatten all positions and colors
    val cubeVerticesPositions = vertices.flatMap(v => Array(v.pos.x, v.pos.y, v.pos.z))
    val cubeVerticesColors    = vertices.flatMap(v => Array(v.color.x, v.color.y, v.color.z))

    val vboPos = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vboPos)
    glBufferData(GL_ARRAY_BUFFER, cubeVerticesPositions, GL_STATIC_DRAW)

    val vboColor = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vboColor)
    glBufferData(GL_ARRAY_BUFFER, cubeVerticesColors, GL_DYNAMIC_DRAW)

    glBindVertexArray(vao)


    glBindVertexArray(vao)

    // Position VBO
    glBindBuffer(GL_ARRAY_BUFFER, vboPos)
    glEnableVertexAttribArray(0)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)

    // Color VBO
    glBindBuffer(GL_ARRAY_BUFFER, vboColor)
    glEnableVertexAttribArray(1)
    glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)

    // EBO
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)

    glBindVertexArray(0)

    val idxBuffer = BufferUtils.createIntBuffer(indices.length)
    idxBuffer.put(indices).flip()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, idxBuffer, GL_STATIC_DRAW)

    // position attribute
    glEnableVertexAttribArray(0)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * java.lang.Float.BYTES, 0)
    // color attribute
    glEnableVertexAttribArray(1)
    glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * java.lang.Float.BYTES, 3 * java.lang.Float.BYTES)

    glBindVertexArray(0)

    // update a face's color
    def updateFaceColor(faceIndex: Int, r: Float, g: Float, b: Float): Unit = {
      val verticesPerFace = 6
      val newColors = Array.fill(verticesPerFace*3)(0f)

      for(i <- 0 until verticesPerFace) {
        newColors(i*3 + 0) = r
        newColors(i*3 + 1) = g
        newColors(i*3 + 2) = b
      }

      val offset = faceIndex * verticesPerFace * 3 * 4 // 3 floats per vertex × 4 bytes
      glBindBuffer(GL_ARRAY_BUFFER, vboColor)
      glBufferSubData(GL_ARRAY_BUFFER, offset, newColors)
    }

    def draw(): Unit = {
      glBindVertexArray(vao)
      glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0)
      glBindVertexArray(0)
    }
  }

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

  val vertexShaderSrc =
    """#version 330 core
      |layout(location=0) in vec3 aPos;
      |layout(location=1) in vec3 aColor;
      |out vec3 vColor;
      |uniform mat4 uMVP;
      |void main() {
      |  gl_Position = uMVP * vec4(aPos, 1.0);
      |  vColor = aColor;
      |}
      |""".stripMargin

  val fragmentShaderSrc =
    """#version 330 core
      |in vec3 vColor;
      |out vec4 FragColor;
      |void main() {
      |  FragColor = vec4(vColor, 1.0);
      |}
      |""".stripMargin

  // Cube definition
  def createCube(): Mesh = {
    val verts = faces.flatten
    val indices = (0 until verts.length).toArray
    new Mesh(verts, indices)
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

    val vs = compileShader(GL_VERTEX_SHADER, vertexShaderSrc)
    val fs = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSrc)
    val program = linkProgram(vs, fs)
    val uMVP = glGetUniformLocation(program, "uMVP")

    val cube = createCube()

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
          println(s"pressed $i")
          cube.updateFaceColor(faceIndex = i, r = Math.random().toFloat, g = Math.random().toFloat, b = Math.random().toFloat)
        }
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

      cube.draw()

      glfwSwapBuffers(window)
      glfwPollEvents()
    }

    glDeleteProgram(program)
    glfwTerminate()
  }
}
