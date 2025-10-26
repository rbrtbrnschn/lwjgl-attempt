package com.rbrtbrnschn.voxel2.core

import org.joml.Vector3f

case class Color(r: Double, g: Double, b: Double) {
  def toVec3f: Vector3f = new Vector3f(r.toFloat, g.toFloat, b.toFloat)
}
object Color {
  val white: Color = new Color(1.0,1.0,1.0)
  val black: Color = new Color(0.0,0.0,0.0)
  val red: Color = new Color(1.0, 0.0, 0.0)
  val green: Color = new Color(0.0, 1.0, 0.0)
  val blue: Color = new Color(0.0, 0.0, 1.0)
  val yellow: Color = new Color(1.0, 1.0, 0.0)
  val cyan: Color = new Color(0.0, 1.0, 1.0)
  val magenta: Color = new Color(1.0, 0.0, 1.0)
}