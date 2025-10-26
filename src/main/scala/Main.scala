import com.rbrtbrnschn.voxel2.core.{Color, Position, Vertex}
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

//  val faces = Array(
//    // front
//    Array(
//      Vertex(Position(-0.5f,-0.5f, 0.5f), red),
//      Vertex(Position( 0.5f,-0.5f, 0.5f), red),
//      Vertex(Position( 0.5f, 0.5f, 0.5f), red),
//      Vertex(Position(-0.5f,-0.5f, 0.5f), red),
//      Vertex(Position( 0.5f, 0.5f, 0.5f), red),
//      Vertex(Position(-0.5f, 0.5f, 0.5f), red)
//    ),
//    // back
//    Array(
//      Vertex(Position(-0.5f,-0.5f,-0.5f), green),
//      Vertex(Position( 0.5f,-0.5f,-0.5f), green),
//      Vertex(Position( 0.5f, 0.5f,-0.5f), green),
//      Vertex(Position(-0.5f,-0.5f,-0.5f), green),
//      Vertex(Position( 0.5f, 0.5f,-0.5f), green),
//      Vertex(Position(-0.5f, 0.5f,-0.5f), green)
//    ),
//    // left
//    Array(
//      Vertex(Position(-0.5f,-0.5f,-0.5f), blue),
//      Vertex(Position(-0.5f,-0.5f, 0.5f), blue),
//      Vertex(Position(-0.5f, 0.5f, 0.5f), blue),
//      Vertex(Position(-0.5f,-0.5f,-0.5f), blue),
//      Vertex(Position(-0.5f, 0.5f, 0.5f), blue),
//      Vertex(Position(-0.5f, 0.5f,-0.5f), blue)
//    ),
//    // right
//    Array(
//      Vertex(Position(0.5f,-0.5f,-0.5f), yellow),
//      Vertex(Position(0.5f,-0.5f, 0.5f), yellow),
//      Vertex(Position(0.5f, 0.5f, 0.5f), yellow),
//      Vertex(Position(0.5f,-0.5f,-0.5f), yellow),
//      Vertex(Position(0.5f, 0.5f, 0.5f), yellow),
//      Vertex(Position(0.5f, 0.5f,-0.5f), yellow)
//    ),
//    // top
//    Array(
//      Vertex(Position(-0.5f,0.5f,-0.5f), cyan),
//      Vertex(Position(-0.5f,0.5f, 0.5f), cyan),
//      Vertex(Position( 0.5f,0.5f, 0.5f), cyan),
//      Vertex(Position(-0.5f,0.5f,-0.5f), cyan),
//      Vertex(Position( 0.5f,0.5f, 0.5f), cyan),
//      Vertex(Position( 0.5f,0.5f,-0.5f), cyan)
//    ),
//    // bottom
//    Array(
//      Vertex(Position(-0.5f,-0.5f,-0.5f), magenta),
//      Vertex(Position(-0.5f,-0.5f, 0.5f), magenta),
//      Vertex(Position( 0.5f,-0.5f, 0.5f), magenta),
//      Vertex(Position(-0.5f,-0.5f,-0.5f), magenta),
//      Vertex(Position( 0.5f,-0.5f, 0.5f), magenta),
//      Vertex(Position( 0.5f,-0.5f,-0.5f), magenta)
//    )
//  )

  val faces = Array(
    // front
    Array(
      Vertex(Position(-0.5f,-0.5f, 0.5f), Color.red),
      Vertex(Position( 0.5f,-0.5f, 0.5f), Color.red),
      Vertex(Position( 0.5f, 0.5f, 0.5f), Color.red),
      Vertex(Position(-0.5f,-0.5f, 0.5f), Color.red),
      Vertex(Position( 0.5f, 0.5f, 0.5f), Color.red),
      Vertex(Position(-0.5f, 0.5f, 0.5f), Color.red)
    ),
    // back
    Array(
      Vertex(Position(-0.5f,-0.5f,-0.5f), Color.green),
      Vertex(Position( 0.5f,-0.5f,-0.5f), Color.green),
      Vertex(Position( 0.5f, 0.5f,-0.5f), Color.green),
      Vertex(Position(-0.5f,-0.5f,-0.5f), Color.green),
      Vertex(Position( 0.5f, 0.5f,-0.5f), Color.green),
      Vertex(Position(-0.5f, 0.5f,-0.5f), Color.green)
    ),
    // left
    Array(
      Vertex(Position(-0.5f,-0.5f,-0.5f), Color.blue),
      Vertex(Position(-0.5f,-0.5f, 0.5f), Color.blue),
      Vertex(Position(-0.5f, 0.5f, 0.5f), Color.blue),
      Vertex(Position(-0.5f,-0.5f,-0.5f), Color.blue),
      Vertex(Position(-0.5f, 0.5f, 0.5f), Color.blue),
      Vertex(Position(-0.5f, 0.5f,-0.5f), Color.blue)
    ),
    // right
    Array(
      Vertex(Position(0.5f,-0.5f,-0.5f), Color.yellow),
      Vertex(Position(0.5f,-0.5f, 0.5f), Color.yellow),
      Vertex(Position(0.5f, 0.5f, 0.5f), Color.yellow),
      Vertex(Position(0.5f,-0.5f,-0.5f), Color.yellow),
      Vertex(Position(0.5f, 0.5f, 0.5f), Color.yellow),
      Vertex(Position(0.5f, 0.5f,-0.5f), Color.yellow)
    ),
    // top
    Array(
      Vertex(Position(-0.5f,0.5f,-0.5f), Color.cyan),
      Vertex(Position(-0.5f,0.5f, 0.5f), Color.cyan),
      Vertex(Position( 0.5f,0.5f, 0.5f), Color.cyan),
      Vertex(Position(-0.5f,0.5f,-0.5f), Color.cyan),
      Vertex(Position( 0.5f,0.5f, 0.5f), Color.cyan),
      Vertex(Position( 0.5f,0.5f,-0.5f), Color.cyan)
    ),
    // bottom
    Array(
      Vertex(Position(-0.5f,-0.5f,-0.5f), Color.magenta),
      Vertex(Position(-0.5f,-0.5f, 0.5f), Color.magenta),
      Vertex(Position( 0.5f,-0.5f, 0.5f), Color.magenta),
      Vertex(Position(-0.5f,-0.5f,-0.5f), Color.magenta),
      Vertex(Position( 0.5f,-0.5f, 0.5f), Color.magenta),
      Vertex(Position( 0.5f,-0.5f,-0.5f), Color.magenta)
    )
  )


  class Mesh(val vertices: Array[Vertex], val indices: Array[Int]) {
    val vao: Int = glGenVertexArrays()
    val vbo: Int = glGenBuffers()
    val ebo: Int = glGenBuffers()
    // Create two VBOs

    // Flatten all positions and colors
    val cubeVerticesPositions = vertices.flatMap(v => Array(v.position.x, v.position.y, v.position.z))
    val cubeVerticesColors    = vertices.flatMap(v => Array(v.color.r, v.color.g, v.color.b))

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
  def createCube(x: Int, y: Int, z: Int): Mesh = {
    val verts = faces.flatten.map { vertex =>
      val newPos = new Vector3f(vertex.position.toVector3f).add(x, y, z)
      vertex.copy(position = Position(newPos.x, newPos.y, newPos.z))
    }
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

    val cubes = (0 to 15).flatMap(x => {
      (0 to 15).flatMap(y => {
        (0 to 15).map(z => {
          createCube(x,y,z)
        })
      })
    })

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
          val r = Math.random().toFloat
          val g = Math.random().toFloat
          val b = Math.random().toFloat
          cubes.foreach(c => c.updateFaceColor(faceIndex = i, r = r, g = g, b = b))
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

      cubes.foreach(_.draw())

      glfwSwapBuffers(window)
      glfwPollEvents()
    }

    glDeleteProgram(program)
    glfwTerminate()
  }
}
