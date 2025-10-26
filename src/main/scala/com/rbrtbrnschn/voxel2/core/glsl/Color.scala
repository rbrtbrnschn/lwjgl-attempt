package com.rbrtbrnschn.voxel2.core.glsl

import org.joml.Vector3f

case class Color(r: Float, g: Float, b: Float) {
  def toVec3f: Vector3f = new Vector3f(r, g, b)
}
object Color {
  val white: Color = new Color(1.0f,1.0f,1.0f)
  val black: Color = new Color(0.0f,0.0f,0.0f)
  val red: Color = new Color(1.0f, 0.0f, 0.0f)
  val green: Color = new Color(0.0f, 1.0f, 0.0f)
  val blue: Color = new Color(0.0f, 0.0f, 1.0f)
  val yellow: Color = new Color(1.0f, 1.0f, 0.0f)
  val cyan: Color = new Color(0.0f, 1.0f, 1.0f)
  val magenta: Color = new Color(1.0f, 0.0f, 1.0f)
}