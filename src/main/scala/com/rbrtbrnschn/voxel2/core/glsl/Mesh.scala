package com.rbrtbrnschn.voxel2.core.glsl

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.{GL_FLOAT, GL_TRIANGLES, GL_UNSIGNED_INT, glDrawElements}
import org.lwjgl.opengl.GL15.{GL_ARRAY_BUFFER, GL_DYNAMIC_DRAW, GL_ELEMENT_ARRAY_BUFFER, GL_STATIC_DRAW, glBindBuffer, glBufferData, glBufferSubData, glGenBuffers}
import org.lwjgl.opengl.GL20.{glEnableVertexAttribArray, glVertexAttribPointer}
import org.lwjgl.opengl.GL30.{glBindVertexArray, glGenVertexArrays}

class Mesh(val vertices: Array[Vertex]) {
  val indices = (0 until vertices.length).toArray

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