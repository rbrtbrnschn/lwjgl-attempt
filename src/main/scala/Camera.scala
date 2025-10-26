import org.joml.{Matrix4f, Vector3f}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL20.{glGetUniformLocation, glUniform3f}

class Camera(var position: Vector3f, var yaw: Float = -90f, var pitch: Float = 0f) {
  private val front = new Vector3f(0f, 0f, -1f)
  private val up = new Vector3f(0f, 1f, 0f)
  private val right = new Vector3f()
  private val worldUp = new Vector3f(0f, 1f, 0f)
  private val worldDown = new Vector3f(0f, -1f, 0f)

  private var firstMouse = true
  private var lastX = 400.0
  private var lastY = 300.0

  val speed = 2.5f
  val sensitivity = 0.1f

  updateVectors()

  def getViewMatrix(): Matrix4f = {
    val center = new Vector3f(position).add(front)
    new Matrix4f().lookAt(position, center, up)
  }

  def processKeyboard(window: Long, deltaTime: Float): Unit = {
    val velocity = speed * deltaTime

    if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
      position.add(new Vector3f(front).mul(velocity))
    if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
      position.sub(new Vector3f(front).mul(velocity))
    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
      position.sub(new Vector3f(right).mul(velocity))
    if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
      position.add(new Vector3f(right).mul(velocity))
    if(glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
      position.add(new Vector3f(worldDown).mul(velocity))
    if(glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS)
      position.add(new Vector3f(worldUp).mul(velocity))

  }

  def processMouse(xpos: Double, ypos: Double): Unit = {
    if (firstMouse) {
      lastX = xpos
      lastY = ypos
      firstMouse = false
    }

    val xoffset = (xpos - lastX).toFloat * sensitivity
    val yoffset = (lastY - ypos).toFloat * sensitivity
    lastX = xpos
    lastY = ypos

    yaw += xoffset
    pitch += yoffset

    if (pitch > 89f) pitch = 89f
    if (pitch < -89f) pitch = -89f

    updateVectors()
  }

  private def updateVectors(): Unit = {
    val frontX = Math.cos(Math.toRadians(yaw)).toFloat * Math.cos(Math.toRadians(pitch)).toFloat
    val frontY = Math.sin(Math.toRadians(pitch)).toFloat
    val frontZ = Math.sin(Math.toRadians(yaw)).toFloat * Math.cos(Math.toRadians(pitch)).toFloat
    front.set(frontX, frontY, frontZ).normalize()
    right.set(front).cross(worldUp).normalize()
    up.set(right).cross(front).normalize()
  }
}
